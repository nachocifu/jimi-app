package edu.itba.paw.jimi.form;

import javax.validation.constraints.*;


public class DishForm {
	
	@Size(min = 1, max = 25)
	@Pattern(regexp = "^[a-zA-Z0-9_]+( [a-zA-Z0-9_]+)*$")
	private String name;
	
	@Digits(integer = 10, fraction = 2)
	private Float price;
	
	@DecimalMin(value = "1")
	@DecimalMax(value = "10000")
	private int stock;
	
	@DecimalMin(value = "0")
	@DecimalMax(value = "10000")
	private int minStock;
	
	private boolean discontinued;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Float getPrice() {
		return price;
	}
	
	public void setPrice(Float price) {
		this.price = price;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public int getStock() {
		return stock;
	}
	
	public int getMinStock() {
		return minStock;
	}
	
	public void setMinStock(int minStock) {
		this.minStock = minStock;
	}
	
	public boolean isDiscontinued() {
		
		return discontinued;
	}
	
	public void setDiscontinued(boolean discontinued) {
		this.discontinued = discontinued;
	}
}
