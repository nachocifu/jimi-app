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
     * This method adds a a dish to the order and returns the amount of quantity of this dish on this order.
     * @param dish the dish to add.
     * @return the resulting quantity of this dish in this order.
     */
    public Integer addDish(Dish dish){
        if (!this.dishes.containsKey(dish))
            return this.dishes.put(dish, 1);
        else{
            Integer previousCount = this.dishes.get(dish);
            return this.dishes.put(dish, previousCount + 1);
        }
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


    /**
     * This method removes 1 of the dish form the order.
     * If the dish was at 1 then the dish it is not removed but marked as 0.
     * If the dish was at n then it is kept and amount decreased by 1.
     * @param dish the dish to modify.
     * @return the remaining amount.
     */
    public Integer removeOneDish(Dish dish){
        if (!this.dishes.containsKey(dish))
            return 0;
        else{
            Integer previousCount = this.dishes.get(dish);
            if (previousCount == 1) {
                this.dishes.put(dish, 0);
                return 0;
            }else
                return this.dishes.put(dish, previousCount - 1);
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
