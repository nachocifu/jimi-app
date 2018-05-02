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
    public void testFindById() {
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

}
