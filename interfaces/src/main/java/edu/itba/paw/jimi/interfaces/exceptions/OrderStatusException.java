package edu.itba.paw.jimi.interfaces.exceptions;

import edu.itba.paw.jimi.models.OrderStatus;

public class OrderStatusException extends RuntimeException {
    public OrderStatusException(OrderStatus expected, OrderStatus got) {
        super("Expected status was: " + expected.name() + ", and got status: " + got.name());
    }
}
