package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Map;

@Controller
@RequestMapping("/kitchen")
public class KitchenController {
	
	@Autowired
	@Qualifier(value = "userOrderService")
	private OrderService orderService;
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private TableService tableService;
	
	@RequestMapping("")
	public ModelAndView view() {
		final ModelAndView mav = new ModelAndView("kitchen/view");
		
		Map totalDishes = orderService.getAllUndoneDishesFromAllActiveOrders();
		
		Collection<Table> busyTables = tableService.findTablesWithStatus(TableStatus.BUSY);
		Collection<Table> urgentTables = tableService.getUrgentTables();
		
		mav.addObject("tables", busyTables);
		mav.addObject("urgentTables", urgentTables);
		mav.addObject("totalDishes", totalDishes);
		
		return mav;
	}
	
	@RequestMapping(value = "done", method = RequestMethod.POST)
	public ModelAndView done(@RequestParam(value = "orderid") long orderid, @RequestParam(value = "dishid") long dishid) {
		orderService.setDishAsDone(orderService.findById(orderid), dishService.findById(dishid));
		return new ModelAndView("redirect:/kitchen");
	}
}
