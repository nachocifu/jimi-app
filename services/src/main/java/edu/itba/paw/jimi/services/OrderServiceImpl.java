package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDao orderDao;

    public int addDish(Order order, Dish dish) {
        int previousAmount;
        if (order.getDishes().containsKey(dish))
            previousAmount = order.getDishes().get(dish);
        else
            previousAmount = 0;

        order.setDish(dish, previousAmount + 1);
        orderDao.update(order);
        Order dbOrder = orderDao.findById(order.getId());
        return dbOrder.getDishes().get(dish);
    }

    public int removeOneDish(Order order, Dish dish) {
        int previousAmount;
        if (order.getDishes().containsKey(dish))
            previousAmount = order.getDishes().get(dish);
        else
            return 0;

        if (previousAmount == 0)
            return 0;

        order.setDish(dish, previousAmount - 1);
        orderDao.update(order);
        Order dbOrder = orderDao.findById(order.getId());
        if (!dbOrder.getDishes().containsKey(dish))
            return 0;
        else
            return dbOrder.getDishes().get(dish);
    }

    public int removeAllDish(Order order, Dish dish) {
        order.setDish(dish, 0);
        orderDao.update(order);
        Order dbOrder = orderDao.findById(order.getId());
        if (!dbOrder.getDishes().containsKey(dish))
            return 0;
        else
            return dbOrder.getDishes().get(dish);
    }
}
