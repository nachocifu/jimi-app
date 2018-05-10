package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.DishDao;
import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.exceptions.DishAddedToInactiveOrderException;
import edu.itba.paw.jimi.interfaces.exceptions.OrderStatusException;
import edu.itba.paw.jimi.interfaces.exceptions.StockHandlingException;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private DishDao dishDao;

    public Order create(OrderStatus status, Timestamp openedAt, Timestamp closedAt) {
        return orderDao.create(status, openedAt, closedAt);
    }

    public int addDish(Order order, Dish dish) {

        return addDishes(order, dish, 1);
    }

    public int addDishes(Order order, Dish dish, int amount){

        if (!order.getStatus().equals(OrderStatus.OPEN))
            throw new DishAddedToInactiveOrderException();

        if (amount > dish.getStock())
            throw new StockHandlingException("Amount of dishes exceeds available dish stock.");

        int previousAmount;
        if (order.getDishes().containsKey(dish))
            previousAmount = order.getDishes().get(dish);
        else
            previousAmount = 0;

        order.setDish(dish, previousAmount + amount);
        orderDao.update(order);
        dish.setStock(dish.getStock() - amount);
        dishDao.update(dish);
        Order dbOrder = orderDao.findById(order.getId());
        return dbOrder.getDishes().get(dish);
    }

    public int removeOneDish(Order order, Dish dish) {

        if (!order.getStatus().equals(OrderStatus.OPEN))
            throw new DishAddedToInactiveOrderException();

        int previousAmount;
        if (order.getDishes().containsKey(dish))
            previousAmount = order.getDishes().get(dish);
        else
            return 0;

        if (previousAmount == 0)
            return 0;

        order.setDish(dish, previousAmount - 1);
        orderDao.update(order);
        dish.setStock(dish.getStock() + 1);
        dishDao.update(dish);
        Order dbOrder = orderDao.findById(order.getId());
        if (!dbOrder.getDishes().containsKey(dish))
            return 0;
        else
            return dbOrder.getDishes().get(dish);
    }

    public int removeAllDish(Order order, Dish dish) {

        if (!order.getStatus().equals(OrderStatus.OPEN))
            throw new DishAddedToInactiveOrderException();

        order.setDish(dish, 0);
        orderDao.update(order);
        Order dbOrder = orderDao.findById(order.getId());
        if (!dbOrder.getDishes().containsKey(dish))
            return 0;
        else
            return dbOrder.getDishes().get(dish);
    }

    public void open(Order order) {

        if (!order.getStatus().equals(OrderStatus.INACTIVE))
            throw new OrderStatusException(OrderStatus.INACTIVE, order.getStatus());


        order.setOpenedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        order.setStatus(OrderStatus.OPEN);
        orderDao.update(order);
    }

    public void close(Order order) {

        if (!order.getStatus().equals(OrderStatus.OPEN))
            throw new OrderStatusException(OrderStatus.OPEN, order.getStatus());
        //TODO: SE DEBE REALIZAR EL COBRO ETC..
        order.setStatus(OrderStatus.CLOSED);
        order.setClosedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        orderDao.update(order);
    }
}
