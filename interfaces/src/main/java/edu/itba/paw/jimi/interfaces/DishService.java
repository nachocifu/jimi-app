package edu.itba.paw.jimi.interfaces;

import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.User;

import java.util.Collection;

public interface DishService {
    Dish findById(long id);

    /**
     * Create a new dish.
     * @param name the dish's name.
     * @param price the dish's price.
     * @return The created dish.
     */
    Dish create(String name, float price);

    /**
     * Modifies the value of stock of the dish.
     * @param dish The dish to be updated.
     * @param stock The new number of stock.
     * @return The new value of stock.
     */
    int modifyStock(Dish dish, int stock);


    /**
     * Returns all the dishes.
     * @return all the dishes.
     */
    Collection<Dish> findAll();
}
