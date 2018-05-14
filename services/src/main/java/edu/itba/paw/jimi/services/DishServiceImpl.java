package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.interfaces.exceptions.MaxStockException;
import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.models.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DishServiceImpl implements DishService {
	
	@Autowired
	private DishDao dishDao;
	
	private static final int MAX_STOCK = 1000000;
	
	public Dish findById(final long id) {
		return dishDao.findById(id);
	}
	
	public Dish create(String name, float price) {
		return dishDao.create(name, price, 0);
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
		return setStock(dish, dish.getStock() + 1);
	}
	
	public int decreaseStock(Dish dish) {
		if (dish.getStock() <= 0) {
			return 0;
		}
		
		return setStock(dish, dish.getStock() - 1);
	}
	
	public Collection<Dish> findAll() {
		return dishDao.findAll();
	}
}
