package edu.itba.paw.jimi.webapp.api;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.utils.QueryParams;
import edu.itba.paw.jimi.webapp.dto.DishDTO;
import edu.itba.paw.jimi.webapp.dto.DishListDTO;
import edu.itba.paw.jimi.webapp.dto.form.DishForm;
import edu.itba.paw.jimi.webapp.utils.PaginationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;

@Path("admin/dishes")
@Controller
public class DishApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DishApiController.class);
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private PaginationHelper paginationHelper;
	
	@Context
	private UriInfo uriInfo;
	
	private final static String dishApiUri = "admin/dishes/";
	private static final int DEFAULT_PAGE_SIZE = 5;
	private static final int MAX_PAGE_SIZE = 20;
	
	@GET
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response listDishes(@QueryParam("page") @DefaultValue("1") Integer page,
	                           @QueryParam("pageSize") @DefaultValue("" + DEFAULT_PAGE_SIZE) Integer pageSize) {
		page = paginationHelper.getPageAsOneIfZeroOrLess(page);
		pageSize = paginationHelper.getPageSizeAsDefaultSizeIfOutOfRange(pageSize, DEFAULT_PAGE_SIZE, MAX_PAGE_SIZE);
		final Collection<Dish> allDishes = dishService.findAll(new QueryParams((page - 1) * pageSize, pageSize)); //TODO: change for paginated
		return Response.ok(new DishListDTO(new LinkedList<>(allDishes), buildBaseURI()))
				.links(paginationHelper.getPaginationLinks(uriInfo, page, dishService.getTotalDishes()))
				.build();
	}
	
	@POST
	@Path("/create")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response createDish(@Valid final DishForm dishForm) {
		if (dishForm == null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		
		final Dish dish = dishService.create(dishForm.getName(), dishForm.getPrice());
		dishService.setStock(dish, dishForm.getStock());
		dishService.setMinStock(dish, dishForm.getMinStock());
		
		final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(dish.getId())).build();
		return Response.created(location).entity(new DishDTO(dish, buildBaseURI())).build();
	}
	
	@PUT
	@Path("/update/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateDish(@PathParam("id") final int dishId,
	                           @Valid final DishForm dishForm) {
		if (dishForm == null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		
		Dish dish = dishService.findById(dishId);
		if (dish == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		
		dishService.setName(dish, dishForm.getName());
		dishService.setStock(dish, dishForm.getStock());
		dishService.setPrice(dish, dishForm.getPrice());
		dishService.setMinStock(dish, dishForm.getMinStock());
		
		return Response.noContent().build();
	}
	
	@PUT
	@Path("/stock/increase/{id}")
	public Response increaseDishStock(@PathParam("id") final int dishId) {
		Dish dish = dishService.findById(dishId);
		if (dish == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		dishService.increaseStock(dish);
		return Response.noContent().build();
	}
	
	@PUT
	@Path("/stock/decrease/{id}")
	public Response decreaseDishStock(@PathParam("id") final int dishId) {
		Dish dish = dishService.findById(dishId);
		if (dish == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		dishService.decreaseStock(dish);
		return Response.noContent().build();
	}
	
	@POST
	@Path("/downloadCSV")
	@Produces({"text/csv"})
	public Response downloadDishesMissingStockCSV(@Context HttpServletResponse response) throws IOException {
		CsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] headerT = {
				messageSource.getMessage("csv.name", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("csv.price", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("csv.stock", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("csv.minStock", null, LocaleContextHolder.getLocale()),
		};
		csvWriter.writeHeader(headerT);
		
		
		String[] header = {"name", "price", "stock", "minStock"};
		for (Dish dish : dishService.findDishesMissingStock())
			csvWriter.write(dish, header);
		
		csvWriter.flush();
		csvWriter.close();
		
		return Response
				.ok(csvWriter.toString())
				.header("Content-Disposition", String.format("attachment; filename=\"%s\"", messageSource.getMessage("csv.file", null, LocaleContextHolder.getLocale())))
				.header("Content-Type", "application/vnd.ms-excel")
				.build();
	}
	
	private URI buildBaseURI() {
		return URI.create(String.valueOf(uriInfo.getBaseUri()) + dishApiUri);
	}
	
}
