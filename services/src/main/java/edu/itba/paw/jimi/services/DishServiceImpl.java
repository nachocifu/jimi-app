package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.interfaces.services.DishService;
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

    public int setStock(Dish dish, int stock) {

        if(stock < 0) {
            return 0;
        }

        dish.setStock(stock);
        dishDao.update(dish);
        return dish.getStock();
    }

    public Collection<Dish> findAll() {
        return dishDao.findAll();
    }
}
