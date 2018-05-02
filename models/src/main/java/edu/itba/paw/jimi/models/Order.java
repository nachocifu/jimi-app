package edu.itba.paw.jimi.models;

import java.util.HashMap;
import java.util.Map;

public class Order {
    
    private long id;
    private Map<Dish, Integer> dishes;

    public Order() {
        this.dishes = new HashMap<Dish, Integer>();
    }
	
	public Order(long id) {
		this.id = id;
        this.dishes = new HashMap<Dish, Integer>();
	}

    /**
     * This method sets the dish and amount overwriting the amount.
     * @param dish the dish to add.
     * @param amount the amount the dish is set to.
     * @return the resulting quantity of this dish in this order.
     */
    public Integer setDish(Dish dish, int amount){
        return this.dishes.put(dish, amount);
    }


    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        for (Dish d : dishes.keySet()) {
            toPrint.append("dish: ").append(d.toString()).append(" * ").append(dishes.get(d)).append("; ");
        }
        return toPrint.toString();
    }
	
	public long getId() {
		return id;
	}
	
	public Map<Dish, Integer> getDishes() {
		return dishes;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return id == order.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
