package edu.itba.paw.jimi.webapp.controller;


import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.StatsService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @Autowired
    StatsService statsService;

    @Autowired
    TableService tableService;

    @Autowired
    OrderService orderService;

    @RequestMapping("")
    public ModelAndView index() {
        final ModelAndView mav = new ModelAndView("admin/dashboard");

        mav.addObject("busyTables", statsService.getBusyTablesUnits());
        mav.addObject("totalTables", tableService.findAll().size());
        mav.addObject("dinersToday", statsService.getDinersToday());
        mav.addObject("dishesSold", statsService.getDishesSold());
        mav.addObject("freeTablesPercentage", statsService.getFreeTables());
        mav.addObject("stockStatePercentage", statsService.getStockState());
        mav.addObject("latest10Orders", orderService.findAll());

        return mav;
    }
}
