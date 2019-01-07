package edu.itba.paw.jimi.interfaces.services;

import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Utilities.QueryParams;

import java.util.Collection;

public interface DishService {
	Dish findById(final long id);
	
	/**
	 * Create a new dish.
	 *
	 * @param name  the dish's name.
	 * @param price the dish's price.
	 * @return The created dish.
	 */
	Dish create(String name, float price);
	
	/**
	 * Sets the value of stock of the dish.
	 *
	 * @param dish  The dish to be updated.
	 * @param stock The new number of stock.
	 * @return The new value of stock.
	 */
	int setStock(Dish dish, int stock);
	
	/**
	 * Sets the value of price of the dish.
	 *
	 * @param dish  The dish to be updated.
	 * @param price The new price.
	 * @return The new value of stock.
	 */
	int setPrice(Dish dish, float price);
	
	/**
	 * Increases the value of stock of the dish by one.
	 *
	 * @param dish The dish to be updated.
	 * @return The new value of stock.
	 */
	int increaseStock(Dish dish);
	
	/**
	 * Decreases the value of stock of the dish by one.
	 *
	 * @param dish The dish to be updated.
	 * @return The new value of stock.
	 */
	int decreaseStock(Dish dish);
	
	/**
	 * Returns all the dishes.
	 *
	 * @return all the dishes.
	 */
	Collection<Dish> findAll();
	
	/**
	 * Returns all the dishes.
	 *
	 * @param qp the QueryParams.
	 * @return all the dishes.
	 */
	Collection<Dish> findAll(QueryParams qp);
	
	/**
	 * Returns all available, that is, stock greater than 0, dishes.
	 *
	 * @return all the dishes.
	 */
	Collection<Dish> findAllAvailable();
	
	int getTotalDishes();
	
	/**
	 * Set a new min stock value to dish
	 *
	 * @param dish     the dish to be updated
	 * @param minStock the new value
	 * @return the new min stock value
	 */
	int setMinStock(Dish dish, int minStock);
}
