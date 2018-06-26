package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.form.DishForm;
import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.models.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @RequestMapping(value = {"/create"}, method = { RequestMethod.GET })
    public ModelAndView register(@ModelAttribute("dishCreateForm") final DishForm form) {

        return new ModelAndView("dishes/create2");
    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("dishCreateForm") final DishForm form, final BindingResult errors) {

        if (errors.hasErrors()) { return register(form); }

        final Dish d = dishService.create(form.getName(), form.getPrice());
        final int i = dishService.setStock(d, form.getStock());

        return new ModelAndView("redirect:/admin/dishes");
    }

    @RequestMapping("")
    public ModelAndView list() {
        final ModelAndView mav = new ModelAndView("dishes/list");

        List<Dish> dishes = (List<Dish>) dishService.findAll();

        Collections.sort(dishes, new Comparator<Dish>() {
            public int compare(Dish o1, Dish o2) {
                return (int)(o1.getName().compareTo(o2.getName()));
            }
        });

        mav.addObject("dishes", dishes);
        return mav;
    }

    @RequestMapping(value = "/stock/increase", method = { RequestMethod.POST })
    public ModelAndView increaseStock(@RequestParam(value = "dishid") final long dishid) {
        Dish dish = dishService.findById(dishid);
        dishService.increaseStock(dish);

        return new ModelAndView("redirect:/admin/dishes");
    }

    @RequestMapping(value = "/stock/decrease", method = { RequestMethod.POST })
    public ModelAndView decreaseStock(@RequestParam(value = "dishid") final long dishid) {
        Dish dish = dishService.findById(dishid);
        dishService.decreaseStock(dish);

        return new ModelAndView("redirect:/admin/dishes");
    }

}
