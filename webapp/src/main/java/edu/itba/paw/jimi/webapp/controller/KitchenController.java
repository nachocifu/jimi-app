package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import edu.itba.paw.jimi.models.Utilities.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
		
		QueryParams qp = new QueryParams("openedat", false);
		Collection<Order> orders = orderService.getActiveOrders(qp);
		
		Map<Dish, Integer> totalDishes = new HashMap<>();
		for (Order o : orders) {
			for (Dish d : o.getUnDoneDishes().keySet()) {
				if (totalDishes.containsKey(d)) {
					totalDishes.put(d, totalDishes.get(d) + o.getUnDoneDishes().get(d).getAmount());
				} else {
					totalDishes.put(d, o.getUnDoneDishes().get(d).getAmount());
				}
			}
		}
		
		Collection<Order> urgentOrders = orderService.get30MinutesWaitOrders();
		Collection<Table> busyTables = tableService.findTablesWithStatus(TableStatus.BUSY);
		Collection<Table> urgentTables = busyTables
				.parallelStream()
				.filter(t -> urgentOrders.contains(t.getOrder()))
				.collect(Collectors.toList());
		busyTables.removeAll(urgentTables);
		
		mav.addObject("tables", busyTables);
		mav.addObject("orders", orders);
		mav.addObject("urgentTables", urgentTables);
		mav.addObject("totalDishes", totalDishes);
		mav.addObject("qp", qp);
		
		return mav;
	}
	
	@RequestMapping(value = "done", method = RequestMethod.POST)
	public ModelAndView done(@RequestParam(value = "orderid") long orderid, @RequestParam(value = "dishid") long dishid) {
		orderService.setDishAsDone(orderService.findById(orderid), dishService.findById(dishid));
		return new ModelAndView("redirect:/kitchen");
	}
}
