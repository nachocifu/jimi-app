package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.form.TableForm;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/tables")
public class TableController {

    @Autowired
    private TableService ts;


    @RequestMapping("")
    public ModelAndView list() {
        final ModelAndView mav = new ModelAndView("tables/list");
        // TODO , el dia de manana se busca con queryparams
        mav.addObject("tables", ts.findAll());
        return mav;
    }

    @RequestMapping("/{id}")
    public ModelAndView index(@PathVariable("id") Integer id) {
        final ModelAndView mav = new ModelAndView("tables/index");
        mav.addObject("table", ts.findById(id));
        return mav;
    }

    @RequestMapping("/register")
    public ModelAndView register(@ModelAttribute("registerForm") final TableForm form) {
        return new ModelAndView("tables/register");
    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("registerForm") final TableForm form, final BindingResult errors) {

        if (errors.hasErrors()) { return register(form); }

        final Table tb = ts.create(form.getName());

        return new ModelAndView("redirect:/tables/" + tb.getId());
    }




}