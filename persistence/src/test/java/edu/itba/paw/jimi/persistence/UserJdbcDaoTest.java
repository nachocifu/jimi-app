package edu.itba.paw.jimi.persistence;


import edu.itba.paw.jimi.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import static junit.framework.Assert.*;
import javax.sql.DataSource;
import java.util.Collection;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class UserJdbcDaoTest {

    private static final String PASSWORD = "test";

    private static final String USERNAME = "test";

    @Autowired
    private DataSource ds;

    private UserJdbcDao userDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        userDao = new UserJdbcDao(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void testCreate() {
        final User user = userDao.create(USERNAME, PASSWORD);
        assertNotNull(user);
        assertEquals(USERNAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

    @Test
    // TODO
    public void testFindById() {  //consultar como testear
        Object user = userDao.findById(1);
    }

    @Test
    public void testFindAll() {
        Object object = userDao.findAll();

        assertNotNull(object);
        assertEquals(((Collection) object).size(), JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
    }

}

