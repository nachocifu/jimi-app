package edu.itba.paw.jimi.models;

public class Dish {
	
	
	private long id;
	private String name;
	private float price;
	private int stock;
	
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
}
