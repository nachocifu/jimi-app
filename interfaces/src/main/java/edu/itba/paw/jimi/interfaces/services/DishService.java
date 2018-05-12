package edu.itba.paw.jimi.interfaces.services;

import edu.itba.paw.jimi.models.Dish;

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
     * Sets the value of stock of the dish.
     * @param dish The dish to be updated.
     * @param stock The new number of stock.
     * @return The new value of stock.
     */
    int setStock(Dish dish, int stock);
    
    /**
     * Increases the value of stock of the dish by one.
     * @param dish The dish to be updated.
     * @return The new value of stock.
     */
    int increaseStock(Dish dish);
    
    /**
     * Decreases the value of stock of the dish by one.
     * @param dish The dish to be updated.
     * @return The new value of stock.
     */
    int decreaseStock(Dish dish);

    /**
     * Returns all the dishes.
     * @return all the dishes.
     */
    Collection<Dish> findAll();
}
