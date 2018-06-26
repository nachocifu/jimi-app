package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.form.DishForm;
import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Utilities.QueryParams;
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

    @RequestMapping(value = {"/create"}, method = { RequestMethod.GET })
    public ModelAndView register(@ModelAttribute("dishCreateForm") final DishForm form) {

        return new ModelAndView("dishes/create");
    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("dishCreateForm") final DishForm form, final BindingResult errors) {

        if (errors.hasErrors()) { return register(form); }

        final Dish d = dishService.create(form.getName(), form.getPrice());
        final int i = dishService.setStock(d, form.getStock());

        return new ModelAndView("redirect:/admin/dishes");
    }

    private static final int PAGE_SIZE = 3;

    @RequestMapping("")
    public ModelAndView list() {
        final ModelAndView mav = new ModelAndView("dishes/list");

        QueryParams qp = new QueryParams(0, PAGE_SIZE, dishService.getTotalDishes());
        List<Dish> dishes = (List<Dish>) dishService.findAll(qp);

        mav.addObject("dishes", dishes);
        mav.addObject("qp", qp);
        return mav;
    }

    @RequestMapping("/page/{page}")
    public ModelAndView listPage(@PathVariable("page") Integer page) {
        final ModelAndView mav = new ModelAndView("dishes/list");

        QueryParams qp = new QueryParams((page - 1) * PAGE_SIZE, PAGE_SIZE, dishService.getTotalDishes());
        List<Dish> dishes = (List<Dish>) dishService.findAll(qp);

        mav.addObject("dishes", dishes);
        mav.addObject("qp", qp);
        return mav;
    }

    @RequestMapping(value = "/stock/increase", method = { RequestMethod.POST })
    public ModelAndView increaseStock(@RequestParam(value = "dishid") final long dishid, @RequestParam(value = "page", defaultValue = "1") final int page) {
        Dish dish = dishService.findById(dishid);
        dishService.increaseStock(dish);

        return new ModelAndView("redirect:/admin/dishes/page/" + page);
    }

    @RequestMapping(value = "/stock/decrease", method = { RequestMethod.POST })
    public ModelAndView decreaseStock(@RequestParam(value = "dishid") final long dishid, @RequestParam(value = "page", defaultValue = "1") final int page) {
        Dish dish = dishService.findById(dishid);
        dishService.decreaseStock(dish);

        return new ModelAndView("redirect:/admin/dishes/page/" + page);
    }

}
