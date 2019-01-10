package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.exceptions.OrderStatusException;
import edu.itba.paw.jimi.interfaces.exceptions.StockHandlingException;
import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class OrderServiceBaseImplTest {
	
	private static final String DISH_NAME = "Cambuchá";
	private static final float DISH_PRICE = 5.25F;
	private static final int DISH_STOCK = 5;
	private static final float DELTA = 0.001f;
	
	private static final Timestamp OPENEDAT = new Timestamp(1525467178);
	private static final Timestamp CLOSEDAT = new Timestamp(1525467178 + 60 * 60);

	@Mock
	private OrderDao orderDao;
	
	@Mock
	private DishService dishService;
	
	@InjectMocks
	private OrderServiceBaseImpl orderServiceBaseImpl;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void addDishTest() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder.setDish(dish, 1);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder);
		// Mockito mocking
		
		int retValue = orderServiceBaseImpl.addDish(order, dish);
		
		assertEquals(1, retValue);
		assertEquals(DISH_PRICE, order.getTotal(), DELTA);
		
	}
	
	@Test
	public void addDishesTest() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder.setDish(dish, 5);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder);
		// Mockito mocking
		
		int retValue = orderServiceBaseImpl.addDishes(order, dish, 5);
		
		assertEquals(5, retValue);
		assertEquals(5 * DISH_PRICE, order.getTotal(), DELTA);
	}
	
	@Test(expected = StockHandlingException.class)
	public void addExceededAmountOfDishesTest() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder.setDish(dish, 0);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder);
		// Mockito mocking
		
		orderServiceBaseImpl.addDishes(order, dish, DISH_STOCK + 1);
		
	}
	
	@Test
	public void addDishThenRemoveOne() {
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder1.setDish(dish, 1);
		
		Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder2.setDish(dish, 2);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2);
		// Mockito mocking
		
		orderServiceBaseImpl.addDish(order, dish);
		orderServiceBaseImpl.removeOneDish(order, dish);
		
		assertEquals(DISH_STOCK, dish.getStock());
	}
	
	@Test
	public void addDishesThenRemoveOne() {
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder1.setDish(dish, 1);
		
		Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder2.setDish(dish, 2);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2);
		// Mockito mocking
		
		orderServiceBaseImpl.addDishes(order, dish, 4);
		
		assertEquals(DISH_STOCK - 3, orderServiceBaseImpl.removeOneDish(order, dish));
	}
	
	@Test
	public void addDishThenRemoveAll() {
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder1.setDish(dish, 1);
		
		Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder2.setDish(dish, 2);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2);
		// Mockito mocking
		
		orderServiceBaseImpl.addDish(order, dish);
		orderServiceBaseImpl.removeAllDish(order, dish);
		
		assertEquals(DISH_STOCK, dish.getStock());
	}
	
	@Test
	public void addDishesThenRemoveAll() {
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder1.setDish(dish, 1);
		
		Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder2.setDish(dish, 2);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2);
		// Mockito mocking
		
		orderServiceBaseImpl.addDishes(order, dish, 4);
		orderServiceBaseImpl.removeAllDish(order, dish);
		
		assertEquals(DISH_STOCK, dish.getStock());
	}
	
	@Test
	public void addDishTwiceTest() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder1.setDish(dish, 1);
		
		Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder2.setDish(dish, 2);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2);
		// Mockito mocking
		
		orderServiceBaseImpl.addDish(order, dish);
		int retValue = orderServiceBaseImpl.addDish(order, dish);
		
		assertEquals(retValue, 2);
	}
	
	@Test
	public void addDishTwiceThenRemoveOnceTest() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder1.setDish(dish, 1);
		
		Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder2.setDish(dish, 2);
		
		Order returnOrder3 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder3.setDish(dish, 1);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2, returnOrder3);
		// Mockito mocking
		
		orderServiceBaseImpl.addDish(order, dish);
		orderServiceBaseImpl.addDish(order, dish);
		int retValue = orderServiceBaseImpl.removeOneDish(order, dish);
		
		assertEquals(1, retValue);
	}
	
	@Test
	public void addTwoDishesAndRemoveOneTotal() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Dish dish2 = new Dish(DISH_NAME, DISH_PRICE * 1.5f, 2, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder1.setDish(dish, 1);
		
		Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder2.setDish(dish, 2);
		returnOrder2.setDish(dish2, 2);
		
		Order returnOrder3 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder3.setDish(dish2, 1);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2, returnOrder3);
		// Mockito mocking
		
		orderServiceBaseImpl.addDish(order, dish);
		orderServiceBaseImpl.addDish(order, dish2);
		orderServiceBaseImpl.removeOneDish(order, dish);
		
		assertEquals(DISH_PRICE * 1.5f, order.getTotal(), DELTA);
	}
	
	@Test
	public void addDishTwiceThenRemoveTwiceTest() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder1.setDish(dish, 1);
		
		Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder2.setDish(dish, 2);
		
		Order returnOrder3 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder3.setDish(dish, 1);
		
		Order returnOrder4 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2, returnOrder3, returnOrder4);
		// Mockito mocking
		
		orderServiceBaseImpl.addDish(order, dish);
		orderServiceBaseImpl.addDish(order, dish);
		orderServiceBaseImpl.removeOneDish(order, dish);
		int retValue = orderServiceBaseImpl.removeOneDish(order, dish);
		
		assertEquals(retValue, 0);
	}
	
	@Test
	public void addDishTwiceThenRemoveTwiceReturning0Test() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder1.setDish(dish, 1);
		
		Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder2.setDish(dish, 2);
		
		Order returnOrder3 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder3.setDish(dish, 1);
		
		Order returnOrder4 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder3.setDish(dish, 0);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2, returnOrder3, returnOrder4);
		// Mockito mocking
		
		orderServiceBaseImpl.addDish(order, dish);
		orderServiceBaseImpl.addDish(order, dish);
		orderServiceBaseImpl.removeOneDish(order, dish);
		int retValue = orderServiceBaseImpl.removeOneDish(order, dish);
		
		assertEquals(retValue, 0);
	}
	
	@Test
	public void addDishTwiceThenRemoveAllTest() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder1.setDish(dish, 1);
		
		Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder2.setDish(dish, 2);
		
		Order returnOrder3 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2, returnOrder3);
		// Mockito mocking
		
		orderServiceBaseImpl.addDish(order, dish);
		orderServiceBaseImpl.addDish(order, dish);
		int retValue = orderServiceBaseImpl.removeAllDish(order, dish);
		
		assertEquals(retValue, 0);
	}
	
	@Test
	public void addDishTwiceThenRemoveAllReturning0Test() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder1.setDish(dish, 1);
		
		Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder2.setDish(dish, 2);
		
		Order returnOrder3 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder2.setDish(dish, 0);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2, returnOrder3);
		// Mockito mocking
		
		orderServiceBaseImpl.addDish(order, dish);
		orderServiceBaseImpl.addDish(order, dish);
		int retValue = orderServiceBaseImpl.removeAllDish(order, dish);
		
		assertEquals(0, retValue);
	}
	
	@Test
	public void removeOneDishWithoutAddingTest() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1);
		// Mockito mocking
		
		int retValue = orderServiceBaseImpl.removeOneDish(order, dish);
		
		assertEquals(retValue, 0);
	}
	
	@Test
	public void removeOneDishWithoutAddingReturning0Test() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder1.setDish(dish, 0);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1);
		// Mockito mocking
		
		int retValue = orderServiceBaseImpl.removeOneDish(order, dish);
		
		assertEquals(retValue, 0);
	}
	
	@Test
	public void removeAllDishWithoutAddingReturning0Test() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		returnOrder1.setDish(dish, 0);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1);
		// Mockito mocking
		
		int retValue = orderServiceBaseImpl.removeAllDish(order, dish);
		
		assertEquals(retValue, 0);
	}
	
	@Test
	public void removeAllDishWithoutAddingTest() {
		
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1);
		// Mockito mocking
		
		int retValue = orderServiceBaseImpl.removeAllDish(order, dish);
		
		assertEquals(retValue, 0);
	}
	
	@Test
	public void setDinersTest() {
		Order order = new Order(1, null, null, OrderStatus.OPEN, 0, 0);
		
		// Mockito mocking
		Mockito.when(orderDao.findById(1)).thenReturn(order);
		// Mockito mocking
		
		orderServiceBaseImpl.setDiners(order, 5);
		
		assertEquals(5, order.getDiners());
	}
	
	@Test
	public void setDinersNegativeTest() {
		Order order = new Order(1, null, null, OrderStatus.OPEN, 2, 0);
		
		// Mockito mocking
		Mockito.when(orderDao.findById(1)).thenReturn(order);
		// Mockito mocking
		
		orderServiceBaseImpl.setDiners(order, -5);
		
		assertEquals(2, order.getDiners());
	}
	
	@Test
	public void openOrderTest() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		
		orderServiceBaseImpl.open(order);
		
		assertEquals(OrderStatus.OPEN, order.getStatus());
		Assert.assertNotNull(order.getOpenedAt());
		Assert.assertNull(order.getClosedAt());
	}
	
	@Test
	public void closeOrderTest() {
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		
		orderServiceBaseImpl.close(order);
		
		assertEquals(OrderStatus.CLOSED, order.getStatus());
		Assert.assertNotNull(order.getOpenedAt());
		Assert.assertNotNull(order.getClosedAt());
	}
	
	@Test(expected = OrderStatusException.class)
	public void openOrderOnNOTInactiveTest() {
		Order order = new Order(1, null, null, OrderStatus.CLOSED, 0, 0);
		
		orderServiceBaseImpl.open(order);
		
		
	}
	
	@Test(expected = OrderStatusException.class)
	public void closeOrderOnNOTOpenTest() {
		Order order = new Order(1, OPENEDAT, null, OrderStatus.INACTIVE, 0, 0);
		
		orderServiceBaseImpl.close(order);
	}
	
	@Test
	public void findAllNotNullEmpty(){
		Mockito.when(orderServiceBaseImpl.findAll()).thenReturn(new LinkedList<Order>());
		Assert.assertNotNull(orderServiceBaseImpl.findAll());
	}
	
	@Test
	public void findAllNotNull(){
		Mockito.when(orderServiceBaseImpl.findAll()).thenReturn(null);
		Assert.assertNotNull(orderServiceBaseImpl.findAll());
	}
}