package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.interfaces.exceptions.Http404Error;
import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.utils.QueryParams;
import edu.itba.paw.jimi.webapp.dto.form.DishForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/dishes")
public class DishController {
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value = {"/create"}, method = {RequestMethod.GET})
	public ModelAndView register(@ModelAttribute("dishCreateForm") final DishForm form) {
		
		return new ModelAndView("dishes/create");
	}
	
	@RequestMapping(value = "/create", method = {RequestMethod.POST})
	public ModelAndView create(@Valid @ModelAttribute("dishCreateForm") final DishForm form, final BindingResult errors) {
		
		if (errors.hasErrors()) {
			return register(form);
		}
		
		final Dish d = dishService.create(form.getName(), form.getPrice());
		dishService.setStock(d, form.getStock());
		dishService.setMinStock(d, form.getMinStock());
		
		
		return new ModelAndView("redirect:/admin/dishes");
	}
	
	@RequestMapping(value = {"/edit/{dishid}"}, method = {RequestMethod.GET})
	public ModelAndView update(@ModelAttribute("dishCreateForm") final DishForm form, @PathVariable("dishid") long dishid) {
		
		Dish dish = dishService.findById(dishid);
		
		form.setName(dish.getName());
		form.setPrice(dish.getPrice());
		form.setStock(dish.getStock());
		form.setMinStock(dish.getMinStock());
		form.setMinStock(dish.getMinStock());
		form.setDiscontinued(dish.isDiscontinued());
		
		ModelAndView mv = new ModelAndView("dishes/edit");
		mv.addObject("dish", dish);
		
		return mv;
	}
	
	@RequestMapping(value = "/edit/{dishid}", method = {RequestMethod.POST})
	public ModelAndView updateP(@Valid @ModelAttribute("dishCreateForm") final DishForm form, final BindingResult errors, @PathVariable("dishid") long dishid) {
		
		if (errors.hasErrors()) {
			return register(form);
		}
		
		Dish dish = dishService.findById(dishid);
		
		dishService.setStock(dish, form.getStock());
		dishService.setPrice(dish, form.getPrice());
		dishService.setMinStock(dish, form.getMinStock());
		dishService.setDiscontinued(dish, form.isDiscontinued());
		
		return new ModelAndView("redirect:/admin/dishes");
	}
	
	private static final int PAGE_SIZE = 10;
	
	@RequestMapping("")
	public ModelAndView list() {
		final ModelAndView mav = new ModelAndView("dishes/list");
		
		QueryParams qp = new QueryParams(0, PAGE_SIZE, dishService.getTotalDishes());
		List<Dish> dishes = (List<Dish>) dishService.findAll(qp);
		
		mav.addObject("dishes", dishes);
		mav.addObject("qp", qp);
		return mav;
	}
	
	@RequestMapping("/page/{page}")
	public ModelAndView listPage(@PathVariable("page") Integer page) {
		final ModelAndView mav = new ModelAndView("dishes/list");
		
		QueryParams qp = new QueryParams((page - 1) * PAGE_SIZE, PAGE_SIZE, dishService.getTotalDishes());
		List<Dish> dishes = (List<Dish>) dishService.findAll(qp);
		
		mav.addObject("dishes", dishes);
		mav.addObject("qp", qp);
		return mav;
	}
	
	@RequestMapping(value = "/stock/increase", method = {RequestMethod.POST})
	public ModelAndView increaseStock(@RequestParam(value = "dishid") final long dishid, @RequestParam(value = "page", defaultValue = "1") final int page) {
		Dish dish = dishService.findById(dishid);
		dishService.increaseStock(dish);
		
		return new ModelAndView("redirect:/admin/dishes/page/" + page);
	}
	
	@RequestMapping(value = "/stock/decrease", method = {RequestMethod.POST})
	public ModelAndView decreaseStock(@RequestParam(value = "dishid") final long dishid, @RequestParam(value = "page", defaultValue = "1") final int page) {
		Dish dish = dishService.findById(dishid);
		dishService.decreaseStock(dish);
		
		return new ModelAndView("redirect:/admin/dishes/page/" + page);
	}
	
	@RequestMapping(value = "/{dishId}/set_min_stock", method = {RequestMethod.POST})
	public ModelAndView setDishMinStockPost(
			@PathVariable("dishId") Integer id,
			@RequestParam(value = "minStock") int minStock) {
		
		Dish dish = dishService.findById(id);
		
		if (dish == null) {
			throw new Http404Error(messageSource.getMessage("error.404.title",
					null, LocaleContextHolder.getLocale()), messageSource.getMessage("dish.error.404.body",
					null, LocaleContextHolder.getLocale()));
		}
		
		dishService.setMinStock(dish, minStock);
		
		return new ModelAndView("redirect:/dishes/" + dish.getId());
	}
	
	@RequestMapping(value = "/csvStockWarning", method = RequestMethod.POST)
	public void downloadCsv(HttpServletResponse response) throws IOException {
		
		response.setContentType("text/csv");
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", messageSource.getMessage("csv.file", null, LocaleContextHolder.getLocale()));
		response.setHeader(headerKey, headerValue);
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] headerT = {
				messageSource.getMessage("csv.name", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("csv.price", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("csv.stock", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("csv.minStock", null, LocaleContextHolder.getLocale()),
		};
		csvWriter.writeHeader(headerT);
		
		
		String[] header = {"name", "price", "stock", "minStock"};
		for (Dish dish : dishService.findDishesMissingStock())
			csvWriter.write(dish, header);
		
		csvWriter.close();
		
	}
	
}
