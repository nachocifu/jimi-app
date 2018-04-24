package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.UserDao;
import edu.itba.paw.jimi.interfaces.UserService;
import edu.itba.paw.jimi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public User findById(long id) {
        return null;
    }

    public User create(final String username) {
        return userDao.create(username);
    }

}
