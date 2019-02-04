package edu.itba.paw.jimi.webapp.exceptionmapper;

import edu.itba.paw.jimi.interfaces.utils.MessageByLocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

@Component
public abstract class BusinessExceptionMapper {

	@Autowired
	protected MessageByLocaleService messageByLocaleServiceImpl;

	@Autowired
	protected LocaleResolver localeResolver;

	@Context
	protected HttpServletRequest request;

}
