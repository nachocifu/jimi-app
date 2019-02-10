package edu.itba.paw.jimi.webapp.auth;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.json.Json;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class StatelessAuthenticationFilter extends GenericFilterBean {

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			Authentication authentication = tokenAuthenticationService.getAuthentication((HttpServletRequest) request);
			if (authentication != null) SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
			SecurityContextHolder.getContext().setAuthentication(null);
		} catch (JwtException e) {
			response.getWriter().write(convertMessageToJWTErrorJson(e.getMessage()));
			HttpServletResponse hsr = (HttpServletResponse) response;
			hsr.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}

	private String convertMessageToJWTErrorJson(String message) {
		return Json.createObjectBuilder()
				.add("JWTerror", message)
				.build()
				.toString();
	}
}
