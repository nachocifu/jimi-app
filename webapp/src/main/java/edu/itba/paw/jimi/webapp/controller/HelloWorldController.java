package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.interfaces.UserService;
import edu.itba.paw.jimi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/users")
    public ModelAndView index(@RequestParam(value = "userid", required = true) final long id) {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("user", us.findById(id));
        return mav;
    }

    @RequestMapping("/users/create")
    public ModelAndView create(@RequestParam(value = "name", required = true) final String username) {
        final User u = us.create(username);
        return new ModelAndView("redirect:/users?userid=" + u.getId());
    }
}