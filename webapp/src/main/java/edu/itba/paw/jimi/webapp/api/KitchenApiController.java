package edu.itba.paw.jimi.webapp.api;

import javax.ws.rs.*;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import edu.itba.paw.jimi.models.utils.QueryParams;
import edu.itba.paw.jimi.webapp.dto.KitchenDTO;
import edu.itba.paw.jimi.webapp.utils.PaginationHelper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

@Controller
@Path("kitchen")
public class KitchenApiController extends BaseApiController{

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(KitchenApiController.class);

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 20;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    @Qualifier(value = "userOrderService")
    private OrderService orderService;

    @Autowired
    private DishService dishService;

    @Autowired
    private TableService tableService;

    @Autowired
    private PaginationHelper paginationHelper;

    @Context
    private UriInfo uriInfo;

    @PUT
    @Path("/{orderId}/{dishId}")
    public Response done(@PathParam("orderId") final long orderId,
                         @PathParam("dishId") final long dishId) {

        final Order order = orderService.findById(orderId);
        if(order == null) {
            logger.warn("Order with id {} not found.", orderId);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(messageSource.getMessage("order.error.404.body",null,LocaleContextHolder.getLocale()))
                    .build();
        }

        final Dish dish = dishService.findById(dishId);
        if (dish == null) {
            logger.warn("Dish with id {} not found", dishId);
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(messageSource.getMessage("dish.error.404.body", null, LocaleContextHolder.getLocale()))
                    .build();
        }

        orderService.setDishAsDone(order,dish);
        return Response.ok().build();
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response view(@QueryParam("page") @DefaultValue("1") Integer page,
                         @QueryParam("pageSize") @DefaultValue("" + DEFAULT_PAGE_SIZE) Integer pageSize) {

        page = paginationHelper.getPageAsOneIfZeroOrLess(page);
        pageSize = paginationHelper.getPageSizeAsDefaultSizeIfOutOfRange(pageSize, DEFAULT_PAGE_SIZE, MAX_PAGE_SIZE);

        Map totalDishes = orderService.getAllUndoneDishesFromAllActiveOrders(new QueryParams((page - 1) * pageSize, pageSize));
        Collection<Table> busyTables = tableService.findTablesWithStatus(TableStatus.BUSY, new QueryParams((page - 1) * pageSize, pageSize));
        Collection<Table> urgentTables = tableService.getTablesWithOrdersFromLastMinutes(30, new QueryParams((page - 1) * pageSize, pageSize));

        KitchenDTO kitchen = new KitchenDTO(new LinkedList(busyTables), new LinkedList(urgentTables), new LinkedList(totalDishes.values()), buildBaseURI(uriInfo));
        return Response.ok(kitchen)
                .links(paginationHelper.getPaginationLinks(uriInfo, page, dishService.getTotalDishes()))
                .build();
    }
}
