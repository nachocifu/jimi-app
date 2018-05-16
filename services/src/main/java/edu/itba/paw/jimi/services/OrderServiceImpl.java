package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.exceptions.DinersSetOnNotOpenOrderException;
import edu.itba.paw.jimi.interfaces.exceptions.DishAddedToInactiveOrderException;
import edu.itba.paw.jimi.interfaces.exceptions.OrderStatusException;
import edu.itba.paw.jimi.interfaces.exceptions.StockHandlingException;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private DishDao dishDao;

	/**
	 * Updates the total value of the object. Does not touch the DB!
	 * @param order The order to update.
	 */
	private void updateTotal(Order order){

		float total = 0f;
        for (Map.Entry<Dish, Integer> d : order.getDishes().entrySet())
            total += d.getKey().getPrice() * d.getValue();

        order.setTotal(total);

	}

	public Order create(OrderStatus status, Timestamp openedAt, Timestamp closedAt, int diners) {
		return orderDao.create(status, openedAt, closedAt, diners, 0f);
	}
	
	public int addDish(Order order, Dish dish) {

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
		dish.setStock(dish.getStock() - amount);
		dishDao.update(dish);
		Order dbOrder = orderDao.findById(order.getId());
		return dbOrder.getDishes().get(dish);
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
		dish.setStock(dish.getStock() + 1);
		dishDao.update(dish);
		Order dbOrder = orderDao.findById(order.getId());
		if (!dbOrder.getDishes().containsKey(dish))
			return 0;
		else
			return dbOrder.getDishes().get(dish);
	}
	
	public int removeAllDish(Order order, Dish dish) {
		
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new DishAddedToInactiveOrderException();
		
		order.setDish(dish, 0);
		updateTotal(order);
		orderDao.update(order);
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
	}
	
	public void close(Order order) {
		
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new OrderStatusException(OrderStatus.OPEN, order.getStatus());

		order.setStatus(OrderStatus.CLOSED);
		order.setClosedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		orderDao.update(order);
	}

	public Collection<Order> findAll(){
		return orderDao.findAll();
	}
	
}
