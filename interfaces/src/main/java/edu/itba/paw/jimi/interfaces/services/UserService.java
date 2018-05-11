package edu.itba.paw.jimi.interfaces.services;

import edu.itba.paw.jimi.models.User;

import java.util.Collection;
import java.util.List;

public interface UserService {

    User findById(long id);

    /**
     * Finds the User by the username.
     * @param username The username to search.
     * @return the user with said username.
     */
    User findByUsername(String username);

    /**
     * Returns all the users.
     *
     * @return all the users.
     */
    Collection<User> findAll();

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @return The created user.
     */
    User create(String username, String password);
}
