package edu.itba.paw.jimi.webapp.exceptionmapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Iterator;

@Provider
public class ValidationExceptionMapper extends BusinessExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

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
			String message = "invalid constraint";
			try {
				String messageTemplate = cv.getMessageTemplate();
				messageTemplate = messageTemplate.substring(1, messageTemplate.length() - 1);
				message = messageSource.getMessage(messageTemplate, null, localeResolver.resolveLocale(request));
			} catch (NoSuchMessageException e) {
				message = cv.getMessage();
			} finally {
				jsonObjectBuilderErrors.add(getPropertyName(cv), message);
			}
		}
		JsonArrayBuilder jsonArrayBuilderErrors = Json.createArrayBuilder();
		jsonArrayBuilderErrors.add(jsonObjectBuilderErrors);
		return Json.createObjectBuilder()
				.add("formErrors", jsonArrayBuilderErrors)
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