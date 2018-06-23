package edu.itba.paw.jimi.models;


import org.springframework.context.annotation.Import;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "dishes")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dishes_dishid_seq")
    @SequenceGenerator(sequenceName = "dishes_dishid_seq", name = "dishes_dishid_seq", allocationSize = 1)
    @Column(name = "dishid")
    private long id;

    @Column(length = 100, nullable = false)
	private String name;

    @Column(precision = 10, scale = 2, nullable = false)
	private float price;

    @Column(precision = 10, nullable = false)
	private int stock;

    /* package */ Dish() {
    // Just for Hibernate, we love you!
    }

	public Dish(String name, float price, int stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public Dish(String name, float price, long id, int stock) {
		this.name = name;
		this.price = price;
		this.id = id;
		this.stock = stock;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		Dish dish = (Dish) o;
		
		return id == dish.id;
	}
	
	@Override
	public int hashCode() {
		return (int) (id ^ (id >>> 32));
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
	
	public int getStock() {
		return stock;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public DishStatus getStatus() {
		if (stock <= 0)
			return DishStatus.UNAVAILABLE;
		return DishStatus.AVAILABLE;
		
	}
	
}
