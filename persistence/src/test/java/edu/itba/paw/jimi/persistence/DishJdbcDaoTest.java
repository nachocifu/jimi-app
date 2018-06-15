package edu.itba.paw.jimi.persistence;


import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.models.Dish;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class DishJdbcDaoTest {
	
	private static final String NAME = "Cambuchá";
	
	private static final String PI_NAME = "Pie";
	
	private static final Float PRICE = 5.25F;
	
	private static final Float REAL_PRICE = (float) Math.PI;
	
	private static final int MAX_STOCK = 1000000;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	@Qualifier("dishJdbcDao")
	private DishDao dishDao; //Here we are not using a mocked dao because orderDao uses a union on DB to get the dishes, so mocking it would break the union.
	
	private JdbcTemplate jdbcTemplate;
	
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "dishes");
	}
	
	@Test
	public void testCreate() {
		final Dish dish = dishDao.create(NAME, PRICE, 1);
		assertNotNull(dish);
		assertEquals(NAME, dish.getName());
		assertEquals(PRICE, dish.getPrice());
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "dishes"));
	}
	
	@Test
	public void testCreateWithPi() {
		final Dish dish = dishDao.create(PI_NAME, REAL_PRICE, 1);
		assertNotNull(dish);
		assertEquals(PI_NAME, dish.getName());
		assertEquals(REAL_PRICE, dish.getPrice());
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "dishes");
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
		final Dish dish1 = dishDao.create(NAME + " Colorada", PRICE * 2, 3);
		final Dish dish2 = dishDao.create(NAME + " Virgen", PRICE, 4);
		final Dish dish3 = dishDao.create(NAME + " Organica", PRICE * 0.5f, 1);
		
		List<Dish> dishes = (List<Dish>) dishDao.findAll();
		assertEquals(3, dishes.size());
		
		assertEquals(dish1.getName(), NAME + " Colorada");
		assertEquals(dish2.getName(), NAME + " Virgen");
		assertEquals(dish3.getName(), NAME + " Organica");
		
		assertEquals(dish1.getPrice(), PRICE * 2);
		assertEquals(dish2.getPrice(), PRICE);
		assertEquals(dish3.getPrice(), PRICE * 0.5f);
		
		assertEquals(dish1.getStock(), 3);
		assertEquals(dish2.getStock(), 4);
		assertEquals(dish3.getStock(), 1);
		
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "dishes");
	}
	
	
	@Test
	public void testFindAllNull() {
		List<Dish> dishes = (List<Dish>) dishDao.findAll();
		assertNotNull(dishes);
		assertEquals(dishes.size(), 0);
	}
	
	
	@Test
	public void testUpdate() {
		
		final Dish dish = dishDao.create(NAME, PRICE, 1);
		dish.setName(NAME + " Virgen");
		dishDao.update(dish);
		
		
		final Dish dish2 = dishDao.findById(dish.getId());
		assertEquals(dish.getId(), dish2.getId());
		assertEquals(dish.getStock(), dish2.getStock());
		assertEquals(dish.getPrice(), dish2.getPrice());
		
		assertEquals(dish.getName(), dish2.getName());
		
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "dishes");
	}
}
