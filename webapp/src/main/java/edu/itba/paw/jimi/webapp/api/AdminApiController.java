package edu.itba.paw.jimi.webapp.api;

import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.webapp.dto.OrderListDTO;
import edu.itba.paw.jimi.webapp.utils.PaginationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.LinkedList;

@Path("admin")
@Controller
public class AdminApiController extends BaseApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminApiController.class);

	@Autowired
	private OrderService orderService;

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
		final Collection<Order> closedOrders = orderService.findCancelledOrClosedOrders(pageSize, (page - 1) * pageSize);
		return Response.ok(new OrderListDTO(new LinkedList<>(closedOrders), buildBaseURI(uriInfo)))
				.links(paginationHelper.getPaginationLinks(uriInfo, page, orderService.getTotalCancelledOrClosedOrders()))
				.build();
	}
}
