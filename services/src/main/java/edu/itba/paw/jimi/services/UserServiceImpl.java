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

    public User findById(User user) {
        return null;
    }
}
