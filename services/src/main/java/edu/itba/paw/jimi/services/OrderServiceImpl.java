package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.exceptions.DinersSetOnNotOpenOrderException;
import edu.itba.paw.jimi.interfaces.exceptions.DishAddedToInactiveOrderException;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import edu.itba.paw.jimi.models.utils.QueryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;

@Service
@Qualifier(value = "userOrderService")
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	@Qualifier(value = "adminOrderService")
	private OrderService orderService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Override
	public Order create(OrderStatus status, Timestamp openedAt, Timestamp closedAt, int diners) {
		return orderService.create(status, openedAt, closedAt, diners);
	}
	
	@Override
	public int addDish(Order order, Dish dish) {
		return orderService.addDish(order, dish);
	}
	
	@Override
	public int addDishes(Order order, Dish dish, int amount) {
		
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new DishAddedToInactiveOrderException();
		
		return orderService.addDishes(order, dish, amount);
	}
	
	@Override
	public int removeOneDish(Order order, Dish dish) {
		
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new DishAddedToInactiveOrderException();
		
		return orderService.removeOneDish(order, dish);
	}
	
	@Override
	public int removeAllDish(Order order, Dish dish) {
		
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new DishAddedToInactiveOrderException();
		
		return orderService.removeAllDish(order, dish);
	}
	
	@Override
	public int setDiners(Order order, int diners) {
		Order o = orderService.findById(order.getId());
		
		if (!o.getStatus().equals(OrderStatus.OPEN))
			throw new DinersSetOnNotOpenOrderException();
		
		return orderService.setDiners(order, diners);
	}
	
	@Override
	public Order findById(long id) {
		return orderService.findById(id);
	}
	
	@Override
	public void open(Order order) {
		orderService.open(order);
	}
	
	@Override
	public void close(Order order) {
		orderService.close(order);
	}
	
	@Override
	public void cancel(Order order) {
		orderService.cancel(order);
	}
	
	@Override
	public Collection<Order> findAll() {
		
		return orderService.findAll();
	}
	
	@Override
	public Collection<Order> findAll(QueryParams qp) {
		return orderService.findAll(qp);
	}
	
	@Override
	public Collection<Order> findAllRelevant(QueryParams qp) {
		return orderService.findAllRelevant(qp);
	}
	
	@Override
	public Map getMonthlyOrderTotal() {
		return orderService.getMonthlyOrderTotal();
	}
	
	@Override
	public Map getMonthlyOrderCancelled() {
		return orderService.getMonthlyOrderCancelled();
	}
	
	@Override
	public void setDishAsDone(Order order, Dish dish) {
		orderService.setDishAsDone(order, dish);
	}
	
	@Override
	public int getTotalRelevantOrders() {
		return orderService.getTotalRelevantOrders();
	}
	
	@Override
	public Collection<Order> getActiveOrders() {
		return orderService.getActiveOrders();
	}
	
	@Override
	public int getTotalActiveOrders() {
		return orderService.getTotalActiveOrders();
	}
	
	@Override
	public Collection<Order> get30MinutesWaitOrders() {
		return orderService.get30MinutesWaitOrders();
	}
	
	@Override
	public Map getAllUndoneDishesFromAllActiveOrders() {
		return orderService.getAllUndoneDishesFromAllActiveOrders();
	}
	
}
