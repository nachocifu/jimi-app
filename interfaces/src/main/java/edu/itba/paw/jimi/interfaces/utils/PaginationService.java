package edu.itba.paw.jimi.interfaces.utils;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;

public interface PaginationService {

	Link[] getPaginationLinks(UriInfo requestUriInfo, int page, int lastPage);

	int getPageAsOneIfZeroOrLess(int page);

	int getPageSizeAsDefaultSizeIfOutOfRange(int pageSize, int defaultSize, int maxSize);
}
