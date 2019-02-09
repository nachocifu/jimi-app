package edu.itba.paw.jimi.webapp.exceptionmapper;

import edu.itba.paw.jimi.interfaces.exceptions.OrderStatusException;
import edu.itba.paw.jimi.webapp.dto.ExceptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.text.MessageFormat;

@Provider
public class OrderStatusExceptionMapper implements ExceptionMapper<OrderStatusException> {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatusExceptionMapper.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LocaleResolver localeResolver;

	@Context
	private HttpServletRequest request;

	@Override
	public Response toResponse(final OrderStatusException exception) {
		LOGGER.warn("Exception: {}", (Object[]) exception.getStackTrace());
		String message = MessageFormat
				.format(messageSource.getMessage("exception.order.status", null, localeResolver.resolveLocale(request)),
						exception.getExpected(),
						exception.getActual());
		return Response
				.status(Response.Status.CONFLICT)
				.entity(new ExceptionDTO(message))
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}