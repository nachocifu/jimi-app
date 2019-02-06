package edu.itba.paw.jimi.webapp.exceptionmapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

public abstract class BusinessExceptionMapper {

	@Autowired
	protected MessageSource messageSource;

	@Autowired
	protected LocaleResolver localeResolver;

	@Context
	protected HttpServletRequest request;

}
