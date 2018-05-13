package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.interfaces.exceptions.MaxStockException;
import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.DishStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

@Service
public class DishServiceImpl implements DishService {
	
	@Autowired
	private DishDao dishDao;
	
	private static final int MAX_STOCK = 1000000;
	
	public Dish findById(long id) {
		return dishDao.findById(id);
	}
	
	public Dish create(String name, float price) {
		Dish dish = dishDao.create(name, price, 0);
		return dish;
	}
	
	public int setStock(Dish dish, int stock) {
		if (stock < 0) {
			return 0;
		}
		
		if (stock >= MAX_STOCK) {
			throw new MaxStockException();
		}
		
		dish.setStock(stock);
		dishDao.update(dish);
		return dish.getStock();
	}
	
	public int increaseStock(Dish dish) {
		int stock = setStock(dish, dish.getStock() + 1);
		return stock;
	}
	
	public int decreaseStock(Dish dish) {
		if (dish.getStock() <= 0) {
			return 0;
		}
		
		int stock = setStock(dish, dish.getStock() - 1);
		return stock;
	}

	public Collection<Dish> findAll() {
		return dishDao.findAll();
	}
}
