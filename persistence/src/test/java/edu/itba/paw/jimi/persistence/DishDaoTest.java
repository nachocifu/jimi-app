package edu.itba.paw.jimi.persistence;


import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.utils.QueryParams;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class DishDaoTest {
	
	private static final String DISHES_TABLE_NAME = "dishes";
	private static final String NAME = "Cambuchá";
	private static final String PI_NAME = "Pie";
	private static final Float PRICE = 5.25F;
	private static final Float REAL_PRICE = (float) Math.PI;
	private static final int MAX_STOCK = 1000000;
	private static final String DISH_NAME = "Cambuchá";
	private static final String DISH_NAME_MISS_STOCK = "Cambuchá missing stock";
	private static final String DISH_NAME_OVER_STOCK = "Cambuchá over stock";
	private static final Float DISH_PRICE = 5.25F;
	private static final int DISH_STOCK = 5;
	private static final int MIN_STOCK = 5;
	private Dish dishEqualStock;
	private Dish dishMissingStock;
	private Dish dishOverStock;
	private Dish dishDiscontinued;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	@Qualifier("dishHibernateDao")
	private DishDao dishDao; //Here we are not using a mocked dao because orderDao uses a union on DB to get the dishes, so mocking it would break the union.
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		addTestData();
	}
	
	private void addTestData() {
		dishEqualStock = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
		dishEqualStock.setMinStock(MIN_STOCK);
		dishDao.update(dishEqualStock);
		dishMissingStock = dishDao.create(DISH_NAME_MISS_STOCK, DISH_PRICE, DISH_STOCK - 3);
		dishMissingStock.setMinStock(MIN_STOCK);
		dishDao.update(dishMissingStock);
		dishOverStock = dishDao.create(DISH_NAME_OVER_STOCK, DISH_PRICE, DISH_STOCK + 3);
		dishOverStock.setMinStock(MIN_STOCK);
		dishDao.update(dishOverStock);
		dishDiscontinued = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
		dishDiscontinued.setDiscontinued(true);
		dishDao.update(dishDiscontinued);
	}
	
	@After
	public void cleanDB() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, DISHES_TABLE_NAME);
	}
	
	@Test
	public void testCreate() {
		final Dish dish = dishDao.create(NAME, PRICE, 1);
		assertNotNull(dish);
		assertEquals(NAME, dish.getName());
		assertEquals(PRICE, dish.getPrice());
	}
	
	@Test
	public void testUpdate() {
		final Dish dish = dishDao.create(NAME, PRICE, 1);
		
		assertNotNull(dish);
		assertEquals(NAME, dish.getName());
		assertEquals(PRICE, dish.getPrice());
		
		dish.setName("Test Name");
		
		dishDao.update(dish);
		
		Dish dbDish = dishDao.findById(dish.getId());
		
		assertEquals("Test Name", dbDish.getName());
	}
	
	@Test
	public void testCreateWithPi() {
		final Dish dish = dishDao.create(PI_NAME, REAL_PRICE, 1);
		assertNotNull(dish);
		assertEquals(PI_NAME, dish.getName());
		assertEquals(REAL_PRICE, dish.getPrice());
	}
	
	@Test
	public void testFindById() {
		final Dish dish = dishDao.create(NAME, PRICE, 1);
		Dish dbDish = dishDao.findById(dish.getId());
		assertNotNull(dbDish);
		assertEquals(NAME, dbDish.getName());
		assertEquals(PRICE, dbDish.getPrice());
	}
	
	@Test
	public void testCreateWithMaxStock() {
		final Dish dish = dishDao.create(NAME, PRICE, MAX_STOCK);
		Dish dbDish = dishDao.findById(dish.getId());
		assertNotNull(dbDish);
		assertEquals(NAME, dbDish.getName());
		assertEquals(PRICE, dbDish.getPrice());
		assertEquals(MAX_STOCK, dbDish.getStock());
	}
	
	@Test
	public void testFindAll() {
		List<Dish> dishes = (List<Dish>) dishDao.findAll(new QueryParams(0, 10));
		assertEquals(4, dishes.size());
		
		assertEquals(dishEqualStock.getName(), DISH_NAME);
		assertEquals(dishMissingStock.getName(), DISH_NAME_MISS_STOCK);
		assertEquals(dishOverStock.getName(), DISH_NAME_OVER_STOCK);
		
		assertEquals(dishEqualStock.getPrice(), DISH_PRICE);
		assertEquals(dishMissingStock.getPrice(), DISH_PRICE);
		assertEquals(dishOverStock.getPrice(), DISH_PRICE);
		
		assertEquals(dishEqualStock.getStock(), DISH_STOCK);
		assertEquals(dishMissingStock.getStock(), DISH_STOCK - 3);
		assertEquals(dishOverStock.getStock(), DISH_STOCK + 3);
	}
	
//	@Test
//	public void testFindAllOffered() {
//		List<Dish> dishes = (List<Dish>) dishDao.findAllOffered();
//		assertEquals(3, dishes.size());
//	}
	
	@Test
	public void testFindAllAvailable() {
		List<Dish> dishes = (List<Dish>) dishDao.findAllAvailable();
		assertEquals(3, dishes.size());
	}
	
	@Test
	public void testFindDiscontinuedDishes() {
		List<Dish> discontinuedDishes = (List<Dish>) dishDao.findDiscontinuedDishes();
		assertEquals(1, discontinuedDishes.size());
		assertTrue(discontinuedDishes.contains(dishDiscontinued));
	}
	
	@Test
	public void testFindDishesMissingStock() {
		Collection<Dish> dishes = dishDao.findDishesMissingStock();
		Assert.assertEquals(1, dishes.size());
		Assert.assertEquals(dishMissingStock, dishes.toArray()[0]);
	}
}