package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.form.UserForm;
import edu.itba.paw.jimi.interfaces.services.UserService;
import edu.itba.paw.jimi.models.User;
import edu.itba.paw.jimi.webapp.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/users")
public class UserController {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping("/register")
	public ModelAndView register(@ModelAttribute("registerForm") final UserForm form) {
		return new ModelAndView("users/create");
    }
	
	@RequestMapping(value = "/create", method = {RequestMethod.POST})
	public ModelAndView create(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
		
		UserValidator userValidator = new UserValidator();
		userValidator.validate(form, errors);
		
		if (errors.hasErrors()) {
			return register(form);
		}
		
		us.create(form.getUsername(), passwordEncoder.encode(form.getPassword()));
		
		return new ModelAndView("redirect:/admin/users");
	}
	
	@RequestMapping("")
	public ModelAndView list() {
		final ModelAndView mav = new ModelAndView("users/list");
		mav.addObject("users", us.findAll());
		return mav;
	}
	
	
}