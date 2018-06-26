package edu.itba.paw.jimi.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class DishForm {
	
	@Size(min = 1, max = 80)
	@Pattern(regexp = "^[a-zA-Z0-9_]+( [a-zA-Z0-9_]+)*$")
	private String name;
	
	private Float price;
	
	private int stock;
	
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
}
