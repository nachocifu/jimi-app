package edu.itba.paw.jimi.webapp.controller;


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
	
	@RequestMapping("")
	public ModelAndView index() {
		final ModelAndView mav = new ModelAndView("admin/index");
		
		mav.addObject("busyTables", statsService.getBusyTablesUnits());
		mav.addObject("totalTables", tableService.findAll().size());
		mav.addObject("dinersToday", statsService.getDinersToday());
		mav.addObject("dishesSold", statsService.getDishesSold());
		mav.addObject("freeTablesPercentage", statsService.getFreeTables());
		mav.addObject("stockStatePercentage", statsService.getStockState());
		
		return mav;
	}
}
