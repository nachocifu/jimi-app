package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.StatsService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.webapp.dto.form.table.TableAddDishForm;
import edu.itba.paw.jimi.webapp.dto.form.table.TableSetDinersForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping("/admin")
@Controller
public class AdminController {

	@Autowired
	private StatsService statsService;

	@Autowired
	private TableService tableService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private DishService dishService;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping("")
	public ModelAndView index() {
		final ModelAndView mav = new ModelAndView("admin/dashboard2");

		mav.addObject("busyTables", statsService.getBusyTablesUnits());
		mav.addObject("freeTables", statsService.getFreeTablesUnits());
		mav.addObject("payingTables", statsService.getPayingTablesUnits());
		mav.addObject("totalTables", tableService.getTotalTables());
		mav.addObject("freeTablesPercentage", statsService.getFreeTables());
		mav.addObject("stockStatePercentage", statsService.getStockState(50));
		mav.addObject("monthOrderTotals", statsService.getMonthlyOrderTotal());
		mav.addObject("monthlyOrdersCancelled", statsService.getMonthlyOrderCancelled());

		return mav;
	}

	private static final int PAGE_SIZE = 10;

	@RequestMapping("/order_edit/{id}")
	public ModelAndView indexEdit(@PathVariable("id") Integer id,
	                              @ModelAttribute("tableSetDinersForm") final TableSetDinersForm form,
	                              HttpServletResponse response) {

		Order order = orderService.findById(id);


//		if (order == null) {
//			throw new Http404Error(messageSource.getMessage("order.error.not.found.title",
//					null, LocaleContextHolder.getLocale()), messageSource.getMessage("order.error.not.found.body",
//					null, LocaleContextHolder.getLocale()));
//		}

		final ModelAndView mav;
		mav = new ModelAndView("tables/order_edit");

		mav.addObject("order", order);
		mav.addObject("dishes", order.getDishes());
		mav.addObject("diners", order.getDiners());
		mav.addObject("total", order.getTotal());
		return mav;
	}

	@RequestMapping(value = "/order_edit/{tableId}/add_dish", method = {RequestMethod.POST})
	public ModelAndView addDishPost(@PathVariable("tableId") Integer id, @Valid @ModelAttribute("tableAddDishForm") final TableAddDishForm form, final BindingResult errors) {

//		if (errors.hasErrors()) {
//			return addDish(id, form);
//		}

		Order order = orderService.findById(id);
		Dish dish = dishService.findById(form.getDishId());
		orderService.addDishes(order, dish, form.getAmount());

		return new ModelAndView("redirect:/admin/order_edit/" + order.getId());
	}

	@RequestMapping(value = "/order_edit/{orderId}/add_one_dish", method = {RequestMethod.POST})
	public ModelAndView addOneDishPost(@PathVariable("orderId") Integer id, @RequestParam(value = "dishid") final Integer dishid) {

		Order order = orderService.findById(id);
		Dish dish = dishService.findById(dishid);
		orderService.addDish(order, dish);

		return new ModelAndView("redirect:/admin/order_edit/" + order.getId());
	}

	@RequestMapping(value = "/order_edit/{orderId}/remove_one_dish", method = {RequestMethod.POST})
	public ModelAndView removeOneDishPost(@PathVariable("orderId") Integer id, @RequestParam(value = "dishid") final Integer dishid) {

		Order order = orderService.findById(id);
		Dish dish = dishService.findById(dishid);
		orderService.removeOneUndoneDish(order, dish);

		return new ModelAndView("redirect:/admin/order_edit/" + order.getId());
	}

	@RequestMapping(value = "/order_edit/{orderId}/remove_all_dish", method = {RequestMethod.POST})
	public ModelAndView removeAllDishPost(@PathVariable("orderId") Integer id, @RequestParam(value = "dishid") final Integer dishid) {

		Order order = orderService.findById(id);
		Dish dish = dishService.findById(dishid);
		orderService.removeAllUndoneDish(order, dish);

		return new ModelAndView("redirect:/admin/order_edit/" + order.getId());
	}

	@RequestMapping(value = "/order_edit/{orderId}/set_diners", method = {RequestMethod.POST})
	public ModelAndView setDinersPost(@PathVariable("orderId") Integer id,
	                                  @Valid @ModelAttribute("tableSetDinersForm") final TableSetDinersForm form,
	                                  final BindingResult errors,
	                                  HttpServletResponse response) {

		if (errors.hasErrors()) {
			return indexEdit(id, form, response);
		}

		Order order = orderService.findById(id);
		orderService.setDiners(order, form.getDiners());

		return new ModelAndView("redirect:/admin/order_edit/" + order.getId());
	}

	@RequestMapping(value = "/order_edit/{orderId}/add_diner", method = {RequestMethod.POST})
	public ModelAndView addDinerPost(@PathVariable("orderId") Integer id) {
		Order order = orderService.findById(id);
		orderService.setDiners(order, order.getDiners() + 1);

		return new ModelAndView("redirect:/admin/order_edit/" + order.getId());
	}

	@RequestMapping(value = "/order_edit/{orderId}/subtract_diner", method = {RequestMethod.POST})
	public ModelAndView subtractDinerPost(@PathVariable("orderId") Integer id) {
		Order order = orderService.findById(id);
		orderService.setDiners(order, order.getDiners() - 1);

		return new ModelAndView("redirect:/admin/order_edit/" + order.getId());
	}

}
