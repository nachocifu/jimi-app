package edu.itba.paw.jimi.webapp.utils;


import edu.itba.paw.jimi.webapp.dto.PaginationDTO;
import org.springframework.stereotype.Component;

@Component
public class PaginationHelper implements PaginationService {

	@Override
	public PaginationDTO getPaginationDTO(int page, int maxPage) {

		PaginationDTO links = new PaginationDTO();

		links.setFirst(1);
		links.setLast(maxPage);

		if (page > 1)
			links.setPrev(page - 1);

		if (page < maxPage)
			links.setNext(page + 1);

		return links;
	}

	@Override
	public int getPageAsOneIfZeroOrLess(int page) {
		return (page < 1) ? 1 : page;
	}

	@Override
	public int getPageSizeAsDefaultSizeIfOutOfRange(int pageSize, int defaultSize, int maxSize) {
		return (pageSize < 1 || pageSize > maxSize) ? defaultSize : pageSize;
	}

	@Override
	public int maxPage(final int total, final int pageSize) {
		return (int) Math.ceil((float) total / pageSize);
	}
}
