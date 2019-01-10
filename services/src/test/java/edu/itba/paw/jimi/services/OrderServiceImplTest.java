package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.exceptions.DinersSetOnNotOpenOrderException;
import edu.itba.paw.jimi.interfaces.exceptions.DishAddedToInactiveOrderException;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;

public class OrderServiceImplTest {
	private static final String DISH_NAME = "Cambuchá";
	private static final float DISH_PRICE = 5.25F;
	private static final int DISH_STOCK = 5;
	private static final float DELTA = 0.001f;
	
	private static final Timestamp OPENEDAT = new Timestamp(1525467178);
	private static final Timestamp CLOSEDAT = new Timestamp(1525467178 + 60 * 60);
	
	@Mock
	private OrderService orderService;
	
	@InjectMocks
	private OrderServiceImpl orderServiceImpl;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(expected = DinersSetOnNotOpenOrderException.class)
	public void setDinersOnNotActiveOrderTest() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		
		// Mockito mocking
		Mockito.when(orderService.findById(order.getId())).thenReturn(order);
		// Mockito mocking
		
		orderServiceImpl.setDiners(order, 5);
	}
	
	@Test(expected = DishAddedToInactiveOrderException.class)
	public void addDishTwiceThenRemoveOnceNotOpenOrderTest() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, null, null, OrderStatus.OPEN, 0, 0);
		Order orderNotOpen = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder1.setDish(dish, 1);
		
		Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder2.setDish(dish, 2);
		
		Order returnOrder3 = new Order(1, OPENEDAT, null, OrderStatus.INACTIVE, 0, 0);
		returnOrder3.setDish(dish, 1);
		
		Mockito.when(orderService.findById(order.getId())).thenReturn(returnOrder1, returnOrder2, returnOrder3);
		// Mockito mocking
		
		orderServiceImpl.addDish(order, dish);
		orderServiceImpl.addDish(order, dish);
		orderServiceImpl.removeOneDish(orderNotOpen, dish);
		
	}
	
	@Test(expected = DishAddedToInactiveOrderException.class)
	public void addDishTwiceThenRemoveAllNotOpenOrderTest() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		Order orderNotOpen = new Order(1, OPENEDAT, null, OrderStatus.INACTIVE, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder1.setDish(dish, 1);
		
		Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder2.setDish(dish, 2);
		
		Order returnOrder3 = new Order(1, OPENEDAT, null, OrderStatus.INACTIVE, 0, 0);
		
		Mockito.when(orderService.findById(order.getId())).thenReturn(returnOrder1, returnOrder2, returnOrder3);
		// Mockito mocking
		
		orderServiceImpl.addDish(order, dish);
		orderServiceImpl.addDish(order, dish);
		int retValue = orderServiceImpl.removeAllDish(orderNotOpen, dish);
		
	}
	
	@Test(expected = DishAddedToInactiveOrderException.class)
	public void addDishesToInactiveOrderTest() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		
		// Mockito mocking
		Order returnOrder = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		returnOrder.setDish(dish, 4);
		
		Mockito.when(orderService.findById(order.getId())).thenReturn(returnOrder);
		// Mockito mocking
		
		orderServiceImpl.addDishes(order, dish, 5);
	}
	
}