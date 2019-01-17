package edu.itba.paw.jimi.persistence;


import edu.itba.paw.jimi.interfaces.daos.UserDao;
import edu.itba.paw.jimi.models.User;
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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class UserDaoTest {
	
	private static final String PASSWORD = "test";
	private static final String USERNAME = "test";
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private UserDao userDao;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		cleanUp();
	}
	
	private void cleanUp() {
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}
	
	@Test
	public void testCreate() {
		final User user = userDao.create(USERNAME, PASSWORD, null);
		assertNotNull(user);
		assertEquals(USERNAME, user.getUsername());
		assertEquals(PASSWORD, user.getPassword());
		assertNotNull(user.getRoles());
		
		cleanUp();
	}
	
	
	@Test
	public void testCreateWithRoles() {
		Set<String> roles = new HashSet<>();
		roles.add(User.ROLE_ADMIN);
		roles.add(User.ROLE_USER);
		
		final User user = userDao.create(USERNAME, PASSWORD, roles);
		assertNotNull(user);
		assertEquals(USERNAME, user.getUsername());
		assertEquals(PASSWORD, user.getPassword());
		
		assertEquals(2, user.getRoles().size());
		assertEquals(true, user.getRoles().contains(User.ROLE_ADMIN));
		assertEquals(true, user.getRoles().contains(User.ROLE_USER));
		
		cleanUp();
	}
	
	@Test
	public void testUpdate() {
		final User user = userDao.create(USERNAME, PASSWORD, null);
		assertNotNull(user);
		User dbUser = userDao.findById(user.getId());
		
		dbUser.setUsername(USERNAME + "1");
		dbUser.setPassword(PASSWORD + "1");
		
		userDao.update(dbUser);
		
		User dbUser2 = userDao.findById(user.getId());
		
		assertEquals(USERNAME + "1", dbUser2.getUsername());
		assertEquals(PASSWORD + "1", dbUser2.getPassword());
		
		cleanUp();
	}
	
	@Test
	public void testUpdateNewRoles() {
		final User user = userDao.create(USERNAME, PASSWORD, null);
		assertNotNull(user);
		User dbUser = userDao.findById(user.getId());
		
		dbUser.setUsername(USERNAME + "1");
		dbUser.setPassword(PASSWORD + "1");
		Set<String> roles = new HashSet<String>();
		roles.add(User.ROLE_ADMIN);
		dbUser.setRoles(roles);
		
		userDao.update(dbUser);
		
		User dbUser2 = userDao.findById(user.getId());
		
		assertEquals(USERNAME + "1", dbUser2.getUsername());
		assertEquals(PASSWORD + "1", dbUser2.getPassword());
		assertEquals(1, dbUser2.getRoles().size());
		assertEquals(true, dbUser2.getRoles().contains(User.ROLE_ADMIN));
		
		cleanUp();
	}
	
	@Test
	public void testUpdateDifferentRole() {
		Set<String> roles1 = new HashSet<String>();
		roles1.add(User.ROLE_ADMIN);
		final User user = userDao.create(USERNAME, PASSWORD, roles1);
		assertNotNull(user);
		User dbUser = userDao.findById(user.getId());
		
		dbUser.setUsername(USERNAME + "1");
		dbUser.setPassword(PASSWORD + "1");
		assertEquals(1, dbUser.getRoles().size());
		assertEquals(true, dbUser.getRoles().contains(User.ROLE_ADMIN));
		
		
		Set<String> roles = new HashSet<String>();
		roles.add(User.ROLE_USER);
		dbUser.setRoles(roles);
		
		userDao.update(dbUser);
		
		User dbUser2 = userDao.findById(user.getId());
		
		assertEquals(USERNAME + "1", dbUser2.getUsername());
		assertEquals(PASSWORD + "1", dbUser2.getPassword());
		assertEquals(1, dbUser2.getRoles().size());
		assertEquals(true, dbUser2.getRoles().contains(User.ROLE_USER));
		
		cleanUp();
	}
	
	@Test
	public void testUpdateDifferentRoles() {
		Set<String> roles1 = new HashSet<String>();
		roles1.add(User.ROLE_ADMIN);
		final User user = userDao.create(USERNAME, PASSWORD, roles1);
		assertNotNull(user);
		User dbUser = userDao.findById(user.getId());
		
		dbUser.setUsername(USERNAME + "1");
		dbUser.setPassword(PASSWORD + "1");
		assertEquals(1, dbUser.getRoles().size());
		assertEquals(true, dbUser.getRoles().contains(User.ROLE_ADMIN));
		
		
		Set<String> roles = new HashSet<String>();
		roles.add(User.ROLE_USER);
		roles.add(User.ROLE_ADMIN);
		dbUser.setRoles(roles);
		
		userDao.update(dbUser);
		
		User dbUser2 = userDao.findById(user.getId());
		
		assertEquals(USERNAME + "1", dbUser2.getUsername());
		assertEquals(PASSWORD + "1", dbUser2.getPassword());
		assertEquals(2, dbUser2.getRoles().size());
		assertEquals(true, dbUser2.getRoles().contains(User.ROLE_USER));
		assertEquals(true, dbUser2.getRoles().contains(User.ROLE_ADMIN));
		
		cleanUp();
	}
	
	@Test
	public void testUpdateNoMoreRolesNull() {
		Set<String> roles1 = new HashSet<String>();
		roles1.add(User.ROLE_ADMIN);
		roles1.add(User.ROLE_USER);
		final User user = userDao.create(USERNAME, PASSWORD, roles1);
		assertNotNull(user);
		User dbUser = userDao.findById(user.getId());
		
		dbUser.setUsername(USERNAME + "1");
		dbUser.setPassword(PASSWORD + "1");
		assertEquals(2, dbUser.getRoles().size());
		assertEquals(true, dbUser.getRoles().contains(User.ROLE_ADMIN));
		
		dbUser.setRoles(null); //If you set roles as null, then we do not alter the roles.
		
		userDao.update(dbUser);
		
		User dbUser2 = userDao.findById(user.getId());
		
		assertEquals(USERNAME + "1", dbUser2.getUsername());
		assertEquals(PASSWORD + "1", dbUser2.getPassword());
		assertEquals(2, dbUser2.getRoles().size());
		assertEquals(true, dbUser2.getRoles().contains(User.ROLE_USER));
		assertEquals(true, dbUser2.getRoles().contains(User.ROLE_ADMIN));
		
		cleanUp();
	}
	
	@Test
	public void testUpdateNoMoreRolesEmpty() {
		Set<String> roles1 = new HashSet<String>();
		roles1.add(User.ROLE_ADMIN);
		roles1.add(User.ROLE_USER);
		final User user = userDao.create(USERNAME, PASSWORD, roles1);
		assertNotNull(user);
		User dbUser = userDao.findById(user.getId());
		
		dbUser.setUsername(USERNAME + "1");
		dbUser.setPassword(PASSWORD + "1");
		assertEquals(2, dbUser.getRoles().size());
		assertEquals(true, dbUser.getRoles().contains(User.ROLE_ADMIN));
		
		
		dbUser.setRoles(new HashSet<String>());
		
		userDao.update(dbUser);
		
		User dbUser2 = userDao.findById(user.getId());
		
		assertEquals(USERNAME + "1", dbUser2.getUsername());
		assertEquals(PASSWORD + "1", dbUser2.getPassword());
		assertEquals(0, dbUser2.getRoles().size());
		
		cleanUp();
	}
	
	@Test
	public void testFindById() {
		User user = userDao.create(USERNAME, PASSWORD, null);
		final User dbUser = userDao.findById(user.getId());
		
		assertEquals(user.getId(), dbUser.getId());
		assertEquals(user.getUsername(), dbUser.getUsername());
		assertEquals(user.getPassword(), dbUser.getPassword());
		
		//assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
		
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
		User user = userDao.create(USERNAME, PASSWORD, null);
		final User dbUser = userDao.findByUsername(USERNAME);
		
		assertEquals(user.getId(), dbUser.getId());
		assertEquals(user.getUsername(), dbUser.getUsername());
		assertEquals(user.getPassword(), dbUser.getPassword());
		
		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
		
		cleanUp();
		
	}
	
	@Test
	public void testFindAllEmpty() {
		Collection<User> col = userDao.findAll();
		assertNotNull(col);
		//assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"), ((Collection) object).size());
	}
	
	
	@Test
	public void testFindAllWithSome() {
		
		userDao.create(USERNAME + "1", PASSWORD, null);
		userDao.create(USERNAME + "2", PASSWORD, null);
		userDao.create(USERNAME + "3", PASSWORD, null);
		userDao.create(USERNAME + "4", PASSWORD, null);
		
		Object object = userDao.findAll();
		
		assertNotNull(object);
		assertEquals(4, ((Collection) object).size());
		
		cleanUp();
	}
	
	//@Test(expected = DuplicateKeyException.class)
	//public void testCreateWithSameUsername() {
	//	userDao.create(USERNAME, PASSWORD, null);
	//	userDao.create(USERNAME, PASSWORD, null);
	//}
	
}

