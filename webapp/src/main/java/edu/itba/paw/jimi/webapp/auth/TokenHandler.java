package edu.itba.paw.jimi.webapp.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TokenHandler {

	private final int HOURS_TO_EXPIRE = 1;

	@Autowired
	private String tokenSigningKey;

	@Autowired
	private UserDetailsService userDetailsService;

	public String createTokenForUser(String username, Collection<? extends GrantedAuthority> authorities) {
		final ZonedDateTime now = ZonedDateTime.now();
		final Date expiration = Date.from(now.plusHours(HOURS_TO_EXPIRE).toInstant());
		return Jwts.builder()
				.setId(UUID.randomUUID().toString())
				.setSubject(username)
				.claim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setExpiration(expiration)
				.setIssuedAt(Date.from(now.toInstant()))
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
