package edu.itba.paw.jimi.models;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Order {

    private long id;
    private int diners;
    private Float total;
    private Map<Dish, Integer> dishes;
    private Timestamp openedAt;
    private Timestamp closedAt;
    private OrderStatus status;

    public Order() {
        this.dishes = new HashMap<Dish, Integer>();
    }

    public Order(long id, Timestamp openedAt, Timestamp closedAt, OrderStatus status, int diners, float total) {
        this.id = id;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.status = status;
        this.dishes = new HashMap<Dish, Integer>();
        this.diners = diners;
        this.total = total;
    }

    /**
     * This method sets the dish and amount overwriting the amount.
     *
     * @param dish   the dish to add.
     * @param amount the amount the dish is set to.
     * @return the resulting quantity of this dish in this order.
     */
    public Integer setDish(Dish dish, int amount) {
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

    public Timestamp getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(Timestamp openedAt) {
        this.openedAt = openedAt;
    }

    public Timestamp getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Timestamp closedAt) {
        this.closedAt = closedAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getDiners() {
        return diners;
    }

    public void setDiners(int diners) {
        this.diners = diners;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
