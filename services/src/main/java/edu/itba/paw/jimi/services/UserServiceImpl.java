package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.UserDao;
import edu.itba.paw.jimi.interfaces.services.UserService;
import edu.itba.paw.jimi.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	public User findById(final long id) {
		return userDao.findById(id);
	}
	
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	public Collection<User> findAll() {
		Collection<User> users = userDao.findAll();
		if (users != null)
			return users;
		else
			return new HashSet<User>();
	}
	
	public User create(final String username, String password) {
		Set<String> roles = new HashSet<String>();
		roles.add(User.ROLE_USER);
		LOGGER.info("Created user {}", username);
		return userDao.create(username, password, roles);
	}
	
	public User createAdmin(String username, String password) {
		Set<String> roles = new HashSet<String>();
		roles.add(User.ROLE_USER);
		roles.add(User.ROLE_ADMIN);
		LOGGER.info("Created admin user {}", username);
		return userDao.create(username, password, roles);
	}
	
}
