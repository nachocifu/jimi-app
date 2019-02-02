package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.form.TableAddDishForm;
import edu.itba.paw.jimi.form.TableForm;
import edu.itba.paw.jimi.form.TableSetDinersForm;
import edu.itba.paw.jimi.interfaces.exceptions.AddingDiscontinuedDishException;
import edu.itba.paw.jimi.interfaces.exceptions.Http400Error;
import edu.itba.paw.jimi.interfaces.exceptions.Http404Error;
import edu.itba.paw.jimi.interfaces.exceptions.Http409Error;
import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import edu.itba.paw.jimi.models.utils.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/tables")
public class TableController {

	@Autowired
	private TableService tableService;

	@Autowired
	@Qualifier(value = "adminOrderService")
	private OrderService orderService;

	@Autowired
	private DishService dishService;

	@Autowired
	private MessageSource messageSource;

	private static final int PAGE_SIZE = 10;

	@RequestMapping("")
	public ModelAndView list() {
		final ModelAndView mav = new ModelAndView("tables/list");
		QueryParams qp = new QueryParams(0, PAGE_SIZE, tableService.getTotalTables());
		mav.addObject("qp", qp);
		mav.addObject("tables", tableService.findAll(qp));
		return mav;
	}

	@RequestMapping("/page/{page}")
	public ModelAndView listPage(@PathVariable("page") Integer page) {
		final ModelAndView mav = new ModelAndView("tables/list");

		QueryParams qp = new QueryParams((page - 1) * PAGE_SIZE, PAGE_SIZE, tableService.getTotalTables());

		mav.addObject("tables", tableService.findAll(qp));
		mav.addObject("qp", qp);
		return mav;
	}

	@RequestMapping("/{id}")
	public ModelAndView index(@PathVariable("id") Integer id,
	                          @ModelAttribute("tableSetDinersForm") final TableSetDinersForm form,
	                          HttpServletResponse response) {
		Table table = tableService.findById(id);

		if (table == null) {
			throw new Http404Error(messageSource.getMessage("table.error.not.found.title",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("table.error.not.found.body",
					null, LocaleContextHolder.getLocale()));
		}

		final ModelAndView mav;
		if (table.getStatus().equals(TableStatus.PAYING))
			mav = new ModelAndView("tables/checkout");
		else
			mav = new ModelAndView("tables/index");

		mav.addObject("table", table);
		mav.addObject("dishes", table.getOrder().getDishes());
		mav.addObject("diners", table.getOrder().getDiners());
		mav.addObject("total", table.getOrder().getTotal());
		return mav;
	}

	@RequestMapping(value = "/{tableId}/status", method = {RequestMethod.POST})
	public ModelAndView statusChange(@PathVariable("tableId") Integer id, @RequestParam(value = "status") final Integer statusId) {

		Table table = tableService.findById(id);
		if (table == null) {
			throw new Http404Error(messageSource.getMessage("table.error.not.found.title",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("table.error.not.found.body",
					null, LocaleContextHolder.getLocale()));
		}
		tableService.changeStatus(table, TableStatus.getTableStatus(statusId));

		if (table.getStatus().equals(TableStatus.FREE))
			return new ModelAndView("redirect:/tables");
		else
			return new ModelAndView("redirect:/tables/" + id);
	}

	@RequestMapping("/register")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView register(@ModelAttribute("registerForm") final TableForm form) {
		return new ModelAndView("tables/create");
	}

	@RequestMapping(value = "/create", method = {RequestMethod.POST})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView create(@Valid @ModelAttribute("registerForm") final TableForm form, final BindingResult errors) {

		if (tableService.tableNameExists(form.getName())) {
			errors.rejectValue("name", "table.error.existing.name.body");
		}

		if (errors.hasErrors()) {
			return register(form);
		}

		final Table tb = tableService.create(form.getName());

		return new ModelAndView("redirect:/tables/" + tb.getId());
	}

	@RequestMapping(value = "/delete/{tableid}", method = {RequestMethod.POST})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView delete(@Valid @ModelAttribute("registerForm") final TableForm form, final BindingResult errors, @PathVariable("tableid") long tableid) {
		Table table = tableService.findById(tableid);
		if (table == null) {
			throw new Http404Error(messageSource.getMessage("table.error.not.found.title",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("table.error.not.found.body",
					null, LocaleContextHolder.getLocale()));
		}
		if (table.getStatus() != TableStatus.FREE) {
			throw new Http400Error(messageSource.getMessage("table.error.not.free.title",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("table.error.not.free.body",
					null, LocaleContextHolder.getLocale()));
		}
		tableService.delete(tableid);
		return new ModelAndView("redirect:/tables");
	}

	@RequestMapping(value = {"/edit/{tableid}"}, method = {RequestMethod.GET})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView update(@ModelAttribute("registerForm") final TableForm form, @PathVariable("tableid") long tableid) {
		Table table = tableService.findById(tableid);
		if (table == null) {
			throw new Http404Error(messageSource.getMessage("table.error.not.found.title",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("table.error.not.found.body",
					null, LocaleContextHolder.getLocale()));
		}

		form.setName(table.getName());

		ModelAndView mv = new ModelAndView("tables/edit");
		mv.addObject("table", table);

		return mv;
	}

	@RequestMapping(value = "/edit/{tableid}", method = {RequestMethod.POST})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView update(@Valid @ModelAttribute("registerForm") final TableForm form, final BindingResult errors, @PathVariable("tableid") long tableid) {

		if (tableService.tableNameExists(form.getName())) {
			errors.rejectValue("name", "table.error.existing.name.body");
		}

		if (errors.hasErrors()) {
			return update(form, tableid);
		}

		final Table table = tableService.findById(tableid);

		tableService.setName(table, form.getName());

		return new ModelAndView("redirect:/tables");
	}

	@RequestMapping(value = "/{tableId}/add_dish", method = {RequestMethod.GET})
	public ModelAndView addDish(@PathVariable("tableId") Integer id,
	                            @ModelAttribute("tableAddDishForm") final TableAddDishForm form) {
		Table table = tableService.findById(id);
		if (table == null) {
			throw new Http400Error(messageSource.getMessage("table.error.not.found.title",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("table.error.not.found.body",
					null, LocaleContextHolder.getLocale()));
		}
		ModelAndView mav = new ModelAndView("tables/add_dish");
		mav.addObject("table", table);
		mav.addObject("dishes", dishService.findAllAvailable());

		return mav;
	}

	@RequestMapping(value = "/{tableId}/add_dish", method = {RequestMethod.POST})
	public ModelAndView addDishPost(@PathVariable("tableId") Integer id,
	                                @Valid @ModelAttribute("tableAddDishForm") final TableAddDishForm form, final BindingResult errors) {

		if (errors.hasErrors()) {
			return addDish(id, form);
		}

		Table table = tableService.findById(id);
		if (table == null) {
			throw new Http400Error(messageSource.getMessage("table.error.not.found.title",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("table.error.not.found.body",
					null, LocaleContextHolder.getLocale()));
		}
		Dish dish = dishService.findById(form.getDishid());
		orderService.addDishes(table.getOrder(), dish, form.getAmount());

		return new ModelAndView("redirect:/tables/" + table.getId());
	}

	@RequestMapping(value = "/{tableId}/add_one_dish", method = {RequestMethod.POST})
	public ModelAndView addOneDishPost(@PathVariable("tableId") Integer id,
	                                   @RequestParam(value = "dishid") final Integer dishid) {

		Table table = tableService.findById(id);
		if (table == null) {
			throw new Http400Error(messageSource.getMessage("table.error.not.found.title",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("table.error.not.found.body",
					null, LocaleContextHolder.getLocale()));
		}
		Dish dish = dishService.findById(dishid);
		try {
			orderService.addDish(table.getOrder(), dish);
		} catch (AddingDiscontinuedDishException e) {
			throw new Http409Error(messageSource.getMessage("dish.discontinued",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("dish.add.discontinued.body",
					null, LocaleContextHolder.getLocale()));
		}

		return new ModelAndView("redirect:/tables/" + table.getId());
	}

	@RequestMapping(value = "/{tableId}/remove_one_dish", method = {RequestMethod.POST})
	public ModelAndView removeOneDishPost(@PathVariable("tableId") Integer id,
	                                      @RequestParam(value = "dishid") final Integer dishid) {

		Table table = tableService.findById(id);
		if (table == null) {
			throw new Http400Error(messageSource.getMessage("table.error.not.found.title",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("table.error.not.found.body",
					null, LocaleContextHolder.getLocale()));
		}
		Dish dish = dishService.findById(dishid);
		orderService.removeOneDish(table.getOrder(), dish);

		return new ModelAndView("redirect:/tables/" + table.getId());
	}

	@RequestMapping(value = "/{tableId}/remove_all_dish", method = {RequestMethod.POST})
	public ModelAndView removeAllDishPost(@PathVariable("tableId") Integer id,
	                                      @RequestParam(value = "dishid") final Integer dishid) {

		Table table = tableService.findById(id);
		if (table == null) {
			throw new Http400Error(messageSource.getMessage("table.error.not.found.title",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("table.error.not.found.body",
					null, LocaleContextHolder.getLocale()));
		}
		Dish dish = dishService.findById(dishid);
		orderService.removeAllDish(table.getOrder(), dish);

		return new ModelAndView("redirect:/tables/" + table.getId());
	}

	@RequestMapping(value = "/{tableId}/set_diners", method = {RequestMethod.POST})
	public ModelAndView setDinersPost(@PathVariable("tableId") Integer id,
	                                  @Valid @ModelAttribute("tableSetDinersForm") final TableSetDinersForm form,
	                                  final BindingResult errors,
	                                  HttpServletResponse response) {

		if (errors.hasErrors()) {
			return index(id, form, response);
		}

		Table table = tableService.findById(id);
		if (table == null) {
			throw new Http400Error(messageSource.getMessage("table.error.not.found.title",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("table.error.not.found.body",
					null, LocaleContextHolder.getLocale()));
		}
		orderService.setDiners(table.getOrder(), form.getDiners());

		return new ModelAndView("redirect:/tables/" + table.getId());
	}

	@RequestMapping(value = "/{tableId}/add_diner", method = {RequestMethod.POST})
	public ModelAndView addDinerPost(@PathVariable("tableId") Integer id) {
		Table table = tableService.findById(id);
		if (table == null) {
			throw new Http400Error(messageSource.getMessage("table.error.not.found.title",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("table.error.not.found.body",
					null, LocaleContextHolder.getLocale()));
		}
		orderService.setDiners(table.getOrder(), table.getOrder().getDiners() + 1);
		return new ModelAndView("redirect:/tables/" + table.getId());
	}

	@RequestMapping(value = "/{tableId}/subtract_diner", method = {RequestMethod.POST})
	public ModelAndView subtractDinerPost(@PathVariable("tableId") Integer id) {
		Table table = tableService.findById(id);
		if (table == null) {
			throw new Http400Error(messageSource.getMessage("table.error.not.found.title",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("table.error.not.found.body",
					null, LocaleContextHolder.getLocale()));
		}
		orderService.setDiners(table.getOrder(), table.getOrder().getDiners() - 1);
		return new ModelAndView("redirect:/tables/" + table.getId());
	}

	@RequestMapping(value = "/{tableId}/checkout")
	public ModelAndView getCheckoutBill(@PathVariable("tableId") Integer id) {

		Table table = tableService.findById(id);
		if (table == null) {
			throw new Http400Error(messageSource.getMessage("table.error.not.found.title",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("table.error.not.found.body",
					null, LocaleContextHolder.getLocale()));
		}
		ModelAndView mav = new ModelAndView("tables/checkout");
		mav.addObject("order", table.getOrder());

		return mav;
	}
}
