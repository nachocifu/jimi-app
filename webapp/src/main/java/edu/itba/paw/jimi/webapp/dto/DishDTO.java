package edu.itba.paw.jimi.webapp.dto;

import edu.itba.paw.jimi.models.Dish;

import java.net.URI;

public class DishDTO {

	private int id;
	private String name;
	private float price;
	private int stock;
	private int minStock;
	private boolean discontinued;
	private URI uri;

	public DishDTO() {
	}

	public DishDTO(Dish dish, URI baseURI) {
		this.id = dish.getId();
		this.name = dish.getName();
		this.price = dish.getPrice();
		this.stock = dish.getStock();
		this.minStock = dish.getMinStock();
		this.discontinued = dish.isDiscontinued();
		this.uri = baseURI.resolve(String.valueOf(this.id));
	}

	public DishDTO(Dish dish) {
		this.id = dish.getId();
		this.name = dish.getName();
		this.price = dish.getPrice();
		this.stock = dish.getStock();
		this.minStock = dish.getMinStock();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}
}
