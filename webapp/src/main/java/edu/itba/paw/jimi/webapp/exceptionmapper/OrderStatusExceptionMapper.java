package edu.itba.paw.jimi.webapp.exceptionmapper;

import edu.itba.paw.jimi.interfaces.exceptions.OrderStatusException;
import edu.itba.paw.jimi.webapp.dto.ExceptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.text.MessageFormat;

@Provider
public class OrderStatusExceptionMapper extends BusinessExceptionMapper implements ExceptionMapper<OrderStatusException> {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatusExceptionMapper.class);

	@Override
	public Response toResponse(final OrderStatusException exception) {
		LOGGER.warn("Exception: {}", (Object[]) exception.getStackTrace());
		String message = MessageFormat
				.format(messageByLocaleServiceImpl.getMessage("exception.order.status", localeResolver.resolveLocale(request)),
						exception.getExpected(),
						exception.getActual());
		return Response
				.status(Response.Status.CONFLICT)
				.entity(new ExceptionDTO(message))
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}