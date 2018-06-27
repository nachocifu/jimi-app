package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.exceptions.DinersSetOnNotOpenOrderException;
import edu.itba.paw.jimi.interfaces.exceptions.DishAddedToInactiveOrderException;
import edu.itba.paw.jimi.interfaces.exceptions.OrderStatusException;
import edu.itba.paw.jimi.interfaces.exceptions.StockHandlingException;
import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import edu.itba.paw.jimi.models.Utilities.QueryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private DishService dishService;

	/**
	 * Updates the total value of the object. Does not touch the DB!
	 *
	 * @param order The order to update.
	 */
	private void updateTotal(Order order) {
		
		float total = 0f;
		for (Map.Entry<Dish, Integer> d : order.getDishes().entrySet())
			total += d.getKey().getPrice() * d.getValue();

		order.setTotal(total);

	}
	
	public Order create(OrderStatus status, Timestamp openedAt, Timestamp closedAt, int diners) {
		LOGGER.info("Create order: {} | {} | {} | {}", status, openedAt, closedAt, diners);
		return orderDao.create(status, openedAt, closedAt, diners, 0f);
	}

	public int addDish(Order order, Dish dish) {
		LOGGER.info("Add dish: {} | {}", order, dish);
		return addDishes(order, dish, 1);
	}

	public int addDishes(Order order, Dish dish, int amount) {
		
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new DishAddedToInactiveOrderException();
		
		if (amount > dish.getStock())
			throw new StockHandlingException("Amount of dishes exceeds available dish stock.");
		
		int previousAmount;
		if (order.getDishes().containsKey(dish))
			previousAmount = order.getDishes().get(dish);
		else
			previousAmount = 0;
		
		order.setDish(dish, previousAmount + amount);
		updateTotal(order);
		orderDao.update(order);

		LOGGER.info("Updated order (add dishes): {}", order);
		
		// Update dish stock
		dishService.setStock(dish, dish.getStock() - amount);

		Order dbOrder = orderDao.findById(order.getId());

		if (dbOrder.getDishes().containsKey(dish))
			return dbOrder.getDishes().get(dish);
		else
			return 0;
	}

	public int removeOneDish(Order order, Dish dish) {
		
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new DishAddedToInactiveOrderException();
		
		int previousAmount;
		if (order.getDishes().containsKey(dish))
			previousAmount = order.getDishes().get(dish);
		else
			return 0;
		
		if (previousAmount == 0)
			return 0;
		
		order.setDish(dish, previousAmount - 1);
		updateTotal(order);
		orderDao.update(order);

		LOGGER.info("Updated order (remove one dish): {}", order);
		
		// Update dish stock
		dishService.setStock(dish, dish.getStock() + 1);
		
		Order dbOrder = orderDao.findById(order.getId());
		if (!dbOrder.getDishes().containsKey(dish))
			return 0;
		else
			return dbOrder.getDishes().get(dish);
	}

	public int removeAllDish(Order order, Dish dish) {
		
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new DishAddedToInactiveOrderException();

		// Update dish stock
		if (order.getDishes().containsKey(dish)) {
			int previousValue = order.getDishes().get(dish);
			dishService.setStock(dish, dish.getStock() + previousValue);
		}

		order.setDish(dish, 0);
		updateTotal(order);
		orderDao.update(order);

		LOGGER.info("Updated order (remove all dish): {}", order);
		
		Order dbOrder = orderDao.findById(order.getId());
		if (!dbOrder.getDishes().containsKey(dish))
			return 0;
		else
			return dbOrder.getDishes().get(dish);
	}

	public int setDiners(Order order, int diners) {
		Order o = orderDao.findById(order.getId());
		
		if (!o.getStatus().equals(OrderStatus.OPEN))
			throw new DinersSetOnNotOpenOrderException();
		
		if (diners >= 0) {
			o.setDiners(diners);
			order.setDiners(diners);
			orderDao.update(o);

			LOGGER.info("Updated order (set diners): {}", order);
			
			return diners;
		}
		return 0;
	}

	public void open(Order order) {
		
		if (!order.getStatus().equals(OrderStatus.INACTIVE))
			throw new OrderStatusException(OrderStatus.INACTIVE, order.getStatus());

		order.setOpenedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		order.setStatus(OrderStatus.OPEN);
		orderDao.update(order);

		LOGGER.info("Opened order {}", order);
	}

	public void close(Order order) {
		
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new OrderStatusException(OrderStatus.OPEN, order.getStatus());

		order.setStatus(OrderStatus.CLOSED);
		order.setClosedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		orderDao.update(order);

		LOGGER.info("Closed order {}", order);
	}

	public void cancel(Order order) {
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new OrderStatusException(OrderStatus.OPEN, order.getStatus());

		order.setStatus(OrderStatus.CANCELED);
		order.setClosedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		orderDao.update(order);

		LOGGER.info("Canceled order {}", order);
	}

	public Collection<Order> findAll() {
		
		Collection<Order> orders = orderDao.findAll();
		if (orders != null)
			return orders;
		else
			return new HashSet<Order>();
	}

	public Collection<Order> findAll(QueryParams qp) {
		Collection<Order> orders = orderDao.findAll(qp);
		if (orders != null)
			return orders;
		else
			return new HashSet<Order>();
	}

	public Map getMonthlyOrderTotal() {
		return orderDao.getMonthlyOrderTotal();
	}
	
}
