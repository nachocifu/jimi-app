package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

	@Autowired
	private UserService us;
	
	@RequestMapping("/hello/{greeting}")
	public ModelAndView helloWorld(@PathVariable("greeting") String greeting) {
		final ModelAndView mav = new ModelAndView("index");
		mav.addObject("greeting", greeting);
		return mav; 
	}
}