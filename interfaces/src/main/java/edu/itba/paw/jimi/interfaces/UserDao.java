package edu.itba.paw.jimi.interfaces;

import edu.itba.paw.jimi.models.User;

public interface UserDao {

    public User findById(int id);
}
