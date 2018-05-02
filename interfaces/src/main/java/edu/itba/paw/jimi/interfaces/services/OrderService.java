package edu.itba.paw.jimi.interfaces.services;

import edu.itba.paw.jimi.models.Order;

import java.util.Collection;

public interface OrderService {

    /**
     * Create a new empty order.
     * @return The created order.
     */
    Order create();
    
    
}
