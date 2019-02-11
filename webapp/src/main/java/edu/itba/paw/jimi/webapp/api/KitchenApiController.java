package edu.itba.paw.jimi.webapp.api;

import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.webapp.dto.KitchenDTO;
import edu.itba.paw.jimi.webapp.utils.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response listKitchenTables(@QueryParam("page") @DefaultValue("1") Integer page,
	                                  @QueryParam("pageSize") @DefaultValue("" + DEFAULT_PAGE_SIZE) Integer pageSize) {
		page = paginationHelper.getPageAsOneIfZeroOrLess(page);
		pageSize = paginationHelper.getPageSizeAsDefaultSizeIfOutOfRange(pageSize, DEFAULT_PAGE_SIZE, MAX_PAGE_SIZE);
		Collection<Table> busyTables = tableService.getBusyTablesWithOrdersOrderedByOrderedAt(pageSize, (page - 1) * pageSize);
		KitchenDTO kitchen = new KitchenDTO(new LinkedList<>(busyTables), buildBaseURI(uriInfo));
		return Response.ok(kitchen)
				.links(paginationHelper.getPaginationLinks(uriInfo, page, busyTables.size()))
				.build();
	}
}
