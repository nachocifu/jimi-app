package edu.itba.paw.jimi.webapp.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenHandler {
	
	@Autowired
	private String tokenSigningKey;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	public String createTokenForUser(String username) {
		return Jwts.builder()
				.setId(UUID.randomUUID().toString())
				.setSubject(username)
				.signWith(SignatureAlgorithm.HS512, tokenSigningKey)
				.compact();
	}
	
	public UserDetails parseUserFromToken(String token) {
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
