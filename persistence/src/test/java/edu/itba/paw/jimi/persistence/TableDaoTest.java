package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.exceptions.ExistingTableNameException;
import edu.itba.paw.jimi.interfaces.exceptions.TableWithNullOrderException;
import edu.itba.paw.jimi.models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@Transactional
public class TableDaoTest {
	
	private static final String ORDER_TABLE_NAME = "orders";
	private static final String TABLE_TABLE_NAME = "tables";
	private static final String TABLE_NAME = "Table 1";
	private static final String TABLE_NAME2 = "Table 2";
	private static final String DISH_NAME = "Cambuchá";
	private static final Float DISH_PRICE = 5.25F;
	private static final int DISH_STOCK = 5;
	private static final Timestamp OPENEDAT = new Timestamp(1525467178);
	private static final Timestamp CLOSEDAT = new Timestamp(1525467178 + 60 * 60);
	private Dish testDish;
	private Order testOrder;
	private Table testTable;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private DishDao dishDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private TableDao tableDao;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		addTestData();
	}
	
	void addTestData() {
		testDish = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
		testOrder = orderDao.create(OrderStatus.INACTIVE, OPENEDAT, CLOSEDAT, 2, 2);
		testOrder.setDish(testDish, 2);
		try {
			testTable = tableDao.create(TABLE_NAME, TableStatus.FREE, testOrder);
		} catch (ExistingTableNameException e) {
			e.printStackTrace();
		}
	}
	
	private void cleanDB() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, TABLE_TABLE_NAME);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, ORDER_TABLE_NAME);
	}
	
	@Test
	public void testFindById() {
		
		Table dbTable = tableDao.findById(testTable.getId());
		assertNotNull(dbTable);
		assertNotNull(dbTable.getOrder());
		assertNotNull(dbTable.getOrder().getDishes().keySet().iterator().next());
		
		//Assert testTable.
		assertEquals(TABLE_NAME, dbTable.getName());
		assertEquals(TableStatus.FREE.ordinal(), dbTable.getStatus().ordinal());
		assertEquals(testOrder.getId(), dbTable.getOrder().getId());
		
	}
	
	@Test(expected = ExistingTableNameException.class)
	public void testCreateTableWithExistingName() throws ExistingTableNameException {
		Assert.assertTrue(tableDao.tableNameExists(TABLE_NAME));
		tableDao.create(TABLE_NAME, TableStatus.FREE, testOrder);
		cleanDB();
	}
	
	@Test(expected = TableWithNullOrderException.class)
	public void testFindByIdNullOrderException() throws ExistingTableNameException {
		tableDao.create(TABLE_NAME, TableStatus.FREE, null);
		cleanDB();
	}
	
	@Test(expected = TableWithNullOrderException.class)
	public void testFindByIdUnsavedOrderException() throws ExistingTableNameException {
		Order order = new Order(1, OPENEDAT, CLOSEDAT, OrderStatus.INACTIVE, 2, 2);
		orderDao.findById(1);
		tableDao.create(TABLE_NAME, TableStatus.FREE, order);
		
		cleanDB();
	}
	
	@Test
	public void testUpdate() {
		Table dbTable = tableDao.findById(testTable.getId());
		assertNotNull(dbTable);
		assertNotNull(dbTable.getOrder());
		assertNotNull(dbTable.getOrder().getDishes().keySet().iterator().next());
		
		//Assert testTable.
		assertEquals(TABLE_NAME, dbTable.getName());
		assertEquals(TableStatus.FREE.ordinal(), dbTable.getStatus().ordinal());
		assertEquals(testOrder.getId(), dbTable.getOrder().getId());
		
		dbTable.getOrder().setDish(testDish, 5);
		dbTable.getOrder().setDiners(5);
		dbTable.setName(TABLE_NAME2);
		dbTable.setStatus(TableStatus.BUSY);
		
		tableDao.update(dbTable);
		
		Table dbTableUpdated = tableDao.findById(dbTable.getId());
		
		//Assert update
		assertNotNull(dbTableUpdated);
		assertEquals(5, dbTableUpdated.getOrder().getDishes().get(testDish).intValue());
		assertEquals(TABLE_NAME2, dbTableUpdated.getName());
		assertEquals(dbTableUpdated.getStatus().ordinal(), TableStatus.BUSY.ordinal());
		assertEquals(5, dbTableUpdated.getOrder().getDiners());
	}
}