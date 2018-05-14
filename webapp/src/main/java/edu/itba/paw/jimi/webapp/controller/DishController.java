package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.form.DishForm;
import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.models.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping("/{dishid}")
    public ModelAndView GET(@PathVariable("dishid") long dishid) {
        final ModelAndView mav = new ModelAndView("dishes/dish_info");
        final Dish dish = dishService.findById(dishid);

        if(dish == null) {
            final ModelAndView mavError = new ModelAndView("simple_error");
            mavError.addObject("error_message", "No dish found. :(");

            return mavError;
        }

        mav.addObject("name", dish.getName());
        mav.addObject("price", dish.getPrice());
        mav.addObject("stock", dish.getStock());
        return mav;
    }

    @RequestMapping(value = {"/create"}, method = { RequestMethod.GET })
    public ModelAndView register(@ModelAttribute("dishCreateForm") final DishForm form) {

        ModelAndView mav = new ModelAndView("dishes/create_dish");

        return mav;
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
        final ModelAndView mav = new ModelAndView("dishes/dishes");

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
