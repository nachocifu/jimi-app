package edu.itba.paw.jimi.models;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_orderid_seq")
	@SequenceGenerator(sequenceName = "orders_orderid_seq", name = "orders_orderid_seq", allocationSize = 1)
	private long id;
	
	@Column(precision = 10, nullable = false)
	private int diners;
	
	@Column(precision = 10, scale = 2, nullable = false)
	private Float total;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Map<Dish, DishData> unDoneDishes;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private Map<Dish, Integer> doneDishes;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date openedAt;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date closedAt;
	
	@Enumerated(EnumType.ORDINAL)
	private OrderStatus status;
	
	public Order() {
		this.unDoneDishes = new HashMap<Dish, DishData>();
		this.doneDishes = new HashMap<Dish, Integer>();
	}
	
	public Order(long id, Date openedAt, Date closedAt, OrderStatus status, int diners, float total) {
		this.id = id;
		this.openedAt = openedAt;
		this.closedAt = closedAt;
		this.status = status;
		this.unDoneDishes = new HashMap<Dish, DishData>();
		this.doneDishes = new HashMap<Dish, Integer>();
		this.diners = diners;
		this.total = total;
	}
	
	public Order(Date openedAt, Date closedAt, OrderStatus status, int diners, float total) {
		this.openedAt = openedAt;
		this.closedAt = closedAt;
		this.status = status;
		this.unDoneDishes = new HashMap<Dish, DishData>();
		this.doneDishes = new HashMap<Dish, Integer>();
		this.diners = diners;
		this.total = total;
	}
	
	public void setDishes(Map<Dish, DishData> dishes) {
		this.unDoneDishes = dishes;
	}
	
	/**
	 * This method sets the dish and amount overwriting the amount.
	 *
	 * @param dish   the dish to add.
	 * @param amount the amount the dish is set to.
	 * @return the resulting quantity of this dish in this order.
	 */
	public Integer setUndoneDish(Dish dish, int amount) {
		if (amount > 0) {
			if (!this.unDoneDishes.containsKey(dish))
				this.unDoneDishes.put(dish, new DishData(amount));
			DishData currentDishData = this.unDoneDishes.get(dish);
			currentDishData.setAmount(amount);
			this.unDoneDishes.put(dish, currentDishData);
			return amount;
		} else {
			this.unDoneDishes.remove(dish);
			return 0;
		}
	}
	
	public Integer setDoneDish(Dish dish, int amount) {
		if (amount > 0) {
			this.doneDishes.put(dish, amount);
			return amount;
		} else {
			this.doneDishes.remove(dish);
			return 0;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder toPrint = new StringBuilder();
		for (Dish d : unDoneDishes.keySet()) {
			toPrint.append("dish: ").append(d.toString()).append(" * ").append(unDoneDishes.get(d)).append("; ");
		}
		for (Dish d : doneDishes.keySet()) {
			toPrint.append("done dish: ").append(d.toString()).append(" * ").append(doneDishes.get(d)).append("; ");
		}
		return toPrint.toString();
	}
	
	public long getId() {
		return id;
	}
	
	public Map<Dish, DishData> getDishes() {
		Map<Dish, DishData> undone = new HashMap<Dish, DishData>(this.unDoneDishes);
		Map<Dish, Integer> done = new HashMap<Dish, Integer>(this.doneDishes);
		for (Dish d : done.keySet()) {
			if (undone.containsKey(d)) {
				undone.put(d, new DishData(undone.get(d).getAmount() + done.get(d)));
			} else {
				undone.put(d, new DishData(done.get(d)));
			}
		}
		return undone;
	}
	
	public Map<Dish, DishData> getUnDoneDishes() {
		return unDoneDishes;
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
	
	public Date getOpenedAt() {
		return openedAt;
	}
	
	public void setOpenedAt(Date openedAt) {
		this.openedAt = openedAt;
	}
	
	public Date getClosedAt() {
		return closedAt;
	}
	
	public void setClosedAt(Date closedAt) {
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
	
	public Map<Dish, Integer> getDoneDishes() {
		return doneDishes;
	}
}
