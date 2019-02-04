package edu.itba.paw.jimi.webapp.utils;

import edu.itba.paw.jimi.interfaces.utils.MessageByLocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageSourceHelper implements MessageByLocaleService {

	@Autowired
	private MessageSource messageSource;

	@Override
	public String getMessage(String code, Locale locale) {
		return messageSource.getMessage(code, null, locale);
	}
}
