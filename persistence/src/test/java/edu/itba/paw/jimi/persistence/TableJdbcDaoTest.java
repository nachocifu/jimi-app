package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.exceptions.TableWithNullOrderException;
import edu.itba.paw.jimi.models.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class TableJdbcDaoTest {
	
	private static final String ORDER_TABLE_NAME = "orders";
	private static final String ORDER_ITEM_TABLE_NAME = "orders_items";
	private static final String TABLE_TABLE_NAME = "tables";
	
	
	private static final String TABLE_NAME = "Table 1";
	private static final String TABLE_NAME2 = "Table 2";
	
	
	private static final String DISH_NAME = "Cambuchá";
	private static final Float DISH_PRICE = 5.25F;
	private static final int DISH_STOCK = 5;
	private static final int NUMBER_OF_TABLES = 10;
	
	private static final Timestamp OPENEDAT = new Timestamp(1525467178);
	private static final Timestamp CLOSEDAT = new Timestamp(1525467178 + 60 * 60);
	
	@Autowired
	private DataSource ds;
	
	private TableDao tableDao;
	private DishDao dishDao;
	private OrderDao orderDao;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		tableDao = new TableJdbcDao(ds);
		dishDao = new DishJdbcDao(ds);
		orderDao = new OrderJdbcDao(ds);
		cleanDB();
	}
	
	private void cleanDB() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, ORDER_TABLE_NAME);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, ORDER_ITEM_TABLE_NAME);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, TABLE_TABLE_NAME);
	}
	
	@Test
	public void testCreate() {
		Order order = orderDao.create(OrderStatus.INACTIVE, null, null, 2, 0);
		Table table = tableDao.create(TABLE_NAME, TableStatus.FREE, order);
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_TABLE_NAME));
		cleanDB();
	}
	
	@Test
	public void testFindById() {
		
		final Dish dish = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
		final Order order = orderDao.create(OrderStatus.INACTIVE, OPENEDAT, CLOSEDAT, 2, 2);
		order.setDish(dish, 2);
		orderDao.update(order);
		final Table table = tableDao.create(TABLE_NAME, TableStatus.FREE, order);
		
		Table dbTable = tableDao.findById(table.getId());
		assertNotNull(dbTable);
		assertNotNull(dbTable.getOrder());
		assertNotNull(dbTable.getOrder().getDishes().keySet().iterator().next());
		
		
		//Assert table.
		assertEquals(TABLE_NAME, dbTable.getName());
		assertEquals(TableStatus.FREE.getId(), dbTable.getStatus().getId());
		assertEquals(order.getId(), dbTable.getOrder().getId());
		
		//Assert order.
		Order dbOrder = dbTable.getOrder();
		assertEquals(2, dbOrder.getDishes().get(dish).intValue());
		assertEquals(order.getStatus().getId(), dbOrder.getStatus().getId());
		assertEquals(order.getId(), dbOrder.getId());
		assertEquals(order.getOpenedAt(), dbOrder.getOpenedAt());
		assertEquals(order.getClosedAt(), dbOrder.getClosedAt());
		assertEquals(2, dbOrder.getDiners());
		assertEquals(2f, dbOrder.getTotal());

		//Assert dishes.
		Dish dbDish = dbOrder.getDishes().keySet().iterator().next();
		assertEquals(dish.getId(), dbDish.getId());
		assertEquals(dish.getName(), dbDish.getName());
		assertEquals(dish.getStock(), dbDish.getStock());
		assertEquals(dish.getPrice(), dbDish.getPrice());
		
		
		cleanDB();
	}
	
	
	@Test(expected = TableWithNullOrderException.class)
	public void testFindByIdNullOrderException() {
		
		tableDao.create(TABLE_NAME, TableStatus.FREE, null);
		
		cleanDB();
	}
	
	@Test(expected = TableWithNullOrderException.class)
	public void testFindByIdUnsavedOrderException() {
		
		final Order order = new Order();
		tableDao.create(TABLE_NAME, TableStatus.FREE, order);
		
		cleanDB();
	}
	
	
	@Test
	public void testUpdate() {
		
		final Dish dish = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
		final Order order = orderDao.create(OrderStatus.INACTIVE, null, null, 2, 0);
		order.setDish(dish, 2);
		orderDao.update(order);
		final Table table = tableDao.create(TABLE_NAME, TableStatus.FREE, order);
		
		Table dbTable = tableDao.findById(table.getId());
		assertNotNull(dbTable);
		assertNotNull(dbTable.getOrder());
		assertNotNull(dbTable.getOrder().getDishes().keySet().iterator().next());
		
		
		//Assert table.
		assertEquals(TABLE_NAME, dbTable.getName());
		assertEquals(TableStatus.FREE.getId(), dbTable.getStatus().getId());
		assertEquals(order.getId(), dbTable.getOrder().getId());
		
		//Assert order.
		Order dbOrder = dbTable.getOrder();
		assertEquals(dbOrder.getDishes().get(dish).intValue(), 2);
		assertEquals(2, dbOrder.getDiners());
		
		//Assert dishes.
		Dish dbDish = dbOrder.getDishes().keySet().iterator().next();
		assertEquals(dish.getId(), dbDish.getId());
		assertEquals(dish.getName(), dbDish.getName());
		assertEquals(dish.getStock(), dbDish.getStock());
		assertEquals(dish.getPrice(), dbDish.getPrice());
		
		
		dbTable.getOrder().setDish(dish, 5);
		dbTable.getOrder().setDiners(5);
		dbTable.setName(TABLE_NAME2);
		dbTable.setStatus(TableStatus.BUSY);
		
		tableDao.update(dbTable);
		
		Table dbTableUpdated = tableDao.findById(dbTable.getId());
		
		//Assert update
		assertNotNull(dbTableUpdated);
		assertEquals(5, dbTableUpdated.getOrder().getDishes().get(dish).intValue());
		assertEquals(TABLE_NAME2, dbTableUpdated.getName());
		assertEquals(dbTableUpdated.getStatus().getId(), TableStatus.BUSY.getId());
		assertEquals(5, dbTableUpdated.getOrder().getDiners());
		
		cleanDB();
	}
	
	@Test
	public void testFindAll() {
		
		for (int i = 0; i < NUMBER_OF_TABLES; i++) {
			final Dish dish = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
			final Order order = orderDao.create(OrderStatus.INACTIVE, null, null, 1, 0);
			order.setDish(dish, 1);
			orderDao.update(order);
			tableDao.create(TABLE_NAME, TableStatus.FREE, order);
		}
		
		assertEquals(NUMBER_OF_TABLES, JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_TABLE_NAME));
		Collection<Table> tables = tableDao.findAll();
		
		for (Table table : tables) {
			assertEquals(TABLE_NAME, table.getName());
			assertEquals(TableStatus.FREE.getId(), table.getStatus().getId());
			assertEquals(1, table.getOrder().getDiners());
			assertEquals(DISH_NAME, table.getOrder().getDishes().keySet().iterator().next().getName());
			assertEquals(DISH_PRICE, table.getOrder().getDishes().keySet().iterator().next().getPrice());
			assertEquals(DISH_STOCK, table.getOrder().getDishes().keySet().iterator().next().getStock());
		}
		cleanDB();
	}

	@Test
	public void testFindAllNoTables() {
		Collection<Table> tables = tableDao.findAll();
		assertNotNull(tables);
		assertEquals(0, tables.size());
	}
	
}