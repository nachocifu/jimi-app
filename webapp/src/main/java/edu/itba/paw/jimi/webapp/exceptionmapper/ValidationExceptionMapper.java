package edu.itba.paw.jimi.webapp.exceptionmapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Iterator;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationExceptionMapper.class);
	
	@Override
	public Response toResponse(ConstraintViolationException exception) {
		LOGGER.warn("Exception: {}", (Object[]) exception.getStackTrace());
		return Response.status(Response.Status.BAD_REQUEST)
				.entity(prepareMessage(exception))
				.type("text/plain")
				.build();
	}
	
	private String prepareMessage(ConstraintViolationException exception) {
		JsonObjectBuilder jsonObjectBuilderErrors = Json.createObjectBuilder();
		for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
			jsonObjectBuilderErrors.add(getPropertyName(cv), cv.getMessage());
		}
		return Json.createArrayBuilder()
				.add(jsonObjectBuilderErrors)
				.build()
				.toString();
	}
	
	private String getPropertyName(final ConstraintViolation<?> cv) {
		final Iterator<Path.Node> iterator = cv.getPropertyPath().iterator();
		Path.Node next = null;
		while (iterator.hasNext())
			next = iterator.next();
		return next != null ? next.getName() : null;
	}
}