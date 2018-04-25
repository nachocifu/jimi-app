package edu.itba.paw.jimi.interfaces;

import edu.itba.paw.jimi.models.Dish;

public interface DishService {
    Dish findById(long id);

    /**
     * Create a new dish.
     * @param name the dish's name.
     * @param price the dish's price.
     * @return The created dish.
     */
    Dish create(String name, float price);
}
