package edu.itba.paw.jimi.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/error")
public class ErrorsController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/400")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView error400() {
        return getErrorView("You sure?", "I that the right way of asking?" );
    }

    @RequestMapping("/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView error401() {
        return getErrorView("Identify yourself!", "Who are you? This is private property");
    }

    @RequestMapping("/403")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView error403() {

        return getErrorView("This is awkard!", "Why are accesing something you know you cant?");
    }

    @RequestMapping("/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView error404() {

        return getErrorView("This is awkard!", "Seems like we cant find what your wanted. ¯\\_(ツ)_/¯");
    }

    @RequestMapping("/500")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView error500() {
        return getErrorView("Something is broken :(", "Seems like someone didnt check their work. We wil fix it for you the first chance we have.\n Our bad! ¯\\_(ツ)_/¯");
    }

    private ModelAndView getErrorView(String title, String message) {
        return (new ModelAndView("error"))
                .addObject("body", message)
                .addObject("title", title);
    }

}