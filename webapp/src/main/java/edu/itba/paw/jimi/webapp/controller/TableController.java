package edu.itba.paw.jimi.webapp.controller;

import edu.itba.paw.jimi.form.TableAddDishForm;
import edu.itba.paw.jimi.form.TableForm;
import edu.itba.paw.jimi.form.TableSetDinersForm;
import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/tables")
public class TableController {

    @Autowired
    private TableService ts;

    @Autowired
    private OrderService os;

    @Autowired
    private DishService ds;


    @RequestMapping("")
    public ModelAndView list() {
        final ModelAndView mav = new ModelAndView("tables/list_tables");
        // TODO , el dia de manana se busca con queryparams
        mav.addObject("tables", ts.findAll());
        return mav;
    }

    @RequestMapping("/{id}")
    public ModelAndView index(@PathVariable("id") Integer id, @ModelAttribute("tableSetDinersForm") final TableSetDinersForm form) {
        final ModelAndView mav = new ModelAndView("tables/index");
        Table table = ts.findById(id);
        mav.addObject("table", table);
        mav.addObject("dishes", table.getOrder().getDishes());
        return mav;
    }

    @RequestMapping(value = "/{tableId}/status", method = {RequestMethod.POST})
    public ModelAndView statusChange(@PathVariable("tableId") Integer id, @RequestParam(value = "status") final Integer statusId) {

        //TODO: Cuando pasa de busy a free hay que llevar a una pantalla de cerrar cuenta y despues limpiar (los datos) de la mesa. Eso deberia ir adentro del servicio. Y el controler deberia elegir a que pagina ir dependiendo de la transision.
        ts.changeStatus(ts.findById(id), TableStatus.getTableStatus(statusId));

        return new ModelAndView("redirect:/tables/" + id);
    }

    @RequestMapping("/register")
    public ModelAndView register(@ModelAttribute("registerForm") final TableForm form) {
        return new ModelAndView("tables/register");
    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("registerForm") final TableForm form, final BindingResult errors) {

        if (errors.hasErrors()) { return register(form); }

        final Table tb = ts.create(form.getName());

        return new ModelAndView("redirect:/tables/" + tb.getId());
    }

    @RequestMapping(value = "/{tableId}/add_dish", method = {RequestMethod.GET})
    public ModelAndView addDish(@PathVariable("tableId") Integer id, @ModelAttribute("tableAddDishForm") final TableAddDishForm form) {

        ModelAndView mav = new ModelAndView("tables/add_dish");
        mav.addObject("table", ts.findById(id));
        mav.addObject("dishes", ds.findAll());

        return mav;
    }

    @RequestMapping(value = "/{tableId}/add_dish", method = {RequestMethod.POST})
    public ModelAndView addDishPost(@PathVariable("tableId") Integer id, @Valid @ModelAttribute("tableAddDishForm") final TableAddDishForm form, final BindingResult errors) {

        if (errors.hasErrors()) { return addDish(id, form); }

        Table table = ts.findById(id);
        Dish dish = ds.findById(form.getDishid());
        os.addDishes(table.getOrder(), dish, form.getAmount()); // TODO handle StockHandlingException

        return new ModelAndView("redirect:/tables/" + table.getId());
    }

    @RequestMapping(value = "/{tableId}/add_one_dish", method = {RequestMethod.POST})
    public ModelAndView addOneDishPost(@PathVariable("tableId") Integer id, @RequestParam(value = "dishid") final Integer dishid) {

        Table table = ts.findById(id);
        Dish dish = ds.findById(dishid);
        os.addDish(table.getOrder(), dish);

        return new ModelAndView("redirect:/tables/" + table.getId());
    }

    @RequestMapping(value = "/{tableId}/remove_one_dish", method = {RequestMethod.POST})
    public ModelAndView removeOneDishPost(@PathVariable("tableId") Integer id, @RequestParam(value = "dishid") final Integer dishid) {

        Table table = ts.findById(id);
        Dish dish = ds.findById(dishid);
        os.removeOneDish(table.getOrder(), dish);

        return new ModelAndView("redirect:/tables/" + table.getId());
    }

    @RequestMapping(value = "/{tableId}/remove_all_dish", method = {RequestMethod.POST})
    public ModelAndView removeAllDishPost(@PathVariable("tableId") Integer id, @RequestParam(value = "dishid") final Integer dishid) {

        Table table = ts.findById(id);
        Dish dish = ds.findById(dishid);
        os.removeAllDish(table.getOrder(), dish);

        return new ModelAndView("redirect:/tables/" + table.getId());
    }


    @RequestMapping(value = "/{tableId}/set_diners", method = {RequestMethod.POST})
    public ModelAndView setDinersPost(@PathVariable("tableId") Integer id, @Valid @ModelAttribute("tableSetDinersForm") final TableSetDinersForm form, final BindingResult errors) {

        if (errors.hasErrors()) { return index(id, form); }

        Table table = ts.findById(id);
        ts.setDiners(table, form.getDiners());

        return new ModelAndView("redirect:/tables/" + table.getId());
    }

    @RequestMapping(value = "/{tableId}/checkout")
    public ModelAndView getCheckoutBill(@PathVariable("tableId") Integer id) {

        ModelAndView mav = new ModelAndView("tables/checkout");
        mav.addObject("order", ts.findById(id).getOrder());

        return mav;
    }
}