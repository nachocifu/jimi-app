package edu.itba.paw.jimi.interfaces.services;

import edu.itba.paw.jimi.models.User;

import java.util.Collection;

public interface UserService {

	User findById(final long id);

	/**
	 * Finds the User by the username.
	 *
	 * @param username The username to search.
	 * @return the user with said username.
	 */
	User findByUsername(String username);

	/**
	 * Returns all the users.
	 *
	 * @param maxResults Max results allowed. Alias page size
	 * @param offset     First result start. Alias first result
	 * @return all the users.
	 */
	Collection<User> findAll(int maxResults, int offset);

	/**
	 * Create a new user.
	 *
	 * @param username The name of the user.
	 * @param password The password of the user.
	 * @return The created user.
	 */
	User create(String username, String password);

	/**
	 * Creates a user with admin privileges.
	 *
	 * @param username the admins username.
	 * @param password the admins password.
	 * @return the created user.
	 */
	User createAdmin(String username, String password);

	/**
	 * Deletes a user.
	 *
	 * @param id Id of the user.
	 */
	void delete(final long id);

	int getTotalUsers();

}
