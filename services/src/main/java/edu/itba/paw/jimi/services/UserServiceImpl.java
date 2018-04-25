package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.UserDao;
import edu.itba.paw.jimi.interfaces.UserService;
import edu.itba.paw.jimi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public User findById(long id) {
        return userDao.findById(id);
    }

    public Collection<User> findAll() {
        return userDao.findAll();
    }

    public User create(final String username, String password) {
        return userDao.create(username, password);
    }

}
