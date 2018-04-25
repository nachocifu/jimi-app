package edu.itba.paw.jimi.interfaces;

import edu.itba.paw.jimi.models.Dish;

public interface DishDao {
    Dish findById(long id);

    /**
     * Create a new dish.
     * @param name the dish's name.
     * @param price the dish's price.
     * @param stock the dish's current stock quantity.
     * @return The created dish.
     */
    Dish create(String name, float price, int stock);
}
