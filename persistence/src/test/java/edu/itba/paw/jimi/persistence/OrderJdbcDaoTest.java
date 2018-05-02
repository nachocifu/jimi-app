package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.DishDao;
import edu.itba.paw.jimi.interfaces.OrderDao;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
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

import java.util.Iterator;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class OrderJdbcDaoTest {

    @Autowired
    private DataSource ds;

    private OrderDao orderDao;
    private DishDao dishDao;

    private JdbcTemplate jdbcTemplate;

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


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        orderDao = new OrderJdbcDao(ds);
        dishDao = new DishJdbcDao(ds);
        cleanDB();
    }

    private void cleanDB(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ORDER_TABLE_NAME);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ORDER_ITEM_TABLE_NAME);
    }

    @Test
    public void testCreate() {
        orderDao.create();
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, ORDER_TABLE_NAME));
        assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, ORDER_ITEM_TABLE_NAME));
        cleanDB();
    }

    @Test
    public void testFindByIdEmpty() {
        final Order order = orderDao.create();
        Order dbOrder = orderDao.findById(order.getId());
        assertNotNull(dbOrder);
        cleanDB();
    }

    @Test
    public void testFindByIdOneDish() {
        final Order order = orderDao.create();
        final Dish dish = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
        order.addDish(dish);

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

        cleanDB();
    }

    @Test
    public void testFindByIdOneDishThrice() {
        final Order order = orderDao.create();
        final Dish dish = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
        order.addDish(dish);
        order.addDish(dish);
        order.addDish(dish);

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

        cleanDB();
    }

    @Test
    public void testFindByIdSeveralDishes() {
        final Order order = orderDao.create();

        final Dish dish = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
        order.addDish(dish);
        order.addDish(dish);
        order.addDish(dish);

        final Dish dish2 = dishDao.create(DISH_NAME2, DISH_PRICE2, DISH_STOCK2);
        order.addDish(dish2);
        order.addDish(dish2);
        order.addDish(dish2);
        order.addDish(dish2);
        order.addDish(dish2);

        final Dish dish3 = dishDao.create(DISH_NAME3, DISH_PRICE3, DISH_STOCK3);
        order.addDish(dish3);


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

        cleanDB();
    }

}
