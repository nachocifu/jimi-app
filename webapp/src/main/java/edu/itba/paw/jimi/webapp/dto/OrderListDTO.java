package edu.itba.paw.jimi.webapp.dto;

import edu.itba.paw.jimi.models.Order;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class OrderListDTO {
	private List<OrderDTO> orders;
	private PaginationDTO links;

	public OrderListDTO() {
	}

	public OrderListDTO(List<Order> orders, URI baseUri, PaginationDTO links) {
		this.orders = new LinkedList<>();

		for (Order order : orders)
			this.orders.add(new OrderDTO(order, baseUri));

		this.links = links;
	}

	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}

	public PaginationDTO getLinks() {
		return links;
	}

	public void setLinks(PaginationDTO links) {
		this.links = links;
	}
}
