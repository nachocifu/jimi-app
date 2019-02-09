package edu.itba.paw.jimi.webapp.auth;

import edu.itba.paw.jimi.interfaces.services.UserService;
import edu.itba.paw.jimi.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenHandler {

	private final int SECONDS_TO_EXPIRE = 3600;

	@Autowired
	private String tokenSigningKey;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	public String createTokenForUser(String username) {
		final ZonedDateTime now = ZonedDateTime.now();
		final Date expiration = Date.from(now.plusSeconds(SECONDS_TO_EXPIRE).toInstant());
		final User user = Optional.ofNullable(userService.findByUsername(username)).orElseThrow(() -> new AccessDeniedException("User not found."));
		return Jwts.builder()
				.setId(String.valueOf(user.getId()))
				.setSubject(username)
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS512, tokenSigningKey)
				.compact();
	}

	public UserDetails parseUserDetailsFromToken(String token) {
		try {
			final String username = Jwts.parser()
					.setSigningKey(tokenSigningKey)
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
			return userDetailsService.loadUserByUsername(username);
		} catch (SignatureException e) {
			return null;
		}
	}
}
