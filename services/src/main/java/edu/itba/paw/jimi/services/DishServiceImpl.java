package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.DishDao;
import edu.itba.paw.jimi.interfaces.DishService;
import edu.itba.paw.jimi.models.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DishServiceImpl implements DishService{

    @Autowired
    private DishDao dishDao;

    public Dish findById(long id) {
        return dishDao.findById(id);
    }

    public Dish create(String name, float price) {
        return dishDao.create(name, price, 0);
    }

    public int modifyStock(Dish dish, int stock) {
        dish.setStock(stock); //Set the stock to the new correct value.
        dishDao.update(dish);
        return dish.getStock();
    }

    public Collection<Dish> findAll() {
        return dishDao.findAll();
    }
}
