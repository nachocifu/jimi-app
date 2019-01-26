package edu.itba.paw.jimi.interfaces.daos;

import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import edu.itba.paw.jimi.models.Utilities.QueryParams;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;

public interface OrderDao {
	
	/**
	 * Returns the Order with the passed id.
	 *
	 * @param id the id to look for.
	 * @return The dish with the passed id.
	 */
	Order findById(final long id);
	
	/**
	 * Create an order.
	 *
	 * @return An order.
	 */
	Order create(OrderStatus status, Timestamp openedAt, Timestamp closedAt, int diners, float total);
	
	/**
	 * Updates the order.
	 *
	 * @param order The order to be updated.
	 */
	void update(Order order);
	
	/**
	 * Find all closed orders, ordered decreased by closed timestamp.
	 *
	 * @return List of said orders.
	 */
	Collection<Order> findAll();
	
	/**
	 * Find all closed orders, ordered decreased by closed timestamp.
	 *
	 * @return List of said orders.
	 */
	Collection<Order> findAll(QueryParams qp);
	
	/**
	 * Find all closed orders' total by month, ordered decreased by closed timestamp.
	 *
	 * @return List of said orders.
	 */
	Map getMonthlyOrderTotal();
	
	Collection<Order> findAllRelevant(QueryParams qp);
	
	int getTotalRelevantOrders();
	
	Collection<Order> getActiveOrders();
	
	int getTotalActiveOrders();
	
	Map getMonthlyOrderCancelled();
	
	/**
	 * Finds all urgent orders.
	 * An order is urgent when it has been opened for more than 30 minutes.
	 *
	 * @return A collection of said orders.
	 */
	Collection<Order> get30MinutesWaitOrders();
	
	
	/**
	 * Finds all undone dishes from active orders.
	 *
	 * @return A collection of said dishes.
	 */
	Map<Dish, Long> getAllUndoneDishesFromAllActiveOrders();
}
