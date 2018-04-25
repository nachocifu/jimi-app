package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.form.UserForm;
import edu.itba.paw.jimi.interfaces.UserService;
import edu.itba.paw.jimi.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class HelloWorldController {

    @Autowired
    private UserService us;

    @RequestMapping("/hello/{greeting}")
    public ModelAndView helloWorld(@PathVariable("greeting") String greeting) {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("greeting", greeting);
        return mav;
    }

    @RequestMapping("")
    public ModelAndView list() {
        final ModelAndView mav = new ModelAndView("users/list");
        //por ahora devolvemos todo, el dia de manana se busca con queryparams
        mav.addObject("users", us.findAll());
        return mav;
    }

    @RequestMapping("/{id}")
    public ModelAndView index(@PathVariable("id") Integer id) {
        final ModelAndView mav = new ModelAndView("users/index");
        mav.addObject("user", us.findById(id));
        return mav;
    }

    @RequestMapping("/register")
    public ModelAndView register(@ModelAttribute("registerForm") final UserForm form) {
        return new ModelAndView("users/register");
    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {

        if (errors.hasErrors()) { return register(form); }

        final User u = us.create(form.getUsername(), form.getPassword());

        return new ModelAndView("redirect:/users/hello/id=" + u.getId());
    }




}