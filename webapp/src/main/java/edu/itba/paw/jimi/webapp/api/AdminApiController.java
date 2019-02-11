package edu.itba.paw.jimi.webapp.api;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.StatsService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.webapp.dto.OrderDTO;
import edu.itba.paw.jimi.webapp.dto.OrderListDTO;
import edu.itba.paw.jimi.webapp.dto.StatsDTO;
import edu.itba.paw.jimi.webapp.dto.form.table.TableAddDishForm;
import edu.itba.paw.jimi.webapp.dto.form.table.TableDishAmountForm;
import edu.itba.paw.jimi.webapp.utils.PaginationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.time.YearMonth;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

@Path("admin")
@Controller
public class AdminApiController extends BaseApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminApiController.class);

	@Autowired
	private TableService tableService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private DishService dishService;

	@Autowired
	private StatsService statsService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PaginationHelper paginationHelper;

	@Context
	private UriInfo uriInfo;

	private static final int DEFAULT_PAGE_SIZE = 5;
	private static final int MAX_PAGE_SIZE = 20;

	@GET
	@Path("/bills")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response listBills(@QueryParam("page") @DefaultValue("1") Integer page,
	                          @QueryParam("pageSize") @DefaultValue("" + DEFAULT_PAGE_SIZE) Integer pageSize) {
		page = paginationHelper.getPageAsOneIfZeroOrLess(page);
		pageSize = paginationHelper.getPageSizeAsDefaultSizeIfOutOfRange(pageSize, DEFAULT_PAGE_SIZE, MAX_PAGE_SIZE);
		int maxPage = paginationHelper.maxPage(orderService.getTotalCancelledOrClosedOrders(), pageSize);
		final Collection<Order> cancelledOrClosedOrders = orderService.findCancelledOrClosedOrders(pageSize, (page - 1) * pageSize);
		URI billsURI = URI.create(String.valueOf(uriInfo.getBaseUri()) +
				UriBuilder.fromResource(AdminApiController.class).build() +
				"/bills" +
				"/");
		final OrderListDTO billsDTO = new OrderListDTO(new LinkedList<>(cancelledOrClosedOrders), billsURI);
		return Response.ok(billsDTO)
				.links(paginationHelper.getPaginationLinks(uriInfo, page, maxPage))
				.build();
	}

	@GET
	@Path("/bills/{id}")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response getBillById(@PathParam("id") final long id) {
		final Order cancelledOrClosedOrder = orderService.findCancelledOrClosedOrderById(id);
		if (cancelledOrClosedOrder == null) {
			LOGGER.warn("Closed or cancelled order with id {} not found", id);
			return Response.status(Response.Status.NOT_FOUND)
					.entity(errorMessageToJSON(messageSource.getMessage("order.error.closed.cancelled.not.found.body", null, LocaleContextHolder.getLocale())))
					.build();
		}
		URI billsURI = URI.create(String.valueOf(uriInfo.getBaseUri()) +
				UriBuilder.fromResource(AdminApiController.class).build() +
				"/bills" +
				"/");
		final OrderDTO billDTO = new OrderDTO(cancelledOrClosedOrder, billsURI);
		return Response.ok(billDTO).build();
	}

	@GET
	@Path("/stats")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response getStats() {
		final int totalAmountOfFreeTables = statsService.getFreeTablesUnits();
		final int totalAmountOfBusyTables = statsService.getBusyTablesUnits();
		final int totalAmountOfPayingTables = statsService.getPayingTablesUnits();
		final int totalAmountOfTables = tableService.getTotalTables();
		final int freeTablesPercentage = statsService.getFreeTables();
		final int stockStatePercentage = statsService.getStockState(50);
		final Map<YearMonth, Double> monthOrderTotals = statsService.getMonthlyOrderTotal();
		final Map<YearMonth, Integer> monthlyOrdersCancelled = statsService.getMonthlyOrderCancelled();
		return Response.ok(new StatsDTO(totalAmountOfFreeTables,
				totalAmountOfBusyTables,
				totalAmountOfPayingTables,
				totalAmountOfTables,
				freeTablesPercentage,
				stockStatePercentage,
				monthOrderTotals,
				monthlyOrdersCancelled
		)).build();
	}

	@POST
	@Path("/bills/{id}/dishes")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addDoneDish(@PathParam("id") final long id,
	                            @Valid final TableAddDishForm tableAddDishForm) {
		if (tableAddDishForm == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		final Order cancelledOrClosedOrder = orderService.findCancelledOrClosedOrderById(id);
		if (cancelledOrClosedOrder == null) {
			LOGGER.warn("Closed or cancelled order with id {} not found", id);
			return Response.status(Response.Status.NOT_FOUND)
					.entity(errorMessageToJSON(messageSource.getMessage("order.error.closed.cancelled.not.found.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		final Dish dish = dishService.findById(tableAddDishForm.getDishId());
		if (dish == null) {
			LOGGER.warn("Dish with id {} not found", tableAddDishForm.getDishId());
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(errorMessageToJSON(messageSource.getMessage("dish.error.404.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		final Dish currentOrderDish = orderService.getDishById(cancelledOrClosedOrder, tableAddDishForm.getDishId());
		if (currentOrderDish != null) {
			LOGGER.warn("From bill with id {}, dish id {} already exists", id, dish.getId());
			return Response
					.status(Response.Status.CONFLICT)
					.entity(errorMessageToJSON(messageSource.getMessage("order.error.dish.exists.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		orderService.addDoneDishes(cancelledOrClosedOrder, dish, tableAddDishForm.getAmount());
		LOGGER.info("Added done dish {} to table with id {}", dish.getId(), id);
		URI billsURI = URI.create(String.valueOf(uriInfo.getBaseUri()) +
				UriBuilder.fromResource(AdminApiController.class).build() +
				"/bills" +
				"/");
		final OrderDTO billDTO = new OrderDTO(cancelledOrClosedOrder, billsURI);
		return Response.ok(billDTO).build();
	}

	@POST
	@Path("/bills/{id}/dishes/{dishId}/amount")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setDoneDishAmount(@PathParam("id") final long id,
	                                  @PathParam("dishId") final int dishId,
	                                  @Valid final TableDishAmountForm tableDishAmountForm) {
		if (tableDishAmountForm == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		final Order cancelledOrClosedOrder = orderService.findCancelledOrClosedOrderById(id);
		if (cancelledOrClosedOrder == null) {
			LOGGER.warn("Closed or cancelled order with id {} not found", id);
			return Response.status(Response.Status.NOT_FOUND)
					.entity(errorMessageToJSON(messageSource.getMessage("order.error.closed.cancelled.not.found.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		final Dish currentDish = orderService.getDishById(cancelledOrClosedOrder, dishId);
		if (currentDish == null) {
			LOGGER.warn("From order with id {}, dish id {} not found", id, dishId);
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(errorMessageToJSON(messageSource.getMessage("dish.error.404.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		final int newAmount = tableDishAmountForm.getAmount();
		orderService.setDoneDishAmount(cancelledOrClosedOrder, currentDish, newAmount);
		final Order updatedCancelledOrClosedOrder = orderService.findCancelledOrClosedOrderById(id); // To see dish stock updates

		LOGGER.info("Set new done dish amount {} with id {} from bill with id {}", newAmount, dishId, id);
		URI billsURI = URI.create(String.valueOf(uriInfo.getBaseUri()) +
				UriBuilder.fromResource(AdminApiController.class).build() +
				"/bills" +
				"/");
		final OrderDTO billDTO = new OrderDTO(updatedCancelledOrClosedOrder, billsURI);
		return Response.ok(billDTO).build();
	}
}
