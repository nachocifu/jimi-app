package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.exceptions.DishAddedToInactiveOrderException;
import edu.itba.paw.jimi.interfaces.exceptions.OrderStatusException;
import edu.itba.paw.jimi.interfaces.exceptions.StockHandlingException;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

/**
 * Some tests are duplicated because it is possible that the order has the Dish with value 0 or doesn't even have the dish.
 * Both are contemplated.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OrderServiceTestConfig.class)
public class OrderServiceTest {

    private static final String DISH_NAME = "Cambuchá";
    private static final Float DISH_PRICE = 5.25F;
    private static final int DISH_STOCK = 5;

    private static final Timestamp OPENEDAT = new Timestamp(1525467178);
    private static final Timestamp CLOSEDAT = new Timestamp(1525467178 + 60*60);


    @InjectMocks
    @Autowired
    private OrderService orderService;

    @Autowired
    @Mock
    private OrderDao orderDao;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void addDishTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        // Mockito mocking
        Order returnOrder = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder.setDish(dish, 1);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder);
        // Mockito mocking

        int retValue = orderService.addDish(order, dish);

        Assert.assertEquals(retValue, 1);
    }

    @Test
    public void addDishesTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        // Mockito mocking
        Order returnOrder = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder.setDish(dish, 5);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder);
        // Mockito mocking

        int retValue = orderService.addDishes(order, dish, 5);

        Assert.assertEquals(retValue, 5);
    }

    @Test(expected = StockHandlingException.class)
    public void addExceededAmountOfDishesTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        // Mockito mocking
        Order returnOrder = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder.setDish(dish, 0);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder);
        // Mockito mocking

        orderService.addDishes(order, dish, DISH_STOCK + 1);

    }

    @Test(expected = DishAddedToInactiveOrderException.class)
    public void addDishesToInactiveOrderTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, null, null, OrderStatus.INACTIVE);

        // Mockito mocking
        Order returnOrder = new Order(1, null, null, OrderStatus.INACTIVE);
        returnOrder.setDish(dish, 4);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder);
        // Mockito mocking

        orderService.addDishes(order, dish, 5);
    }

    @Test
    public void addDishTwiceTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        // Mockito mocking
        Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder1.setDish(dish, 1);

        Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder2.setDish(dish, 2);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2);
        // Mockito mocking

        orderService.addDish(order, dish);
        int retValue = orderService.addDish(order, dish);

        Assert.assertEquals(retValue, 2);
    }

    @Test
    public void addDishTwiceThenRemoveOnceTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        // Mockito mocking
        Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder1.setDish(dish, 1);

        Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder2.setDish(dish, 2);

        Order returnOrder3 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder3.setDish(dish, 1);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2, returnOrder3);
        // Mockito mocking

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        int retValue = orderService.removeOneDish(order, dish);

        Assert.assertEquals(retValue, 1);
    }

    @Test(expected = DishAddedToInactiveOrderException.class)
    public void addDishTwiceThenRemoveOnceNotOpenOrderTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, null, null, OrderStatus.OPEN);
        Order orderNotOpen = new Order(1, null, null, OrderStatus.INACTIVE);

        // Mockito mocking
        Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder1.setDish(dish, 1);

        Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder2.setDish(dish, 2);

        Order returnOrder3 = new Order(1, OPENEDAT, null, OrderStatus.INACTIVE);
        returnOrder3.setDish(dish, 1);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2, returnOrder3);
        // Mockito mocking

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        orderService.removeOneDish(orderNotOpen, dish);

    }

    @Test
    public void addDishTwiceThenRemoveTwiceTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        // Mockito mocking
        Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder1.setDish(dish, 1);

        Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder2.setDish(dish, 2);

        Order returnOrder3 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder3.setDish(dish, 1);

        Order returnOrder4 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2, returnOrder3, returnOrder4);
        // Mockito mocking

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        orderService.removeOneDish(order, dish);
        int retValue = orderService.removeOneDish(order, dish);

        Assert.assertEquals(retValue, 0);
    }

    @Test
    public void addDishTwiceThenRemoveTwiceReturning0Test() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        // Mockito mocking
        Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder1.setDish(dish, 1);

        Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder2.setDish(dish, 2);

        Order returnOrder3 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder3.setDish(dish, 1);

        Order returnOrder4 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder3.setDish(dish, 0);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2, returnOrder3, returnOrder4);
        // Mockito mocking

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        orderService.removeOneDish(order, dish);
        int retValue = orderService.removeOneDish(order, dish);

        Assert.assertEquals(retValue, 0);
    }

    @Test
    public void addDishTwiceThenRemoveAllTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        // Mockito mocking
        Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder1.setDish(dish, 1);

        Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder2.setDish(dish, 2);

        Order returnOrder3 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2, returnOrder3);
        // Mockito mocking

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        int retValue = orderService.removeAllDish(order, dish);

        Assert.assertEquals(retValue, 0);
    }

    @Test(expected = DishAddedToInactiveOrderException.class)
    public void addDishTwiceThenRemoveAllNotOpenOrderTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        Order orderNotOpen = new Order(1, OPENEDAT, null, OrderStatus.INACTIVE);

        // Mockito mocking
        Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder1.setDish(dish, 1);

        Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder2.setDish(dish, 2);

        Order returnOrder3 = new Order(1, OPENEDAT, null, OrderStatus.INACTIVE);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2, returnOrder3);
        // Mockito mocking

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        int retValue = orderService.removeAllDish(orderNotOpen, dish);

    }

    @Test
    public void addDishTwiceThenRemoveAllReturning0Test() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        // Mockito mocking
        Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder1.setDish(dish, 1);

        Order returnOrder2 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder2.setDish(dish, 2);

        Order returnOrder3 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder2.setDish(dish, 0);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2, returnOrder3);
        // Mockito mocking

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        int retValue = orderService.removeAllDish(order, dish);

        Assert.assertEquals(retValue, 0);
    }

    @Test
    public void removeOneDishWithoutAddingTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        // Mockito mocking
        Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1);
        // Mockito mocking

        int retValue = orderService.removeOneDish(order, dish);

        Assert.assertEquals(retValue, 0);
    }

    @Test
    public void removeOneDishWithoutAddingReturning0Test() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        // Mockito mocking
        Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder1.setDish(dish, 0);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1);
        // Mockito mocking

        int retValue = orderService.removeOneDish(order, dish);

        Assert.assertEquals(retValue, 0);
    }


    @Test
    public void removeAllDishWithoutAddingReturning0Test() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        // Mockito mocking
        Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);
        returnOrder1.setDish(dish, 0);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1);
        // Mockito mocking

        int retValue = orderService.removeAllDish(order, dish);

        Assert.assertEquals(retValue, 0);
    }

    @Test
    public void removeAllDishWithoutAddingTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        // Mockito mocking
        Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1);
        // Mockito mocking

        int retValue = orderService.removeAllDish(order, dish);

        Assert.assertEquals(retValue, 0);
    }


    @Test
    public void openOrderTest(){
        Order order = new Order(1, null, null, OrderStatus.INACTIVE);

        orderService.open(order);

        Assert.assertEquals(OrderStatus.OPEN, order.getStatus());
        Assert.assertNotNull(order.getOpenedAt());
        Assert.assertNull(order.getClosedAt());
    }

    @Test
    public void closeOrderTest(){
        Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN);

        orderService.close(order);

        Assert.assertEquals(OrderStatus.CLOSED, order.getStatus());
        Assert.assertNotNull(order.getOpenedAt());
        Assert.assertNotNull(order.getClosedAt());
    }

    @Test(expected = OrderStatusException.class)
    public void openOrderOnNOTInactiveTest(){
        Order order = new Order(1, null, null, OrderStatus.CLOSED);

        orderService.open(order);


    }

    @Test(expected = OrderStatusException.class)
    public void closeOrderOnNOTOpenTest(){
        Order order = new Order(1, OPENEDAT, null, OrderStatus.INACTIVE);

        orderService.close(order);
    }

}
