package edu.itba.paw.jimi.webapp.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter extends GenericFilterBean {

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Allow-Headers", "X-AUTH-TOKEN, Content-Type");
		response.setHeader("Access-Control-Expose-Headers", "X-AUTH-TOKEN");
		response.setHeader("Access-Control-Max-Age", "3600");

		if (request.getMethod().toUpperCase().trim().equals("OPTIONS")) {

			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().flush();
			response.getWriter().close();
			return;
		}

		chain.doFilter(request, response);
	}
}
