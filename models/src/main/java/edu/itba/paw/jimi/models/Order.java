package edu.itba.paw.jimi.models;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Entity
//@SecondaryTable(name = "orders_items")
@javax.persistence.Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_orderid_seq")
    @SequenceGenerator(sequenceName = "orders_orderid_seq", name = "orders_orderid_seq", allocationSize = 1)
//    @Column(name = "orderid")
	private long id;

    @Column(precision = 10, nullable = false)
	private int diners;

    @Column(precision = 10, scale = 2, nullable = false)
	private Float total;

//    @Column(name="dishid")
@ElementCollection(fetch = FetchType.EAGER)
//    @OneToMany(targetEntity = Dish.class)
//    @Transient
	private Map<Dish, Integer> dishes;

    @Temporal(TemporalType.DATE)
	private Date openedAt;

    @Temporal(TemporalType.DATE)
	private Date closedAt;

    @Enumerated(EnumType.ORDINAL) //TODO: Esto no esta bien, guardamos en la base el id, no el string.
	private OrderStatus status;
	
	public Order() {
		this.dishes = new HashMap<Dish, Integer>();
	}
	
	public Order(long id, Date openedAt, Date closedAt, OrderStatus status, int diners, float total) {
		this.id = id;
		this.openedAt = openedAt;
		this.closedAt = closedAt;
		this.status = status;
		this.dishes = new HashMap<Dish, Integer>();
		this.diners = diners;
		this.total = total;
	}
    public Order(Date openedAt, Date closedAt, OrderStatus status, int diners, float total) {
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.status = status;
        this.dishes = new HashMap<Dish, Integer>();
        this.diners = diners;
        this.total = total;
    }

    public void setDishes(Map<Dish, Integer> dishes) {
        this.dishes = dishes;
    }

    /**
	 * This method sets the dish and amount overwriting the amount.
	 *
	 * @param dish   the dish to add.
	 * @param amount the amount the dish is set to.
	 * @return the resulting quantity of this dish in this order.
	 */
	public Integer setDish(Dish dish, int amount) {
//		return this.dishes.put(dish, amount);
        if (amount > 0) {
            this.dishes.put(dish, amount);
            return amount;
        }else {
            this.dishes.remove(dish);
            return 0;
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
}
