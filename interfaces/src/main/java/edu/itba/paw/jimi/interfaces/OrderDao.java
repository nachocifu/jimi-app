package edu.itba.paw.jimi.interfaces;

import edu.itba.paw.jimi.models.Order;

public interface OrderDao {
    
    Order findById(long id);
    
    /**
     * Create an order.
     * @return An order.
     */
    Order create();

    /**
     * Updates the order.
     * @param order The order to be updated.
     * @return Booleand for success or failure.
     */
    Boolean update(Order order);
    
}
