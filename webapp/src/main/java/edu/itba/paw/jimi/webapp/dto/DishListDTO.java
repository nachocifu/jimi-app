package edu.itba.paw.jimi.webapp.dto;

import edu.itba.paw.jimi.models.Dish;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class DishListDTO {
	private List<DishDTO> dishes;
	private PaginationDTO links;

	public PaginationDTO getLinks() {
		return links;
	}

	public void setLinks(PaginationDTO links) {
		this.links = links;
	}

	public DishListDTO() {
	}

	public DishListDTO(List<Dish> allDishes, URI baseUri, PaginationDTO links) {
		this.dishes = new LinkedList<>();

		for (Dish dish : allDishes)
			this.dishes.add(new DishDTO(dish, baseUri));

		this.links = links;
	}

	public List<DishDTO> getDishes() {
		return dishes;
	}

	public void setDishes(List<DishDTO> dishes) {
		this.dishes = dishes;
	}
}
