package edu.itba.paw.jimi.webapp.utils;


import edu.itba.paw.jimi.interfaces.utils.PaginationService;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Component
public class PaginationHelper implements PaginationService {

	private static final String PREV = "prev";
	private static final String NEXT = "next";
	private static final String FIRST = "first";
	private static final String LAST = "last";
	private static final String PAGE = "page";

	public Link[] getPaginationLinks(UriInfo requestUriInfo, int page, int lastPage) {
		UriBuilder uriBuilder = requestUriInfo.getRequestUriBuilder();

		List<Link> links = new ArrayList<>();
		links.add(Link.fromUriBuilder(uriBuilder.replaceQueryParam(PAGE, 1)).rel(FIRST).build());
		links.add(Link.fromUriBuilder(uriBuilder.replaceQueryParam(PAGE, lastPage)).rel(LAST).build());

		if (page > 1)
			links.add(Link.fromUriBuilder(uriBuilder.replaceQueryParam(PAGE, page - 1)).rel(PREV).build());

		if (page < lastPage)
			links.add(Link.fromUriBuilder(uriBuilder.replaceQueryParam(PAGE, page + 1)).rel(NEXT).build());

		return links.toArray(new Link[0]);
	}

	public int getPageAsOneIfZeroOrLess(int page) {
		return (page < 1) ? 1 : page;
	}

	public int getPageSizeAsDefaultSizeIfOutOfRange(int pageSize, int defaultSize, int maxSize) {
		return (pageSize < 1 || pageSize > maxSize) ? defaultSize : pageSize;
	}
}
