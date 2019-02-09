package edu.itba.paw.jimi.webapp.auth;

import edu.itba.paw.jimi.interfaces.utils.UserAuthenticationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

	@Override
	public boolean currentUserHasRole(String role) {
		return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
				.stream()
				.anyMatch(a -> a.getAuthority().equals(role));
	}
}
