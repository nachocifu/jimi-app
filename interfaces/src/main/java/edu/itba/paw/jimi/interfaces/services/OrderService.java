package edu.itba.paw.jimi.interfaces.services;

import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import edu.itba.paw.jimi.models.Utilities.QueryParams;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;


public interface OrderService {
	
	/**
	 * This creates a Order.
	 *
	 * @param status   The OrderStatus.
	 * @param openedAt The timestamp when this order became opened.
	 * @param closedAt The timestamp when this order became closed.
	 * @param diners   The diners of the order.
	 * @return
	 */
	Order create(OrderStatus status, Timestamp openedAt, Timestamp closedAt, int diners);
	
	/**
	 * Adds a dish to the order, if it is already there it increments the amount of said dish.
	 *
	 * @param order The order in which to operate.
	 * @param dish  The dish to add.
	 * @return The resulting amount of passed dish.
	 */
	int addDish(Order order, Dish dish);
	
	/**
	 * Adds n dishes to the order, if it is already there it increments the amount of said dish.
	 *
	 * @param order  The order in which to operate.
	 * @param dish   The dish to add.
	 * @param amount The amount to add.
	 * @return The resulting amount of passed dish.
	 */
	int addDishes(Order order, Dish dish, int amount);
	
	/**
	 * Removes a dish from the order, only one. If there was 2 of passed dish, 1 will remain.
	 * To remove all dishes of the same kind see removeAllDish.
	 *
	 * @param order The order in which to operate.
	 * @param dish  The dish to remove.
	 * @return The resulting amount of passed dish.
	 */
	int removeOneDish(Order order, Dish dish);
	
	
	/**
	 * Removes all instances of a dish from the order.
	 *
	 * @param order The order in which to operate.
	 * @param dish  The dish to remove completely.
	 * @return the amount of dishes left of this dish. (should be 0).
	 */
	int removeAllDish(Order order, Dish dish);
	
	
	/**
	 * Sets the timestamp for openedAt and changes the status open.
	 *
	 * @param order the order to open.
	 */
	void open(Order order);
	
	/**
	 * Sets the timestamp for closedAt and changes the status to closed.
	 *
	 * @param order
	 */
	void close(Order order);
	
	/**
	 * Sets the amount of dinners.
	 *
	 * @param order  The order to modify.
	 * @param diners The positive amount of diners.
	 * @return The amount of diners saved.
	 */
	int setDiners(Order order, int diners);

	/**
	 * Finds all closed orders.
	 *
	 * @return A collection of said orders.
	 */
	Collection<Order> findAll();


	/**
	 * Finds all closed orders.
	 *
	 * @return A collection of said orders.
	 */
	Collection<Order> findAll(QueryParams qp);

    /**
     * Finds all closed orders' total by month.
     *
     * @return A collection of said orders.TODO
     */
    Map getMonthlyOrderTotal();
}
