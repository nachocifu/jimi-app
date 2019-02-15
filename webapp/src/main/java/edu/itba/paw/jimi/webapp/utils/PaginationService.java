package edu.itba.paw.jimi.webapp.utils;

import edu.itba.paw.jimi.webapp.dto.PaginationDTO;

public interface PaginationService {

	PaginationDTO getPaginationDTO(int page, int maxPage);

	int getPageAsOneIfZeroOrLess(int page);

	int getPageSizeAsDefaultSizeIfOutOfRange(int pageSize, int defaultSize, int maxSize);

	int maxPage(final int total, final int pageSize);
}
