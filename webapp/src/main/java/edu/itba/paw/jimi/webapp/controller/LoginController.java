package edu.itba.paw.jimi.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
	@RequestMapping("/login")
	public ModelAndView login() {
        return new ModelAndView("users/login");
	}

	@RequestMapping("/")
	public ModelAndView onLoginSuccess(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_ADMIN"))
			return new ModelAndView("redirect:/admin");
		else
			return new ModelAndView("redirect:/tables");
	}
	
}
