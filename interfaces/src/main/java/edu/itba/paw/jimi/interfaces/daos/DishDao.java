package edu.itba.paw.jimi.interfaces.daos;

import edu.itba.paw.jimi.models.Dish;

import java.util.Collection;

public interface DishDao {
	Dish findById(final long id);
	
	/**
	 * Create a new dish.
	 *
	 * @param name  the dish's name.
	 * @param price the dish's price.
	 * @param stock the dish's current stock quantity.
	 * @return The created dish.
	 */
	Dish create(String name, float price, int stock);
	
	/**
	 * Updates the dish.
	 *
	 * @param dish The dish to be updated.
	 * @return The number of dishes modified.
	 */
	int update(Dish dish);
	
	/**
	 * Returns all the dishes.
	 *
	 * @return all the dishes.
	 */
	Collection<Dish> findAll();
}
