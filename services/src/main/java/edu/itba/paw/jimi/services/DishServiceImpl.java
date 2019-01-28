package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.interfaces.exceptions.MaxPriceException;
import edu.itba.paw.jimi.interfaces.exceptions.MaxStockException;
import edu.itba.paw.jimi.interfaces.exceptions.MinStockException;
import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.utils.QueryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;

@Service
@Transactional
public class DishServiceImpl implements DishService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DishServiceImpl.class);
	private static final float MAX_PRICE = 10000f;
	
	@Autowired
	private DishDao dishDao;
	
	private static final int MAX_STOCK = 1000000;
	
	@Override
	public Dish findById(final long id) {
		return dishDao.findById(id);
	}
	
	@Override
	public Dish create(String name, float price) {
		LOGGER.info("Create dish {} {}", name, price);
		return dishDao.create(name, price, 0);
	}
	
	@Override
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
	
	@Override
	public void setPrice(Dish dish, float price) {
		if (price < 0) {
			return;
		}
		
		if (price >= MAX_PRICE) {
			throw new MaxPriceException();
		}
		
		dish.setPrice(price);
		dishDao.update(dish);
		LOGGER.info("Updated dish price {}", dish);
	}
	
	@Override
	public int increaseStock(Dish dish) {
		return setStock(dish, dish.getStock() + 1);
	}
	
	@Override
	public int decreaseStock(Dish dish) {
		if (dish.getStock() <= 0) {
			return 0;
		}
		
		return setStock(dish, dish.getStock() - 1);
	}
	
	@Override
	public void setDiscontinued(Dish dish, boolean discontinued) {
		dish.setDiscontinued(discontinued);
		dishDao.update(dish);
		LOGGER.info("Updated dish discontinued {}", dish);
	}
	
	@Override
	public Collection<Dish> findAll() {
		Collection<Dish> dishes = dishDao.findAll();
		if (dishes != null)
			return dishes;
		else
			return new HashSet<Dish>();
	}
	
	@Override
	public Collection<Dish> findAll(QueryParams qp) {
		Collection<Dish> dishes = dishDao.findAll(qp);
		if (dishes != null)
			return dishes;
		else
			return new HashSet<Dish>();
	}
	
	@Override
	public Collection<Dish> findAllAvailable() {
		return dishDao.findAllAvailable();
	}
	
	@Override
	public Collection<Dish> findAllAvailable(QueryParams qp) {
		return dishDao.findAllAvailable(qp);
	}
	
	@Override
	public int getTotalDishes() {
		return dishDao.getTotalDishes();
	}
	
	@Override
	public int getDiscontinuedDishes() {
		return dishDao.findDiscontinuedDishes().size();
	}
	
	@Override
	public int setMinStock(Dish dish, int minStock) {
		if (minStock < 0)
			throw new MinStockException();
		dish.setMinStock(minStock);
		dishDao.update(dish);
		LOGGER.info("Updated dish minstock {}", dish);
		return dish.getMinStock();
	}
	
	@Override
	public Collection<Dish> findDishesMissingStock() {
		return dishDao.findDishesMissingStock();
	}
	
}
