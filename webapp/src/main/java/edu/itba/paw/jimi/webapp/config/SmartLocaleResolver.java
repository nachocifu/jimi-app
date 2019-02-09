package edu.itba.paw.jimi.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class SmartLocaleResolver extends AcceptHeaderLocaleResolver {

	@Autowired
	private Environment environment;

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		if (StringUtils.isEmpty(request.getHeader("Accept-Language"))) {
			return Locale.getDefault();
		}
		String acceptedLocales = environment.getProperty("locales");
		List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));
		List<Locale> locales = Arrays.stream(acceptedLocales.split(","))
				.map(Locale::new)
				.collect(Collectors.toList());
		return Locale.lookup(list, locales);
	}
}