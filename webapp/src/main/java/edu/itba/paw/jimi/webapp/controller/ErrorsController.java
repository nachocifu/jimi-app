package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.interfaces.exceptions.Http400Error;
import edu.itba.paw.jimi.interfaces.exceptions.Http404Error;
import edu.itba.paw.jimi.interfaces.exceptions.HttpError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/error")
@ControllerAdvice
public class ErrorsController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/400")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView error400() {
        return getErrorView(
                messageSource.getMessage("error.400.title",
                        null,
                        LocaleContextHolder.getLocale())
                , messageSource.getMessage("error.400.body",
                        null,
                        LocaleContextHolder.getLocale()));
    }

    @RequestMapping("/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView error401() {
        return getErrorView(
                messageSource.getMessage("user.error.not.found.body",
                        null,
                        LocaleContextHolder.getLocale())
                , messageSource.getMessage("user.error.not.found.title",
                        null,
                        LocaleContextHolder.getLocale()));
    }

    @RequestMapping("/403")
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView error403() {

        return getErrorView(
                messageSource.getMessage("error.403.title",
                        null,
                        LocaleContextHolder.getLocale())
                , messageSource.getMessage("error.403.body",
                        null,
                        LocaleContextHolder.getLocale()));
    }

    @RequestMapping("/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView error404() {

        return getErrorView(
                messageSource.getMessage("error.404.title",
                        null,
                        LocaleContextHolder.getLocale())
                , messageSource.getMessage("error.404.body",
                        null,
                        LocaleContextHolder.getLocale()));
    }

    @RequestMapping("/500")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView error500(Object e) {
        return getErrorView(
                messageSource.getMessage("error.500.title",
                        null,
                        LocaleContextHolder.getLocale())
                , messageSource.getMessage("error.500.body",
                        null,
                        LocaleContextHolder.getLocale()));
    }

    private ModelAndView getErrorView(String title, String message) {
        return (new ModelAndView("error"))
                .addObject("body", message)
                .addObject("title", title);
    }

    @ExceptionHandler({Http404Error.class, Http400Error.class})
    public ModelAndView handleErrorException(HttpError e, HttpServletResponse response) {
        response.setStatus(e.getStatus());
        return (new ModelAndView("error"))
                .addObject("body", e.getBody())
                .addObject("title", e.getTitle());
    }

}