package edu.itba.paw.jimi.interfaces.services;

import edu.itba.paw.jimi.models.Dish;

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
	 * Sets the name of the dish.
	 *
	 * @param dish The dish to be updated.
	 * @param name The new name of stock.
	 */
	void setName(Dish dish, String name);

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
	 */
	void setPrice(Dish dish, float price);

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
	 * Toggles a dish to be discontinued or not.
	 *
	 * @param dish         the dish to be updated
	 * @param discontinued the new value
	 */
	void setDiscontinued(Dish dish, boolean discontinued);

	/**
	 * Returns all the dishes paginated.
	 *
	 * @return all the dishes paginated.
	 */
	Collection<Dish> findAll(int pageSize, int offset, boolean filterAvailable);

	/**
	 * Returns all available paginated dishes, that is, stock greater than 0.
	 *
	 * @return all the available dishes paginated.
	 */
	Collection<Dish> findAllAvailable(int pageSize, int offset);

	/**
	 * Returns all paginated dishes with missing stock, that is, their stock lower than their
	 * minimum stock.
	 *
	 * @return dishes missing stock dishes paginated.
	 */
	Collection<Dish> findDishesMissingStock(int pageSize, int offset);

	/**
	 * Returns the amount of all dishes in the database.
	 *
	 * @return a positive integer.
	 */
	int getTotalDishes();

	/**
	 * Calculates discontinued dishes.
	 *
	 * @return count of said dishes.
	 */
	int getDiscontinuedDishes();

	/**
	 * Set a new min stock value to dish
	 *
	 * @param dish     the dish to be updated
	 * @param minStock the new value
	 * @return the new min stock value
	 */
	int setMinStock(Dish dish, int minStock);

	/**
	 * Returns all dishes with stock less than limit
	 *
	 * @return number of dishes with stock less than limit.
	 */
	int getAllDishesWithStockLessThanLimit(int limit);
}
