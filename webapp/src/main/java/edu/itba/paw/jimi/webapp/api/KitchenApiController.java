package edu.itba.paw.jimi.webapp.api;

import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import edu.itba.paw.jimi.webapp.dto.TableListDTO;
import edu.itba.paw.jimi.webapp.utils.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;

@Controller
@Path("kitchen")
public class KitchenApiController extends BaseApiController {

	private static final int DEFAULT_PAGE_SIZE = 10;
	private static final int MAX_PAGE_SIZE = 20;

	@Autowired
	private TableService tableService;

	@Autowired
	private PaginationHelper paginationHelper;

	@Context
	private UriInfo uriInfo;

	@GET
	@Path("/busyTables")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response listKitchenTables(@QueryParam("page") @DefaultValue("1") Integer page,
	                                  @QueryParam("pageSize") @DefaultValue("" + DEFAULT_PAGE_SIZE) Integer pageSize) {
		page = paginationHelper.getPageAsOneIfZeroOrLess(page);
		pageSize = paginationHelper.getPageSizeAsDefaultSizeIfOutOfRange(pageSize, DEFAULT_PAGE_SIZE, MAX_PAGE_SIZE);
		int maxPage = paginationHelper.maxPage(tableService.getNumberOfTablesWithState(TableStatus.BUSY), pageSize);
		Collection<Table> busyTables = tableService.getBusyTablesWithOrdersOrderedByOrderedAt(pageSize, (page - 1) * pageSize);
		URI tableURI = URI.create(String.valueOf(uriInfo.getBaseUri()) +
				UriBuilder.fromResource(TableApiController.class).build() +
				"/");
		TableListDTO kitchenBusyTables = new TableListDTO(new LinkedList<>(busyTables), tableURI);
		return Response.ok(kitchenBusyTables)
				.links(paginationHelper.getPaginationLinks(uriInfo, page, maxPage))
				.build();
	}
}
