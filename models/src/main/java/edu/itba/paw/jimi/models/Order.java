package edu.itba.paw.jimi.models;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private Map<Dish, Integer> dishes;

    public Order() {
        this.dishes = new HashMap<Dish, Integer>();
    }

    /**
     * This method adds a a dish to the order and returns the amount of quantity of this dish on this order.
     * @param dish the dish to add.
     * @return the count of this dish in this order.
     */
    public Integer addDish(Dish dish){
        if (!this.dishes.containsKey(dish))
            return this.dishes.put(dish, 0);
        else{
            Integer previousCount = this.dishes.get(dish);
            return this.dishes.put(dish, previousCount + 1);
        }
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        for (Dish d : dishes.keySet()) {
            toPrint.append("dish: ").append(d.toString()).append(" * ").append(dishes.get(d)).append("; ");
        }
        return toPrint.toString();
    }
}
