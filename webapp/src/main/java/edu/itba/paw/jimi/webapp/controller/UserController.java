package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.interfaces.exceptions.Http400Error;
import edu.itba.paw.jimi.interfaces.exceptions.Http404Error;
import edu.itba.paw.jimi.interfaces.services.UserService;
import edu.itba.paw.jimi.models.User;
import edu.itba.paw.jimi.webapp.dto.form.UserForm;
import edu.itba.paw.jimi.webapp.validators.UserValidator;
import edu.itba.paw.jimi.models.utils.QueryParams;
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
import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserController {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MessageSource messageSource;
	
	private static final int PAGE_SIZE = 10;
	
	@RequestMapping("/register")
	public ModelAndView register(@ModelAttribute("registerForm") final UserForm form) {
		return new ModelAndView("users/create");
	}

	@RequestMapping("/{userId}")
	public ModelAndView index(@PathVariable("userId") final int id, HttpServletResponse response) {
		final ModelAndView mav = new ModelAndView("users/index");
		User user = us.findById(id);
		if (user != null) {
			mav.addObject("user", user);
			return mav;
        } else
            throw new Http404Error(messageSource.getMessage("user.error.not.found.title",
                    null, LocaleContextHolder.getLocale()),
                    messageSource.getMessage("user.error.not.found.body",
                            null, LocaleContextHolder.getLocale()));

	}
	
	@RequestMapping(value = "/create", method = {RequestMethod.POST})
	public ModelAndView create(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
		
		UserValidator userValidator = new UserValidator();
		userValidator.validate(form, errors);
		
		if (errors.hasErrors()) {
			return register(form);
		}

		if (us.findByUsername(form.getUsername()) != null)
			throw new Http400Error(
					messageSource.getMessage("user.error.repeated.title",
							null, LocaleContextHolder.getLocale()),
					messageSource.getMessage("user.error.repeated.body",
							null, LocaleContextHolder.getLocale())
			);
		
		us.create(form.getUsername(), passwordEncoder.encode(form.getPassword()));
		
		return new ModelAndView("redirect:/admin/users");
	}
	
	@RequestMapping("")
	public ModelAndView list() {
		final ModelAndView mav = new ModelAndView("users/list");
		
		
		QueryParams qp = new QueryParams(0, PAGE_SIZE, us.getTotalUsers());
		List<User> users = (List<User>) us.findAll(qp);

		mav.addObject("users", users);
		mav.addObject("qp", qp);
		return mav;
	}
	
	@RequestMapping("/page/{page}")
	public ModelAndView listPage(@PathVariable("page") Integer page) {
		final ModelAndView mav = new ModelAndView("users/list");
		
		QueryParams qp = new QueryParams((page - 1) * PAGE_SIZE, PAGE_SIZE, us.getTotalUsers());
		List<User> users = (List<User>) us.findAll(qp);
		
		mav.addObject("users", users);
		mav.addObject("qp", qp);
		return mav;
	}
	
	
}