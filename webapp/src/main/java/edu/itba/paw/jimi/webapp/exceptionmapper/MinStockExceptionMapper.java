package edu.itba.paw.jimi.webapp.exceptionmapper;

import edu.itba.paw.jimi.interfaces.exceptions.MinStockException;
import edu.itba.paw.jimi.webapp.dto.ExceptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MinStockExceptionMapper extends BusinessExceptionMapper implements ExceptionMapper<MinStockException> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MinStockExceptionMapper.class);

	@Override
	public Response toResponse(final MinStockException exception) {
		LOGGER.warn("Exception: {}", (Object[]) exception.getStackTrace());
		String message = messageSource.getMessage("exception.min.stock", null, localeResolver.resolveLocale(request));
		return Response
				.status(Response.Status.CONFLICT)
				.entity(new ExceptionDTO(message))
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}
