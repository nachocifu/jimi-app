package edu.itba.paw.jimi.interfaces.daos;

import edu.itba.paw.jimi.models.User;

import java.util.Collection;

public interface UserDao {

    User findById(long id);

    Collection<User> findAll();

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @param password The password of the user.
     * @return The created user.
     */
    User create(String username, String password);


    /**
     * Finds the User by the username.
     * @param username The username to search.
     * @return the user with said username.
     */
    User findByUsername(String username);
}
