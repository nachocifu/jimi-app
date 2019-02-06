package edu.itba.paw.jimi.interfaces.exceptions;

import edu.itba.paw.jimi.models.OrderStatus;

public class OrderStatusException extends RuntimeException {

	private OrderStatus expected;
	private OrderStatus actual;

	public OrderStatusException(OrderStatus expected, OrderStatus actual) {
		super();
		this.expected = expected;
		this.actual = actual;
	}

	public OrderStatus getExpected() {
		return expected;
	}

	public OrderStatus getActual() {
		return actual;
	}

}
