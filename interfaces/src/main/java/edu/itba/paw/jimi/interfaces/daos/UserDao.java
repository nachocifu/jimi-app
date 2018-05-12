package edu.itba.paw.jimi.interfaces.daos;

import edu.itba.paw.jimi.models.User;

import java.util.Collection;
import java.util.Set;

public interface UserDao {

    User findById(long id);

    Collection<User> findAll();

    /**
     * Create a new user.
     *
     * @param username The name of the user.
     * @param password The password of the user.
     * @param roles A set of roles.
     * @return The created user.
     */
    User create(String username, String password, Set<String> roles);


    /**
     * Finds the User by the username.
     * @param username The username to search.
     * @return the user with said username.
     */
    User findByUsername(String username);


    /**
     * Updates all the contents of the user.
     * @param user The user to be updated.
     */
    void update(User user);
}
