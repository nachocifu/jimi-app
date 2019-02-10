package edu.itba.paw.jimi.interfaces.daos;

import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;

import java.sql.Timestamp;
import java.time.YearMonth;
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
	Collection<Order> findAll(int maxResults, int offset);

	/**
	 * Find all closed orders' total by month, ordered decreased by closed timestamp.
	 *
	 * @return List of said orders.
	 */
	Map<YearMonth, Double> getMonthlyOrderTotal();

	/**
	 * Find all cancelled or closed orders.
	 *
	 * @return List of said orders.
	 */
	Collection<Order> findCancelledOrClosedOrders(int maxResults, int offset);

	/**
	 * Calculates the total amount of cancelled or closed orders.
	 */
	int getTotalCancelledOrClosedOrders();

	/**
	 * Find all active orders.
	 *
	 * @return List of said orders.
	 */
	Collection<Order> getActiveOrders(int maxResults, int offset);

	int getTotalActiveOrders();

	/**
	 * Find all cancelled orders' total by month.
	 *
	 * @return List of said orders.
	 */
	Map<YearMonth, Integer> getMonthlyOrderCancelled();

	/**
	 * Finds all orders from the last given amount of minutes.
	 *
	 * @return A collection of said orders.
	 */
	Collection<Order> getOrdersFromLastMinutes(int minutes);

	/**
	 * Finds all undone dishes from active orders.
	 *
	 * @return A collection of said dishes.
	 */
	Map<Dish, Long> getAllUndoneDishesFromAllActiveOrders();
}
