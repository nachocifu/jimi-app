package edu.itba.paw.jimi.webapp.exceptionmapper;

import edu.itba.paw.jimi.interfaces.exceptions.DishSetToInactiveOrderException;
import edu.itba.paw.jimi.webapp.dto.ExceptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DishSetToInactiveOrderExceptionMapper extends BusinessExceptionMapper implements ExceptionMapper<DishSetToInactiveOrderException> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DishSetToInactiveOrderExceptionMapper.class);

	@Override
	public Response toResponse(final DishSetToInactiveOrderException exception) {
		LOGGER.warn("Exception: {}", (Object[]) exception.getStackTrace());
		String message = messageByLocaleServiceImpl.getMessage("exception.orders.inactive.set.dish", localeResolver.resolveLocale(request));
		return Response
				.status(Response.Status.CONFLICT)
				.entity(new ExceptionDTO(message))
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}