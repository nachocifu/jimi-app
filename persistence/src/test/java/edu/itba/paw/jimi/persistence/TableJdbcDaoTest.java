package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.DishDao;
import edu.itba.paw.jimi.interfaces.OrderDao;
import edu.itba.paw.jimi.interfaces.TableDao;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import edu.itba.paw.jimi.interfaces.exceptions.TableWithNullOrderException;
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
public class TableJdbcDaoTest {

    private static final String ORDER_TABLE_NAME = "orders";
    private static final String ORDER_ITEM_TABLE_NAME = "orders_items";
    private static final String TABLE_TABLE_NAME = "tables";



    private static final String TABLE_NAME = "Table 1";
    private static final String TABLE_NAME2 = "Table 2";


    private static final String DISH_NAME = "Cambuchá";
    private static final Float DISH_PRICE = 5.25F;
    private static final int DISH_STOCK = 5;

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

    private void cleanDB(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ORDER_TABLE_NAME);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ORDER_ITEM_TABLE_NAME);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TABLE_TABLE_NAME);
    }

    @Test
    public void testCreate() {
        Order order = orderDao.create();
        Table table = tableDao.create(TABLE_NAME, TableStatus.Free, order, 2);
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, TABLE_TABLE_NAME));
        cleanDB();
    }

    @Test
    public void testFindById() {

        final Dish dish = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
        final Order order = orderDao.create();
        order.setDish(dish, 2);
        orderDao.update(order);
        final Table table = tableDao.create(TABLE_NAME, TableStatus.Free, order, 2);

        Table dbTable = tableDao.findById(table.getId());
        assertNotNull(dbTable);
        assertNotNull(dbTable.getOrder());
        assertNotNull(dbTable.getOrder().getDishes().keySet().iterator().next());


        //Assert table.
        assertEquals(TABLE_NAME, dbTable.getName());
        assertEquals(TableStatus.Free.getId(), dbTable.getStatus().getId());
        assertEquals(order.getId(), dbTable.getOrder().getId());
        assertEquals(2, dbTable.getDiners());

        //Assert order.
        Order dbOrder = dbTable.getOrder();
        assertEquals(dbOrder.getDishes().get(dish).intValue(), 2);

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

        tableDao.create(TABLE_NAME, TableStatus.Free, null, 2);

        cleanDB();
    }

    @Test(expected = TableWithNullOrderException.class)
    public void testFindByIdUnsavedOrderException() {

        final Order order = new Order();
        tableDao.create(TABLE_NAME, TableStatus.Free, order, 2);

        cleanDB();
    }


    @Test
    public void testUpdate() {

        final Dish dish = dishDao.create(DISH_NAME, DISH_PRICE, DISH_STOCK);
        final Order order = orderDao.create();
        order.setDish(dish, 2);
        orderDao.update(order);
        final Table table = tableDao.create(TABLE_NAME, TableStatus.Free, order, 2);

        Table dbTable = tableDao.findById(table.getId());
        assertNotNull(dbTable);
        assertNotNull(dbTable.getOrder());
        assertNotNull(dbTable.getOrder().getDishes().keySet().iterator().next());


        //Assert table.
        assertEquals(TABLE_NAME, dbTable.getName());
        assertEquals(TableStatus.Free.getId(), dbTable.getStatus().getId());
        assertEquals(order.getId(), dbTable.getOrder().getId());
        assertEquals(2, dbTable.getDiners());

        //Assert order.
        Order dbOrder = dbTable.getOrder();
        assertEquals(dbOrder.getDishes().get(dish).intValue(), 2);

        //Assert dishes.
        Dish dbDish = dbOrder.getDishes().keySet().iterator().next();
        assertEquals(dish.getId(), dbDish.getId());
        assertEquals(dish.getName(), dbDish.getName());
        assertEquals(dish.getStock(), dbDish.getStock());
        assertEquals(dish.getPrice(), dbDish.getPrice());


        dbTable.getOrder().setDish(dish, 5);
        dbTable.setName(TABLE_NAME2);
        dbTable.setDiners(5);
        dbTable.setStatus(TableStatus.Busy);

        tableDao.update(dbTable);

        Table dbTableUpdated = tableDao.findById(dbTable.getId());

        //Assert update
        assertNotNull(dbTableUpdated);
        assertEquals(dbTableUpdated.getOrder().getDishes().get(dish).intValue(), 5);
        assertEquals(dbTableUpdated.getName(), TABLE_NAME2);
        assertEquals(dbTableUpdated.getStatus().getId(), TableStatus.Busy.getId());
        assertEquals(dbTableUpdated.getDiners(), 5);

        cleanDB();
    }

}