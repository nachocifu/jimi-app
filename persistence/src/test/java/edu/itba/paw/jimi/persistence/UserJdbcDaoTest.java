package edu.itba.paw.jimi.persistence;


import edu.itba.paw.jimi.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


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
        cleanUp();
    }

    private void cleanUp() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void testCreate() {
        final User user = userDao.create(USERNAME, PASSWORD);
        assertNotNull(user);
        assertEquals(USERNAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));

        cleanUp();
    }

    @Test
    public void testFindById() {
        User user = userDao.create(USERNAME, PASSWORD);
        final User dbUser = userDao.findById(user.getId());

        assertEquals(user.getId(), dbUser.getId());
        assertEquals(user.getUsername(), dbUser.getUsername());
        assertEquals(user.getPassword(), dbUser.getPassword());

        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));

        cleanUp();

    }

    @Test
    public void testFindByIdEmpty() {
        final User dbUser = userDao.findById(1);

        assertNull(dbUser);

        cleanUp();

    }


    @Test
    public void testFindByUsernameWithSarasa() {
        final User dbUser = userDao.findByUsername("asd");

        assertNull(dbUser);

        cleanUp();

    }

    @Test
    public void testFindByUsernameEmpty() {
        final User dbUser = userDao.findByUsername("");

        assertNull(dbUser);

        cleanUp();

    }

    @Test
    public void testFindByUsernameNull() {
        final User dbUser = userDao.findByUsername(null);

        assertNull(dbUser);

        cleanUp();

    }


    @Test
    public void testFindByUsername() {
        User user = userDao.create(USERNAME, PASSWORD);
        final User dbUser = userDao.findByUsername(USERNAME);

        assertEquals(user.getId(), dbUser.getId());
        assertEquals(user.getUsername(), dbUser.getUsername());
        assertEquals(user.getPassword(), dbUser.getPassword());

        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));

        cleanUp();

    }

    @Test
    public void testFindAllEmpty() {
        Object object = userDao.findAll();

        assertNotNull(object);
        assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"), ((Collection) object).size());
    }


    @Test
    public void testFindAllWithSome() {

        userDao.create(USERNAME + "1", PASSWORD);
        userDao.create(USERNAME + "2", PASSWORD);
        userDao.create(USERNAME + "3", PASSWORD);
        userDao.create(USERNAME + "4", PASSWORD);


        Object object = userDao.findAll();

        assertNotNull(object);
        assertEquals(4, ((Collection) object).size());

        cleanUp();
    }


    @Test(expected = DuplicateKeyException.class)
    public void testCreateWithSameUsername() {

        userDao.create(USERNAME, PASSWORD);
        userDao.create(USERNAME, PASSWORD);

        cleanUp();
    }

}

