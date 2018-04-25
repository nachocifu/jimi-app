package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.interfaces.DishService;
import edu.itba.paw.jimi.models.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DishController {

    @Autowired
    private DishService dishService;

    @RequestMapping("/dish/{dishid}")
    public ModelAndView GET(@PathVariable("dishid") long dishid) {
        final ModelAndView mav = new ModelAndView("dish_info");
        final Dish dish = dishService.findById(dishid);
        mav.addObject("name", dish.getName());
        mav.addObject("price", dish.getPrice());
        return mav;
    }

    @RequestMapping("/dish/create")
    public ModelAndView create(@RequestParam(value = "name", required = true) final String name,
                               @RequestParam(value = "price", required = true) final String priceString) {

        float price = Float.parseFloat(priceString); //TODO: Que pasa cuando esto rompe?

        final Dish dish = dishService.create(name, price);
        return new ModelAndView("redirect:/dish/" + dish.getId());
    }
}
