package edu.itba.paw.jimi.models;


public class Table {
    private int diners;
    private String name;
    private TableStatus status;
    private Order order;

    public Table(String name) {
        this.name = name;
        this.diners = 0;
        this.status = TableStatus.Free;
        this.order = new Order();
    }

    /**
     * See implementation of Order.addDish(Dish dish);
     * @param dish
     * @return
     */
    public Integer addDish(Dish dish) {
        return order.addDish(dish);
    }

    @Override
    public String toString() {
        return "Table{" +
                "diners=" + diners +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", order=" + order +
                '}';
    }

    public int getDiners() {
        return diners;
    }

    public String getName() {
        return name;
    }

    public TableStatus getStatus() {
        return status;
    }

    public Order getOrder() {
        return order;
    }
}
