package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.exceptions.*;
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
import java.util.*;

import static org.junit.Assert.assertEquals;

public class OrderServiceImplTest {

	private static final String DISH_NAME = "Cambuchá";
	private static final float DISH_PRICE = 5.25F;
	private static final int DISH_STOCK = 5;
	private static final float DELTA = 0.001F;
	private static final Timestamp OPENEDAT = new Timestamp(1525467178);

	@Mock
	private OrderDao orderDao;

	@Mock
	private DishService dishService;

	@InjectMocks
	private OrderServiceImpl orderService;

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

        int retValue = orderService.addDish(order, dish);

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

        int retValue = orderService.addDishes(order, dish, 5);

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

        orderService.addDishes(order, dish, DISH_STOCK + 1);

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

        orderService.addDish(order, dish);
        orderService.removeOneDish(order, dish);

		assertEquals(DISH_STOCK, dish.getStock());
	}

	@Test
	public void addDishesThenRemoveOne() {
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
        orderService.addDishes(order, dish, 4);
        assertEquals(3, orderService.removeOneDish(order, dish));
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

        orderService.addDish(order, dish);
        orderService.removeAllDish(order, dish);

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

        orderService.addDishes(order, dish, 4);
        orderService.removeAllDish(order, dish);

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

        orderService.addDish(order, dish);
        int retValue = orderService.addDish(order, dish);

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

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        int retValue = orderService.removeOneDish(order, dish);

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

        orderService.addDish(order, dish);
        orderService.addDish(order, dish2);
        orderService.removeOneDish(order, dish);

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

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        orderService.removeOneDish(order, dish);
        int retValue = orderService.removeOneDish(order, dish);

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

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        orderService.removeOneDish(order, dish);
        int retValue = orderService.removeOneDish(order, dish);

		assertEquals(retValue, 0);
	}

    @Test
	public void addDishTwiceThenRemoveAllTest() {
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        int retValue = orderService.removeAllDish(order, dish);
		assertEquals(0, retValue);
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

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        int retValue = orderService.removeAllDish(order, dish);

		assertEquals(0, retValue);
	}

    @Test(expected = AddingDiscontinuedDishException.class)
	public void addDiscontinuedDish() {
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		dish.setDiscontinued(true);
        orderService.addDishes(order, dish, 1);
    }

	@Test
	public void removeOneDishWithoutAddingTest() {

        Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);

        // Mockito mocking
		Order returnOrder1 = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);

        Mockito.when(orderDao.findById(1)).thenReturn(returnOrder1);
		// Mockito mocking

        int retValue = orderService.removeOneDish(order, dish);

		assertEquals(0, retValue);
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

        int retValue = orderService.removeOneDish(order, dish);

		assertEquals(0, retValue);
	}

    @Test
	public void removeOneUndoneDishTest() {
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		order.setDish(dish, 2);

        Mockito.when(orderDao.findById(order.getId())).thenReturn(order);
        orderService.removeOneDish(order, dish);

		int newUndoneDishAmount = order.getUnDoneDishes().get(dish).getAmount();
		Assert.assertEquals(1, newUndoneDishAmount);
		Assert.assertEquals(1, order.getUnDoneDishes().size());
		Assert.assertEquals(DISH_PRICE, order.getTotal(), 0D);
	}

    @Test
	public void removeOneDoneDishTest() {
		Dish dish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);
		order.setDoneDish(dish, 2);
		int originalStock = dish.getStock();

        orderService.removeOneDish(order, dish);

		int newDoneDishAmount = order.getDoneDishes().get(dish);
		Assert.assertEquals(1, newDoneDishAmount);
		Assert.assertEquals(1, order.getDoneDishes().size());
		Assert.assertEquals(DISH_PRICE, order.getTotal(), 0D);
		Dish updatedDish = order.getDoneDishes().keySet().stream().findFirst().get();
		Assert.assertEquals(originalStock, updatedDish.getStock());
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

        int retValue = orderService.removeAllDish(order, dish);

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

        int retValue = orderService.removeAllDish(order, dish);

		assertEquals(retValue, 0);
	}

    @Test
	public void setDinersTest() {
		Order order = new Order(1, null, null, OrderStatus.OPEN, 0, 0);

        // Mockito mocking
		Mockito.when(orderDao.findById(1)).thenReturn(order);
		// Mockito mocking

        orderService.setDiners(order, 5);

		assertEquals(5, order.getDiners());
	}

    @Test
	public void setDinersNegativeTest() {
		Order order = new Order(1, null, null, OrderStatus.OPEN, 2, 0);

        // Mockito mocking
		Mockito.when(orderDao.findById(1)).thenReturn(order);
		// Mockito mocking

        orderService.setDiners(order, -5);

		assertEquals(2, order.getDiners());
	}

    @Test
	public void openOrderTest() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);

        orderService.open(order);

		assertEquals(OrderStatus.OPEN, order.getStatus());
		Assert.assertNotNull(order.getOpenedAt());
		Assert.assertNull(order.getClosedAt());
	}

    @Test
	public void closeOrderTest() {
		Order order = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 0, 0);

        orderService.close(order);

		assertEquals(OrderStatus.CLOSED, order.getStatus());
		Assert.assertNotNull(order.getOpenedAt());
		Assert.assertNotNull(order.getClosedAt());
	}

    @Test(expected = OrderStatusException.class)
	public void openOrderOnNOTInactiveTest() {
		Order order = new Order(1, null, null, OrderStatus.CLOSED, 0, 0);

        orderService.open(order);


    }

	@Test(expected = OrderStatusException.class)
	public void closeOrderOnNOTOpenTest() {
		Order order = new Order(1, OPENEDAT, null, OrderStatus.INACTIVE, 0, 0);

        orderService.close(order);
    }

	@Test
	public void findAllNotNullEmpty() {
        Mockito.when(orderService.findAll()).thenReturn(new LinkedList<Order>());
        Assert.assertNotNull(orderService.findAll());
    }

	@Test
	public void findAllNotNull() {
        Mockito.when(orderService.findAll()).thenReturn(null);
        Assert.assertNotNull(orderService.findAll());
    }

	@Test
	public void getAllUndoneDishesFromAllActiveOrders() {
		Dish dish1 = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		Dish dish2 = new Dish(DISH_NAME, DISH_PRICE, 2, DISH_STOCK);

        Map<Dish, Long> totalDishes = new HashMap<>();
		totalDishes.put(dish1, 3L);
		totalDishes.put(dish2, 7L);

        Mockito.when(orderDao.getAllUndoneDishesFromAllActiveOrders()).thenReturn(totalDishes);

        Map actualDishes = orderService.getAllUndoneDishesFromAllActiveOrders();
		assertEquals(totalDishes, actualDishes);
	}

    @Test
	public void getOrdersFromLast30Minutes() {
		Order urgentOrder = new Order(1, OPENEDAT, null, OrderStatus.OPEN, 1, 0);
		Dish urgentDish = new Dish(DISH_NAME, DISH_PRICE, 1, DISH_STOCK);
		urgentOrder.setDish(urgentDish, 1);

        Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, -30);
		urgentOrder.getUnDoneDishes().get(urgentDish).setOrderedAt(new Timestamp(cal.getTimeInMillis()));

        List<Order> expectedUrgentOrders = new LinkedList<>();
		expectedUrgentOrders.add(urgentOrder);

        Mockito.when(orderDao.getOrdersFromLastMinutes(30)).thenReturn(expectedUrgentOrders);

        List<Order> actualUrgentOrders = (List<Order>) orderService.getOrdersFromLastMinutes(30);
		assertEquals(expectedUrgentOrders, actualUrgentOrders);
	}

	@Test(expected = DinersSetOnNotOpenOrderException.class)
    public void setDinersOnNotActiveOrderTest() {
        Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);

        // Mockito mocking
        Mockito.when(orderService.findById(order.getId())).thenReturn(order);
        // Mockito mocking

        orderService.setDiners(order, 5);
    }

	@Test(expected = DishSetToInactiveTableException.class)
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

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        orderService.removeOneDish(orderNotOpen, dish);

    }

	@Test(expected = DishSetToInactiveTableException.class)
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

        orderService.addDish(order, dish);
        orderService.addDish(order, dish);
        int retValue = orderService.removeAllDish(orderNotOpen, dish);

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

        orderService.addDishes(order, dish, 5);
    }
}
