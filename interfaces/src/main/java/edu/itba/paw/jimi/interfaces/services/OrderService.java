package edu.itba.paw.jimi.interfaces.services;

import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;

import java.util.Collection;


/**
 * This service does not create or fetches Orders form the Dao.
 * This is managed by the Table service because a orders gets created and managed by the table.
 * IMPORTANT!!!
 */
public interface OrderService {


    /**
     * Adds a dish to the order, if it is already there it increments the amount of said dish.
     * @param order The order in which to operate.
     * @param dish The dish to add.
     * @return The resulting amount of passed dish.
     */
    int addDish(Order order, Dish dish);

    /**
     * Removes a dish from the order, only one. If there was 2 of passed dish, 1 will remain.
     * To remove all dishes of the same kind see removeAllDish.
     * @param order The order in which to operate.
     * @param dish The dish to remove.
     * @return The resulting amount of passed dish.
     */
    int removeOneDish(Order order, Dish dish);


    /**
     * Removes all instances of a dish from the order.
     * @param order The order in which to operate.
     * @param dish The dish to remove completely.
     * @return the amount of dishes left of this dish. (should be 0).
     */
    int removeAllDish(Order order, Dish dish);
}
