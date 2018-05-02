package edu.itba.paw.jimi.interfaces;

import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;


/**
 * The purpose of this Dao is to insert and update information about the OrderItem table.
 * Not to get individual or several items because this is done on the OrderDao implementation.
 * This is basically a mapping of the Map<Dish, Integer> inside Order to de DB.
 */
public interface OrderItemDao {
	
	/**
	 * Create an order item if it did not exist for an order.
	 * @param order The order to have the new/updated order item.
	 * @param dish The dish to add/update.
	 * @param quantity The quantity to add/update.
	 */
	void createOrUpdate(Order order, Dish dish, int quantity);

	/**
	 * Deletes the order item from the order.
	 * @param order The order from which you wish to delete a dish.
	 * @param dish The dish to delete.
	 */
	void delete(Order order, Dish dish);
}
