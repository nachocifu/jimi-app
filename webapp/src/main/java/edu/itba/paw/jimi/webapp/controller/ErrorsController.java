package edu.itba.paw.jimi.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class ErrorsController {

	
	@RequestMapping("/400")
	public ModelAndView error400() {
		return new ModelAndView("errors/400");
	}

	@RequestMapping("/404")
	public ModelAndView error404() {
		return new ModelAndView("errors/404");
	}

	@RequestMapping("/500")
	public ModelAndView error500() {
		return new ModelAndView("errors/500");
	}

}