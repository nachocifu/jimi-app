package edu.itba.paw.jimi.interfaces.exceptions;

import edu.itba.paw.jimi.models.OrderStatus;

public class OrderStatusException extends RuntimeException {
    public OrderStatusException(OrderStatus expected, OrderStatus got) {
        super("Expected status was: " + expected.toString() + ", and got status: " + got.toString());
    }
}
