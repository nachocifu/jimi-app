package edu.itba.paw.jimi.interfaces.daos;

import edu.itba.paw.jimi.models.Order;

public interface OrderDao {

    /**
     * Returns the Order with the passed id.
     * @param id the id to look for.
     * @return The dish with the passed id.
     */
    Order findById(long id);
    
    /**
     * Create an order.
     * @return An order.
     */
    Order create();

    /**
     * Updates the order.
     * @param order The order to be updated.
     */
    void update(Order order);
    
}
