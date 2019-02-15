package edu.itba.paw.jimi.webapp.dto;

public class PaginationDTO {

	private Integer prev;
	private Integer next;
	private Integer first;
	private Integer last;
	private Integer page;

	public PaginationDTO() {
	}

	public PaginationDTO(Integer prev, Integer next, Integer first, Integer last, Integer page) {
		this.prev = prev;
		this.next = next;
		this.first = first;
		this.last = last;
		this.page = page;
	}

	public Integer getPrev() {
		return prev;
	}

	public void setPrev(Integer prev) {
		this.prev = prev;
	}

	public Integer getNext() {
		return next;
	}

	public void setNext(Integer next) {
		this.next = next;
	}

	public Integer getFirst() {
		return first;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}

	public Integer getLast() {
		return last;
	}

	public void setLast(Integer last) {
		this.last = last;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
