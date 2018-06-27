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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

@Service
@Qualifier(value = "userOrderService")
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    @Qualifier(value = "adminOrderService")
    private OrderService orderService;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	public Order create(OrderStatus status, Timestamp openedAt, Timestamp closedAt, int diners) {
		return orderService.create(status, openedAt, closedAt, diners);
	}

	public int addDish(Order order, Dish dish) {
		return orderService.addDish(order, dish);
	}

	public int addDishes(Order order, Dish dish, int amount) {
		
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new DishAddedToInactiveOrderException();
		
		return orderService.addDishes(order, dish, amount);
	}

	public int removeOneDish(Order order, Dish dish) {
		
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new DishAddedToInactiveOrderException();
		
		return orderService.removeOneDish(order, dish);
	}

	public int removeAllDish(Order order, Dish dish) {
		
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new DishAddedToInactiveOrderException();

		return orderService.removeAllDish(order, dish);
	}

	public int setDiners(Order order, int diners) {
		Order o = orderService.findById(order.getId());

		if (!o.getStatus().equals(OrderStatus.OPEN))
			throw new DinersSetOnNotOpenOrderException();
		
		return orderService.setDiners(order, diners);
	}

	public Order findById(long id) {
		return orderService.findById(id);
	}

	public void open(Order order) {
		orderService.open(order);
	}

	public void close(Order order) {
		orderService.close(order);
	}

	public void cancel(Order order) {
		orderService.cancel(order);
	}

	public Collection<Order> findAll() {
		
		return orderService.findAll();
	}

	public Collection<Order> findAll(QueryParams qp) {
		return orderService.findAll(qp);
	}

    public Collection<Order> findAllRelevant(QueryParams qp) {
        return orderService.findAllRelevant(qp);
    }

    public Map getMonthlyOrderTotal() {
	    return orderService.getMonthlyOrderTotal();
	}

    public void setDishAsDone(Order order, Dish dish) {
        orderService.setDishAsDone(order, dish);
    }

    public int getTotalRelevantOrders() {
        return orderService.getTotalRelevantOrders();
    }

    public Collection<Order> getActiveOrders(QueryParams qp) {
        return orderService.getActiveOrders(qp);
    }

    public int getTotalActiveOrders() {
        return orderService.getTotalActiveOrders();
    }

}
