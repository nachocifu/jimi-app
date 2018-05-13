package edu.itba.paw.jimi.models;


public class Table {
	
	private long id;
	private String name;
	private TableStatus status;
	private Order order;
	
	public Table(String name, long id, TableStatus status, Order order) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.order = order;
	}
	
	/**
	 * See implementation of Order.setDish(Dish dish, int n);
	 *
	 * @param dish The dish to be set.
	 * @return The resulting quantity of this dish in this order.
	 */

	//TODO: Me parece que hay que sacar esto. Se puede hacer table.getOrder.setDish();
	public Integer setDish(Dish dish, int n) {
		return order.setDish(dish, n);
	}
	
	@Override
	public String toString() {
		return "Table{" +
				", name='" + name + '\'' +
				", status=" + status +
				", order=" + order +
				'}';
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(TableStatus status) {
		this.status = status;
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
