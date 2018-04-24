package edu.itba.paw.jimi.interfaces;

import edu.itba.paw.jimi.models.User;

public interface UserDao {

    User findById(long id);

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @return The created user.
     */
    User create(String username);
}
