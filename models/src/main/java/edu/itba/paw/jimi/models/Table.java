package edu.itba.paw.jimi.models;


public class Table {
	
	private long id;
	private int diners;
	private String name;
	private TableStatus status;
	private Order order;
	
	public Table(String name, long id, TableStatus status, Order order, int diners) {
		this.id = id;
		this.diners = diners;
		this.name = name;
		this.status = status;
		this.order = order;
	}
	
	/**
	 * See implementation of Order.addDish(Dish dish);
	 *
	 * @param dish The dish to be added.
	 * @return The resulting quantity of this dish in this order.
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
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public long getId() {
		return id;
	}
}
