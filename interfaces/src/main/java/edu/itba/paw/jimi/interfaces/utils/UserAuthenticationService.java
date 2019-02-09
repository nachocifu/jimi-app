package edu.itba.paw.jimi.interfaces.utils;

public interface UserAuthenticationService {

	/**
	 * Returns true if the current user has the given role.
	 */
	public boolean currentUserHasRole(String role);
}
