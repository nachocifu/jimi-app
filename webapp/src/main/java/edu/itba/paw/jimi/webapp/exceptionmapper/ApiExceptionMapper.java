package edu.itba.paw.jimi.webapp.exceptionmapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApiExceptionMapper extends Throwable implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		Response response;
		if (exception instanceof WebApplicationException) {
			WebApplicationException webApplicationException = (WebApplicationException) exception;
			response = Response.status(webApplicationException.getResponse().getStatus())
					.entity("{ \"error\": \"" + webApplicationException.getMessage() + "\" }").build();
		} else {
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("{ \"error\": \"Internal error\" }").type("application/json").build();
		}
		return response;
	}
}