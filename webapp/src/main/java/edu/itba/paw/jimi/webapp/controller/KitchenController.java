package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.interfaces.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/kitchen")
@Controller
public class KitchenController {

    @Autowired
    OrderService orderService;

    @RequestMapping("")
    public ModelAndView index() {
        final ModelAndView mav = new ModelAndView("/kitchen/kitchen");

        //mav.addObject("lastOrders", orderService.findAll(null));

        return mav;
    }
}
