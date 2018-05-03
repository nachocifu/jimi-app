package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
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
        Order order = new Order(1);

        // Mockito mocking
        Order returnOrder = new Order(1);
        returnOrder.setDish(dish, 1);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder);
        // Mockito mocking

        int retValue = orderService.addDish(order, dish);

        Assert.assertEquals(retValue, 1);
    }

    @Test
    public void addDishesTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1);

        // Mockito mocking
        Order returnOrder = new Order(1);
        returnOrder.setDish(dish, 5);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder);
        // Mockito mocking

        int retValue = orderService.addDishes(order, dish, 5);

        Assert.assertEquals(retValue, 5);
    }

    @Test
    public void addDishTwiceTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1);

        // Mockito mocking
        Order returnOrder1 = new Order(1);
        returnOrder1.setDish(dish, 1);

        Order returnOrder2 = new Order(1);
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
        Order order = new Order(1);

        // Mockito mocking
        Order returnOrder1 = new Order(1);
        returnOrder1.setDish(dish, 1);

        Order returnOrder2 = new Order(1);
        returnOrder2.setDish(dish, 2);

        Order returnOrder3 = new Order(1);
        returnOrder3.setDish(dish, 1);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2, returnOrder3);
        // Mockito mocking

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        int retValue = orderService.removeOneDish(order, dish);

        Assert.assertEquals(retValue, 1);
    }

    @Test
    public void addDishTwiceThenRemoveTwiceTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1);

        // Mockito mocking
        Order returnOrder1 = new Order(1);
        returnOrder1.setDish(dish, 1);

        Order returnOrder2 = new Order(1);
        returnOrder2.setDish(dish, 2);

        Order returnOrder3 = new Order(1);
        returnOrder3.setDish(dish, 1);

        Order returnOrder4 = new Order(1);

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
        Order order = new Order(1);

        // Mockito mocking
        Order returnOrder1 = new Order(1);
        returnOrder1.setDish(dish, 1);

        Order returnOrder2 = new Order(1);
        returnOrder2.setDish(dish, 2);

        Order returnOrder3 = new Order(1);
        returnOrder3.setDish(dish, 1);

        Order returnOrder4 = new Order(1);
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
        Order order = new Order(1);

        // Mockito mocking
        Order returnOrder1 = new Order(1);
        returnOrder1.setDish(dish, 1);

        Order returnOrder2 = new Order(1);
        returnOrder2.setDish(dish, 2);

        Order returnOrder3 = new Order(1);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1, returnOrder2, returnOrder3);
        // Mockito mocking

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        int retValue = orderService.removeAllDish(order, dish);

        Assert.assertEquals(retValue, 0);
    }

    @Test
    public void addDishTwiceThenRemoveAllReturning0Test() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1);

        // Mockito mocking
        Order returnOrder1 = new Order(1);
        returnOrder1.setDish(dish, 1);

        Order returnOrder2 = new Order(1);
        returnOrder2.setDish(dish, 2);

        Order returnOrder3 = new Order(1);
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
        Order order = new Order(1);

        // Mockito mocking
        Order returnOrder1 = new Order(1);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1);
        // Mockito mocking

        int retValue = orderService.removeOneDish(order, dish);

        Assert.assertEquals(retValue, 0);
    }

    @Test
    public void removeOneDishWithoutAddingReturning0Test() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1);

        // Mockito mocking
        Order returnOrder1 = new Order(1);
        returnOrder1.setDish(dish, 0);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1);
        // Mockito mocking

        int retValue = orderService.removeOneDish(order, dish);

        Assert.assertEquals(retValue, 0);
    }


    @Test
    public void removeAllDishWithoutAddingReturning0Test() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1);

        // Mockito mocking
        Order returnOrder1 = new Order(1);
        returnOrder1.setDish(dish, 0);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1);
        // Mockito mocking

        int retValue = orderService.removeAllDish(order, dish);

        Assert.assertEquals(retValue, 0);
    }

    @Test
    public void removeAllDishWithoutAddingTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
        Order order = new Order(1);

        // Mockito mocking
        Order returnOrder1 = new Order(1);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1);
        // Mockito mocking

        int retValue = orderService.removeAllDish(order, dish);

        Assert.assertEquals(retValue, 0);
    }

}
