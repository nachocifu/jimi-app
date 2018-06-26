package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.interfaces.exceptions.MaxStockException;
import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.models.Dish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Service
public class DishServiceImpl implements DishService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DishServiceImpl.class);
	
	@Autowired
	private DishDao dishDao;
	
	private static final int MAX_STOCK = 1000000;

	public Dish findById(final long id) {
		return dishDao.findById(id);
	}

	@Transactional
	public Dish create(String name, float price) {
		LOGGER.info("Create dish {} {}", name, price);
		return dishDao.create(name, price, 0);
	}

	@Transactional
	public int setStock(Dish dish, int stock) {
		if (stock < 0) {
			return 0;
		}

		if (stock >= MAX_STOCK) {
			throw new MaxStockException();
		}

		dish.setStock(stock);
		dishDao.update(dish);
		LOGGER.info("Updated dish stock {}", dish);
		return dish.getStock();
	}

	@Transactional
	public int increaseStock(Dish dish) {
		return setStock(dish, dish.getStock() + 1);
	}

	@Transactional
	public int decreaseStock(Dish dish) {
		if (dish.getStock() <= 0) {
			return 0;
		}

		return setStock(dish, dish.getStock() - 1);
	}

	public Collection<Dish> findAll() {
		Collection<Dish> dishes = dishDao.findAll();
		if (dishes != null)
			return dishes;
		else
			return new HashSet<Dish>();
	}

	public Collection<Dish> findAllAvailable() {
		
		Collection<Dish> dishes = findAll();
		
		Collection<Dish> availableDishes = new ArrayList<Dish>();
		
		for (Dish d : dishes) {
			if (d.getStock() > 0) {
				availableDishes.add(d);
			}
		}
		
		return availableDishes;
		
	}
}
