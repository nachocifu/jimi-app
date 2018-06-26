package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;

import static junit.framework.TestCase.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class OrderJdbcDaoTest {

	@Autowired
	private DataSource ds;

	@Qualifier("orderHibernateDao")
	@Autowired
	private OrderDao orderDao;

	@Qualifier("dishHibernateDao")
	@Autowired
	private DishDao dishDao; //Here we are not using a mocked dao because orderDao uses a union on DB to get the dishes, so mocking it would break the union.

	private static final String ORDER_TABLE_NAME = "orders";
	private static final String ORDER_ITEM_TABLE_NAME = "orders_items";

	private static final String DISH_NAME = "Cambuchá";
	private static final Float DISH_PRICE = 5.25F;
	private static final int DISH_STOCK = 5;

	private static final String DISH_NAME2 = "Hamburguesa";
	private static final Float DISH_PRICE2 = 92.6F;
	private static final int DISH_STOCK2 = 19;

	private static final String DISH_NAME3 = "Milanesa";
	private static final Float DISH_PRICE3 = 0.6F;
	private static final int DISH_STOCK3 = 1;

	private static final Timestamp OPENEDAT = new Timestamp(1525467178);
	private static final Timestamp CLOSEDAT = new Timestamp(1525467178 + 60 * 60);

	private static final int DINERS = 2;
	private static final float TOTAL = 2f;


	@Before
	public void setUp() {
	}

	@Test
    @Transactional
	public void testCreate() {
		Order order = orderDao.create(OrderStatus.INACTIVE, null, null, 0, 0);
		assertNotNull(order);
        assertNotNull(order.getId());
	}

	@Test
    @Transactional
	public void testUpdate() {
		final Order order = orderDao.create(OrderStatus.INACTIVE, null, null, 0, TOTAL);
		order.setStatus(OrderStatus.OPEN);
		order.setOpenedAt(OPENEDAT);
		order.setClosedAt(CLOSEDAT);
		order.setDiners(DINERS);

		orderDao.update(order);

		Order dbOrder = orderDao.findById(order.getId());
		assertNotNull(dbOrder);
		assertEquals(OrderStatus.OPEN, dbOrder.getStatus());
		assertEquals(OPENEDAT, dbOrder.getOpenedAt());
		assertEquals(CLOSEDAT, dbOrder.getClosedAt());
		assertEquals(DINERS, dbOrder.getDiners());
		assertEquals(TOTAL, dbOrder.getTotal());

	}

	@Test
    @Transactional
	public void testFindByIdEmpty() {
		final Order order = orderDao.create(OrderStatus.INACTIVE, null, null, 0, 0);
		Order dbOrder = orderDao.findById(order.getId());
		assertNotNull(dbOrder);
	}

	@Test
    @Transactional
	public void testFindByIdWithValues() {
		final Order order = orderDao.create(OrderStatus.OPEN, OPENEDAT, CLOSEDAT, DINERS, 0);
		Order dbOrder = orderDao.findById(order.getId());
		assertNotNull(dbOrder);
		assertEquals(OrderStatus.OPEN.ordinal(), dbOrder.getStatus().ordinal());
		assertEquals(OPENEDAT, dbOrder.getOpenedAt());
		assertEquals(CLOSEDAT, dbOrder.getClosedAt());
		assertEquals(DINERS, dbOrder.getDiners());
	}

	@Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public void testFindByIdOneDish() {

        final Dish dish = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
        final Order order = orderDao.create(OrderStatus.INACTIVE, null, null, 0, 0);

		order.setDish(dish, 1);

		System.out.println("DISH: " + dishDao.findById(dish.getId()).getId());

		orderDao.update(order);

		Order dbOrder = orderDao.findById(order.getId());
		assertNotNull(dbOrder);

		assertNotNull(dbOrder.getDishes().get(dish));


		int amount = dbOrder.getDishes().get(dish);
		assertEquals(1, amount);

		Dish dbDish = dbOrder.getDishes().keySet().iterator().next();
		assertEquals(dish.getName(), dbDish.getName());
		assertEquals(dish.getPrice(), dbDish.getPrice());
		assertEquals(dish.getStock(), dbDish.getStock());
		assertEquals(dish.getId(), dbDish.getId());

	}

	@Test
    @Transactional
	public void testFindByIdOneDishThrice() {
		final Order order = orderDao.create(OrderStatus.INACTIVE, null, null, 0, 0);
		final Dish dish = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
		order.setDish(dish, 3);

		orderDao.update(order);

		Order dbOrder = orderDao.findById(order.getId());
		assertNotNull(dbOrder);

		assertNotNull(dbOrder.getDishes().get(dish));


		int amount = dbOrder.getDishes().get(dish);
		assertEquals(3, amount);

		Dish dbDish = dbOrder.getDishes().keySet().iterator().next();
		assertEquals(dish.getName(), dbDish.getName());
		assertEquals(dish.getPrice(), dbDish.getPrice());
		assertEquals(dish.getStock(), dbDish.getStock());
		assertEquals(dish.getId(), dbDish.getId());

	}

	@Test
    @Transactional
	public void testFindByIdSeveralDishes() {
		final Order order = orderDao.create(OrderStatus.INACTIVE, null, null, 0, 0);

		final Dish dish = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
		order.setDish(dish, 3);

		final Dish dish2 = dishDao.create(DISH_NAME2, DISH_PRICE2, DISH_STOCK2);
		order.setDish(dish2, 5);

		final Dish dish3 = dishDao.create(DISH_NAME3, DISH_PRICE3, DISH_STOCK3);
		order.setDish(dish3, 1);


		orderDao.update(order);


		Order dbOrder = orderDao.findById(order.getId());
		assertNotNull(dbOrder);

		assertNotNull(dbOrder.getDishes().get(dish));
		assertNotNull(dbOrder.getDishes().get(dish2));
		assertNotNull(dbOrder.getDishes().get(dish3));


		int amount = dbOrder.getDishes().get(dish);
		assertEquals(3, amount);

		int amount2 = dbOrder.getDishes().get(dish2);
		assertEquals(5, amount2);

		int amount3 = dbOrder.getDishes().get(dish3);
		assertEquals(1, amount3);


		for (Dish dbDish : dbOrder.getDishes().keySet()) {
			if (dbDish.getId() == dish.getId()) {
				assertEquals(dish.getName(), dbDish.getName());
				assertEquals(dish.getPrice(), dbDish.getPrice());
				assertEquals(dish.getStock(), dbDish.getStock());
				assertEquals(dish.getId(), dbDish.getId());
			} else if (dbDish.getId() == dish2.getId()) {
				assertEquals(dish2.getName(), dbDish.getName());
				assertEquals(dish2.getPrice(), dbDish.getPrice());
				assertEquals(dish2.getStock(), dbDish.getStock());
				assertEquals(dish2.getId(), dbDish.getId());
			} else if (dbDish.getId() == dish3.getId()) {
				assertEquals(dish3.getName(), dbDish.getName());
				assertEquals(dish3.getPrice(), dbDish.getPrice());
				assertEquals(dish3.getStock(), dbDish.getStock());
				assertEquals(dish3.getId(), dbDish.getId());
			} else {
				assertEquals(0, 1);
			}

		}

	}

	@Test
    @Transactional
	public void testFindByIdAddAndRemove() {
		final Order order = orderDao.create(OrderStatus.INACTIVE, null, null, 0, 0);
		final Dish dish = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
		order.setDish(dish, 1);


		orderDao.update(order);

		Order dbOrder = orderDao.findById(order.getId());
		assertNotNull(dbOrder);

		assertNotNull(dbOrder.getDishes().get(dish));


		int amount = dbOrder.getDishes().get(dish);
		assertEquals(1, amount);

		Dish dbDish = dbOrder.getDishes().keySet().iterator().next();
		assertEquals(dish.getName(), dbDish.getName());
		assertEquals(dish.getPrice(), dbDish.getPrice());
		assertEquals(dish.getStock(), dbDish.getStock());
		assertEquals(dish.getId(), dbDish.getId());

		order.setDish(dish, 0);

		orderDao.update(order);


		dbOrder = orderDao.findById(order.getId());
		assertNotNull(dbOrder);

		assertNull(dbOrder.getDishes().get(dish));

	}

	@Test
    @Transactional
	public void testFindByIdAddAndRemoveButNoDelete() {
		final Order order = orderDao.create(OrderStatus.INACTIVE, null, null, 0, 0);
		final Dish dish = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
		order.setDish(dish, 2);


		orderDao.update(order);

		Order dbOrder = orderDao.findById(order.getId());
		assertNotNull(dbOrder);

		assertNotNull(dbOrder.getDishes().get(dish));


		int amount = dbOrder.getDishes().get(dish);
		assertEquals(2, amount);

		Dish dbDish = dbOrder.getDishes().keySet().iterator().next();
		assertEquals(dish.getName(), dbDish.getName());
		assertEquals(dish.getPrice(), dbDish.getPrice());
		assertEquals(dish.getStock(), dbDish.getStock());
		assertEquals(dish.getId(), dbDish.getId());

		order.setDish(dish, 1);

		orderDao.update(order);


		dbOrder = orderDao.findById(order.getId());
		assertNotNull(dbOrder);

		assertNotNull(dbOrder.getDishes().get(dish));


		amount = dbOrder.getDishes().get(dish);
		assertEquals(1, amount);

		dbDish = dbOrder.getDishes().keySet().iterator().next();
		assertEquals(dish.getName(), dbDish.getName());
		assertEquals(dish.getPrice(), dbDish.getPrice());
		assertEquals(dish.getStock(), dbDish.getStock());
		assertEquals(dish.getId(), dbDish.getId());

	}

}