package edu.itba.paw.jimi.interfaces.daos;

import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;

public interface OrderDao {
	
	/**
	 * Returns the Order with the passed id.
	 * @param id the id to look for.
	 * @return The dish with the passed id.
	 */
	Order findById(final long id);
	
	/**
	 * Create an order.
	 * @return An order.
	 */
	Order create(OrderStatus status, Timestamp openedAt, Timestamp closedAt, int diners, float total);
	
	/**
	 * Updates the order.
	 * @param order The order to be updated.
	 */
	void update(Order order);

	/**
	 * Find all closed orders, ordered decreased by closed timestamp.
	 * @return List of said orders.
	 */
	Collection<Order> findAll();

	/**
	 * Find all closed orders' total by month, ordered decreased by closed timestamp.
	 *
	 * @return List of said orders.TODO
	 */
	Map getMonthlyOrderTotal();
}
