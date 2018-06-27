package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.form.DishForm;
import edu.itba.paw.jimi.interfaces.exceptions.Http404Error;
import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Utilities.QueryParams;
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

    @RequestMapping(value = {"/create"}, method = { RequestMethod.GET })
    public ModelAndView register(@ModelAttribute("dishCreateForm") final DishForm form) {

        return new ModelAndView("dishes/create");
    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("dishCreateForm") final DishForm form, final BindingResult errors) {

        if (errors.hasErrors()) { return register(form); }

        final Dish d = dishService.create(form.getName(), form.getPrice());
        final int i = dishService.setStock(d, form.getStock());

        return new ModelAndView("redirect:/admin/dishes");
    }

    private static final int PAGE_SIZE = 3;

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

    @RequestMapping(value = "/stock/increase", method = { RequestMethod.POST })
    public ModelAndView increaseStock(@RequestParam(value = "dishid") final long dishid, @RequestParam(value = "page", defaultValue = "1") final int page) {
        Dish dish = dishService.findById(dishid);
        dishService.increaseStock(dish);

        return new ModelAndView("redirect:/admin/dishes/page/" + page);
    }

    @RequestMapping(value = "/stock/decrease", method = { RequestMethod.POST })
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

    @RequestMapping(value = "csvStockWarning", method = RequestMethod.POST)
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
        for (Dish dish : dishService.findAll()) //TODO bring only does where stock<minStock
            csvWriter.write(dish, header);

        csvWriter.close();

    }

}
