package edu.itba.paw.jimi.webapp.exceptionmapper;

import edu.itba.paw.jimi.interfaces.exceptions.TableWithNullOrderException;
import edu.itba.paw.jimi.webapp.dto.ExceptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TableWithNullOrderExceptionMapper extends BusinessExceptionMapper implements ExceptionMapper<TableWithNullOrderException> {

	private static final Logger LOGGER = LoggerFactory.getLogger(TableWithNullOrderExceptionMapper.class);

	@Override
	public Response toResponse(final TableWithNullOrderException exception) {
		LOGGER.warn("Exception: {}", (Object[]) exception.getStackTrace());
		String message = messageByLocaleServiceImpl.getMessage("exception.table.null.order", localeResolver.resolveLocale(request));
		return Response
				.status(Response.Status.CONFLICT)
				.entity(new ExceptionDTO(message))
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
}