package edu.itba.paw.jimi.services;


import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.interfaces.exceptions.MaxStockException;
import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.models.Dish;
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

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DishServiceTestConfig.class)
public class DishServiceTest {
	
	
	private static final String NAME = "Papa";
	private static final float PRICE = 10.50F;
	private static final int DEFAULT_STOCK = 0;
	private static final int TEST_STOCK = 150;
	private static final int TEST_NEGATIVE_STOCK = -1;
	private static final float e = 0.00001f;
	private static final int MAX_STOCK = 1000000;
	
	@InjectMocks
	@Autowired
	private DishService dishService;
	
	@Autowired
	@Mock
	private DishDao dishDao;
	
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void findByIdTest() {
		
		Mockito.when(dishDao.findById(1)).thenReturn(new Dish(NAME, PRICE, 1, DEFAULT_STOCK));
		
		Dish testDish = dishService.findById(1);
		Assert.assertEquals(NAME, testDish.getName());
		Assert.assertEquals(PRICE, testDish.getPrice(), e);
		Assert.assertEquals(1, testDish.getId());
		Assert.assertEquals(DEFAULT_STOCK, testDish.getStock());
	}
	
	@Test
	public void findByIdNullTest() {
		
		Mockito.when(dishDao.findById(1)).thenReturn(null);
		
		Dish testDish = dishService.findById(1);
		assertNull(testDish);
		
	}
	
	@Test
	public void createTest() {
		Mockito.when(dishDao.create(NAME, PRICE, 0)).thenReturn(new Dish(NAME, PRICE, 1, 0));
		
		Dish testDish = dishService.create(NAME, PRICE);
		Assert.assertEquals(testDish.getName(), NAME);
		Assert.assertEquals(testDish.getPrice(), PRICE, e);
		Assert.assertEquals(testDish.getStock(), 0);
	}
	
	@Test
	public void setStockTest() {
		Mockito.when(dishDao.findById(0)).thenReturn(new Dish(NAME, PRICE, 1, 0));
		
		Dish testDish = dishService.findById(0);
		
		Mockito.when(dishDao.update(testDish)).thenReturn(1);
		
		int retValue = dishService.setStock(testDish, TEST_STOCK);
		
		Assert.assertEquals(retValue, TEST_STOCK);
		Assert.assertEquals(testDish.getStock(), TEST_STOCK);
	}
	
	@Test(expected = MaxStockException.class)
	public void setStockMaxTest() {
		Mockito.when(dishDao.findById(0)).thenReturn(new Dish(NAME, PRICE, 1, 0));
		
		Dish testDish = dishService.findById(0);
		
		Mockito.when(dishDao.update(testDish)).thenReturn(1);
		
		dishService.setStock(testDish, MAX_STOCK + 1);
		
	}
	
	@Test
	public void setStockNegativeTest() {
		Mockito.when(dishDao.findById(0)).thenReturn(new Dish(NAME, PRICE, 1, 0));
		
		Dish testDish = dishService.findById(0);
		
		Mockito.when(dishDao.update(testDish)).thenReturn(1);
		
		int retValue = dishService.setStock(testDish, TEST_NEGATIVE_STOCK);
		
		Assert.assertEquals(retValue, 0);
		Assert.assertEquals(testDish.getStock(), 0);
	}
	
	@Test
	public void setStockUpdateDuplicateTest() {
		Mockito.when(dishDao.findById(0)).thenReturn(new Dish(NAME, PRICE, 1, 0));
		
		Dish testDish = dishService.findById(0);
		
		Mockito.when(dishDao.update(testDish)).thenReturn(2);
		
		int retValue = dishService.setStock(testDish, TEST_STOCK);
		
		Assert.assertEquals(retValue, TEST_STOCK);
		Assert.assertEquals(testDish.getStock(), TEST_STOCK);
	}
	
	@Test
	public void increaseStockFromZeroToOneTest() {
		Mockito.when(dishDao.findById(0)).thenReturn(new Dish(NAME, PRICE, 1, 0));
		
		Dish testDish = dishService.findById(0);
		
		Mockito.when(dishDao.update(testDish)).thenReturn(1);
		
		int retValue = dishService.increaseStock(testDish);
		
		Assert.assertEquals(retValue, 1);
		Assert.assertEquals(testDish.getStock(), 1);
	}
	
	@Test(expected = MaxStockException.class)
	public void increaseStockFromMaxTest() {
		Mockito.when(dishDao.findById(0)).thenReturn(new Dish(NAME, PRICE, 1, MAX_STOCK));
		
		Dish testDish = dishService.findById(0);
		
		Mockito.when(dishDao.update(testDish)).thenReturn(1);
		
		dishService.increaseStock(testDish);
	}
	
	@Test
	public void decreaseStockFromOneToZeroTest() {
		Mockito.when(dishDao.findById(0)).thenReturn(new Dish(NAME, PRICE, 1, 1));
		
		Dish testDish = dishService.findById(0);
		
		Mockito.when(dishDao.update(testDish)).thenReturn(1);
		
		int retValue = dishService.decreaseStock(testDish);
		
		Assert.assertEquals(retValue, 0);
		Assert.assertEquals(testDish.getStock(), 0);
	}
	
	@Test
	public void decreaseStockFromZeroRemainZeroTest() {
		Mockito.when(dishDao.findById(0)).thenReturn(new Dish(NAME, PRICE, 1, 0));
		
		Dish testDish = dishService.findById(0);
		
		Mockito.when(dishDao.update(testDish)).thenReturn(1);
		
		int retValue = dishService.decreaseStock(testDish);
		
		Assert.assertEquals(retValue, 0);
		Assert.assertEquals(testDish.getStock(), 0);
	}
	
	@Test
	public void findAllTest() {
		Collection<Dish> dishes = new LinkedList<Dish>();
		dishes.add(new Dish(NAME, PRICE, 0, 10));
		dishes.add(new Dish(NAME, PRICE, 1, 10));
		dishes.add(new Dish(NAME, PRICE, 2, 10));
		dishes.add(new Dish(NAME, PRICE, 3, 10));
		Mockito.when(dishDao.findAll()).thenReturn(dishes);
		
		Collection<Dish> dbDishes = dishService.findAll();
		
		assertNotNull(dbDishes);
		
		// Pass all dishes to hashset and compare sizes to check if find all contains duplicates.
		Set<Dish> nonRepetingDishes = new HashSet<Dish>();
		nonRepetingDishes.addAll(dishes);
		
		assertEquals(dbDishes.size(), nonRepetingDishes.size());
	}
	
	@Test
	public void findAllNotNullTest() {
		
		Mockito.when(dishDao.findAll()).thenReturn(new HashSet<Dish>());
		Collection<Dish> dbDishes = dishService.findAll();
		assertNotNull(dbDishes);
	}
	
	@Test
	public void findAllTestRepeated() {
		Collection<Dish> dishes = new LinkedList<Dish>();
		dishes.add(new Dish(NAME, PRICE, 0, 10));
		dishes.add(new Dish(NAME, PRICE, 0, 10)); // Duplicated, should return 1 less.
		dishes.add(new Dish(NAME, PRICE, 2, 10));
		dishes.add(new Dish(NAME, PRICE, 3, 10));
		Mockito.when(dishDao.findAll()).thenReturn(dishes);
		
		Collection<Dish> dbDishes = dishService.findAll();
		
		assertNotNull(dbDishes);
		
		// Pass all dishes to hashset and compare sizes to check if find all contains duplicates.
		Set<Dish> nonRepetingDishes = new HashSet<Dish>();
		nonRepetingDishes.addAll(dishes);
		
		assertEquals(dbDishes.size() - 1, nonRepetingDishes.size());
	}
	
	
}
