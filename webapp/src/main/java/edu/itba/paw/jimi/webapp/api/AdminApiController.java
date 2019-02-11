package edu.itba.paw.jimi.webapp.api;

import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.StatsService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.webapp.dto.OrderDTO;
import edu.itba.paw.jimi.webapp.dto.OrderListDTO;
import edu.itba.paw.jimi.webapp.dto.StatsDTO;
import edu.itba.paw.jimi.webapp.utils.PaginationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;

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
}
