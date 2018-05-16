package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.StatsService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;

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
		mav.addObject("freeTablesPercentage", statsService.getFreeTables());
		mav.addObject("stockStatePercentage", statsService.getStockState());
		mav.addObject("lastOrders", orderService.findAll());
		
		return mav;
	}
}
