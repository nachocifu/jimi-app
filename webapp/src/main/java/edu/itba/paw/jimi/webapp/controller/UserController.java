package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.form.UserForm;
import edu.itba.paw.jimi.interfaces.services.UserService;
import edu.itba.paw.jimi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService us;
	
	@RequestMapping("/register")
	public ModelAndView register(@ModelAttribute("registerForm") final UserForm form) {
		return new ModelAndView("users/register");
	}
	
	
	@RequestMapping("/{userId}")
	public ModelAndView index(@RequestParam(value = "userId", required = true) final int id) {
		final ModelAndView mav = new ModelAndView("users/index");
		mav.addObject("user", us.findById(id));
		return mav;
	}
	
	@RequestMapping(value = "/create", method = {RequestMethod.POST})
	public ModelAndView create(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
		
		if (errors.hasErrors()) {
			return register(form);
		}
		
		final User u = us.create(form.getUsername(), form.getPassword());
		
		return new ModelAndView("redirect:/users/user?userId=" + u.getId());
	}
	
	@RequestMapping("")
	public ModelAndView list() {
		final ModelAndView mav = new ModelAndView("users/list");
		// TODO , el dia de manana se busca con queryparams
		mav.addObject("users", us.findAll());
		return mav;
	}
	
	
}