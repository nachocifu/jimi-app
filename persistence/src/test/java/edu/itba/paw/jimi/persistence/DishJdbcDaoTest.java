package edu.itba.paw.jimi.persistence;


import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.User;
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

import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.postgresql.shaded.com.ongres.scram.common.ScramAttributes.USERNAME;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class DishJdbcDaoTest {

    private static final String NAME = "Cambuchá";

    private static final String PI_NAME = "Pie";

    private static final Float PRICE = 5.25F;

    private static final Float REAL_PRICE = (float) Math.PI;

    @Autowired
    private DataSource ds;

    private DishJdbcDao dishDao;

    private JdbcTemplate jdbcTemplate;


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        dishDao = new DishJdbcDao(ds);
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

    //TODO: Cuando esté hecho 'update', testear (y también hacer test de services)
}
