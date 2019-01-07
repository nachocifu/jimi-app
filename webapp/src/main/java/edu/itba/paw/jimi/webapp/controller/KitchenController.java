package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.Utilities.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/kitchen")
public class KitchenController {
    @Autowired
    @Qualifier(value = "userOrderService")
    private OrderService orderService;

    @Autowired
    private DishService dishService;

    @RequestMapping("")
    public ModelAndView view() {
        final ModelAndView mav = new ModelAndView("kitchen/view");

        QueryParams qp = new QueryParams("openedat", false);
        Collection<Order> orders = orderService.getActiveOrders(qp);

        Date urgentThreshold = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(urgentThreshold);
        cal.add(Calendar.MINUTE, -30);
        urgentThreshold = cal.getTime();

        Collection<Order> urgentOrders = new LinkedList<>();
        for (Order o : orders) {
            if (o.getOpenedAt().before(urgentThreshold))
                urgentOrders.add(o);
        }

        Map<Dish, Integer> totalDishes = new HashMap<>();
        for (Order o : orders) {
            for (Dish d : o.getUnDoneDishes().keySet()){
                if (totalDishes.containsKey(d)){
                    totalDishes.put(d, totalDishes.get(d) + o.getUnDoneDishes().get(d));
                }else{
                    totalDishes.put(d, o.getUnDoneDishes().get(d));
                }
            }
        }

        mav.addObject("orders", orders);
        mav.addObject("urgentOrders", urgentOrders);
        mav.addObject("totalDishes", totalDishes);
        mav.addObject("qp", qp);

        return mav;
    }

    @RequestMapping(value = "done", method = RequestMethod.POST)
    public ModelAndView done(@RequestParam(value = "orderid") long orderid, @RequestParam(value = "dishid") long dishid){
        orderService.setDishAsDone(orderService.findById(orderid), dishService.findById(dishid));
        return new ModelAndView("redirect:/kitchen");
    }
}
