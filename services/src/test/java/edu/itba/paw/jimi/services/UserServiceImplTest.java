package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.UserDao;
import edu.itba.paw.jimi.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	private static final String USERNAME = "test";
	private static final String PASSWORD = "test";
	private static Set<String> userRoles;
	private static Set<String> adminRoles;
	private static int offset = 0;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Mock
	private UserDao userDao;

	@Before
	public void before() {
		userRoles = new HashSet<String>();
		userRoles.add(User.ROLE_USER);

		adminRoles = new HashSet<String>();
		adminRoles.add(User.ROLE_USER);
		adminRoles.add(User.ROLE_ADMIN);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createTest() {

		// Mocking
		User returnUser = new User(USERNAME, 1, PASSWORD);
		returnUser.setRoles(userRoles);
		Mockito.when(userDao.create(USERNAME, PASSWORD, userRoles)).thenReturn(returnUser);
		// Mocking

		User user = userServiceImpl.create(USERNAME, PASSWORD);
		assertEquals(USERNAME, user.getUsername());
		assertEquals(PASSWORD, user.getPassword());

		assertEquals(1, user.getRoles().size());
		assertTrue(user.getRoles().contains(User.ROLE_USER));
	}

	@Test
	public void createAdminTest() {

		// Mocking
		User returnUser = new User(USERNAME, 1, PASSWORD);
		returnUser.setRoles(adminRoles);
		Mockito.when(userDao.create(USERNAME, PASSWORD, adminRoles)).thenReturn(returnUser);
		// Mocking

		User user = userServiceImpl.createAdmin(USERNAME, PASSWORD);
		assertEquals(USERNAME, user.getUsername());
		assertEquals(PASSWORD, user.getPassword());

		assertEquals(2, user.getRoles().size());
		assertTrue(user.getRoles().contains(User.ROLE_USER));
		assertTrue(user.getRoles().contains(User.ROLE_ADMIN));
	}

	@Test
	public void findByIdTest() {

		// Mocking
		User returnUser = new User(USERNAME, 1, PASSWORD);
		returnUser.setRoles(adminRoles);
		Mockito.when(userDao.findById(1)).thenReturn(returnUser);
		// Mocking

		User dbUser = userServiceImpl.findById(1);
		assertNotNull(dbUser);
		assertEquals(1, dbUser.getId());
		assertEquals(USERNAME, dbUser.getUsername());
		assertEquals(PASSWORD, dbUser.getPassword());

	}

	@Test
	public void findByIdFailTest() {

		// Mocking
		Mockito.when(userDao.findById(1)).thenReturn(null);
		// Mocking

		User dbUser = userServiceImpl.findById(1);
		assertNull(dbUser);
	}

	@Test
	public void findByUsernameTest() {

		// Mocking
		User returnUser = new User(USERNAME, 1, PASSWORD);
		returnUser.setRoles(adminRoles);
		Mockito.when(userDao.findByUsername(USERNAME)).thenReturn(returnUser);
		// Mocking

		User dbUser = userServiceImpl.findByUsername(USERNAME);
		assertNotNull(dbUser);
		assertEquals(1, dbUser.getId());
		assertEquals(USERNAME, dbUser.getUsername());
		assertEquals(PASSWORD, dbUser.getPassword());

	}

	@Test
	public void findByUsernameFailTest() {

		// Mocking
		Mockito.when(userDao.findByUsername(USERNAME)).thenReturn(null);
		// Mocking

		User dbUser = userServiceImpl.findByUsername(USERNAME);
		assertNull(dbUser);
	}

	@Test
	public void findAllTest() {

		// Mocking
		User returnUser1 = new User(USERNAME, 1, PASSWORD);
		User returnUser2 = new User(USERNAME, 2, PASSWORD);
		User returnUser3 = new User(USERNAME, 3, PASSWORD);
		returnUser1.setRoles(adminRoles);
		returnUser2.setRoles(userRoles);
		returnUser3.setRoles(adminRoles);
		Set<User> returnUsers = new HashSet<User>();
		returnUsers.add(returnUser1);
		returnUsers.add(returnUser2);
		returnUsers.add(returnUser3);
		Mockito.when(userDao.findAll(any(Integer.class), any(Integer.class))).thenReturn(returnUsers);
		// Mocking

		Collection<User> dbUsers = userServiceImpl.findAll(returnUsers.size(), offset);
		assertNotNull(dbUsers);

		assertEquals(3, dbUsers.size());
	}

	@Test
	public void findAllRepeatedTest() {

		// Mocking
		User returnUser1 = new User(USERNAME, 1, PASSWORD);
		User returnUser2 = new User(USERNAME, 1, PASSWORD);
		User returnUser3 = new User(USERNAME, 3, PASSWORD);
		returnUser1.setRoles(adminRoles);
		returnUser2.setRoles(userRoles);
		returnUser3.setRoles(adminRoles);
		Set<User> returnUsers = new HashSet<User>();
		returnUsers.add(returnUser1);
		returnUsers.add(returnUser2);
		returnUsers.add(returnUser3);
		Mockito.when(userDao.findAll(any(Integer.class), any(Integer.class))).thenReturn(returnUsers);
		// Mocking

		Collection<User> dbUsers = userServiceImpl.findAll(returnUsers.size(), offset);

		assertEquals(2, dbUsers.size());
	}

	@Test
	public void findAllEmptyTest() {

		// Mocking
		Set<User> returnUsers = new HashSet<User>();
		Mockito.when(userDao.findAll(any(Integer.class), any(Integer.class))).thenReturn(returnUsers);
		// Mocking

		Collection<User> dbUsers = userServiceImpl.findAll(returnUsers.size(), offset);
		assertNotNull(dbUsers);

		assertEquals(0, dbUsers.size());
	}

	@Test
	public void findAllNullTest() {

		// Mocking
		Mockito.when(userDao.findAll(any(Integer.class), any(Integer.class))).thenReturn(null);

		// Mocking

		Collection<User> dbUsers = userServiceImpl.findAll(10, offset);
		assertNotNull(dbUsers);

		assertEquals(0, dbUsers.size());
	}

	@Test
	public void deleteUser() {
		User user = new User(USERNAME, 1, PASSWORD);
		Mockito.when(userDao.findById(any(Integer.class))).thenReturn(user);
		userServiceImpl.delete(user.getId());
	}

}