package edu.itba.paw.jimi.interfaces.daos;

import edu.itba.paw.jimi.models.User;


/**
 * The purpose of this Dao is to insert and update information about the UserRoles table.
 * Not to get individual or several items because this is done on the UserDao implementation.
 * This is basically a mapping of the Set<String> inside User to de DB.
 */
public interface UserRolesDao {
	
	/**
	 * Create a role for a user.
	 *
	 * @param user The user.
	 * @param role The string role.
	 */
	void create(User user, String role);
	
	/**
	 * Removes instances of the role of the user.
	 *
	 * @param user the user to remove a role from.
	 * @param role the role to remove.
	 */
	void delete(User user, String role);
	
	
	/**
	 * Removes all the roles from the user.
	 *
	 * @param user the user to remove all roles from.
	 */
	void deleteAllFromUser(User user);
}
