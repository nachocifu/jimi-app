package edu.itba.paw.jimi.webapp.exceptionmapper;

import edu.itba.paw.jimi.interfaces.exceptions.TableStatusInvalidTransitionException;
import edu.itba.paw.jimi.webapp.dto.ExceptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.text.MessageFormat;

@Provider
public class TableStatusInvalidTransitionExceptionMapper extends BusinessExceptionMapper implements ExceptionMapper<TableStatusInvalidTransitionException> {

	private static final Logger LOGGER = LoggerFactory.getLogger(TableStatusInvalidTransitionExceptionMapper.class);

	@Override
	public Response toResponse(final TableStatusInvalidTransitionException exception) {
		LOGGER.warn("Exception: {}", (Object[]) exception.getStackTrace());
		String message = MessageFormat
				.format(messageSource.getMessage("exception.table.status", null, localeResolver.resolveLocale(request)),
						exception.getExpected(),
						exception.getActual());
		return Response
				.status(Response.Status.CONFLICT)
				.entity(new ExceptionDTO(message))
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}