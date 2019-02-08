package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.exceptions.AddingDiscontinuedDishException;
import edu.itba.paw.jimi.interfaces.exceptions.DinersSetOnNotOpenOrderException;
import edu.itba.paw.jimi.interfaces.exceptions.OrderStatusException;
import edu.itba.paw.jimi.interfaces.exceptions.StockHandlingException;
import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.DishData;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private DishService dishService;

	/**
	 * Updates the total value of the object. Does not touch the DB!
	 *
	 * @param order The order to update.
	 */
	private void updateTotal(Order order) {
		float total = 0f;
		for (Map.Entry<Dish, DishData> d : order.getDishes().entrySet())
			total += d.getKey().getPrice() * d.getValue().getAmount();

		order.setTotal(total);
	}

	@Override
	public Order create(OrderStatus status, Timestamp openedAt, Timestamp closedAt, int diners) {
		LOGGER.info("Create order: {} | {} | {} | {}", status, openedAt, closedAt, diners);
		return orderDao.create(status, openedAt, closedAt, diners, 0f);
	}

	@Override
	public int addDish(Order order, Dish dish) {
		LOGGER.info("Add dish: {} | {}", order, dish);
		return addDishes(order, dish, 1);
	}

	@Override
	public int addDishes(Order order, Dish dish, int amount) {
		if (amount > dish.getStock())
			throw new StockHandlingException();

		if (dish.isDiscontinued())
			throw new AddingDiscontinuedDishException();

		int previousAmount;
		if (order.getUnDoneDishes().containsKey(dish))
			previousAmount = order.getUnDoneDishes().get(dish).getAmount();
		else
			previousAmount = 0;

		order.setDish(dish, previousAmount + amount);
		updateTotal(order);

		LOGGER.info("Updated order (add dishes): {}", order);

		// Update dish stock
		dishService.setStock(dish, dish.getStock() - amount);

		orderDao.update(order);

		if (order.getDishes().containsKey(dish))
			return order.getDishes().get(dish).getAmount();
		else
			return 0;
	}

	@Override
	public int removeOneUndoneDish(Order order, Dish dish) {
		return removeUndoneDish(order, dish, 1);
	}

	@Override
	public int removeUndoneDish(Order order, Dish dish, int amount) {
		if (order.getUnDoneDishes().containsKey(dish) && order.getUnDoneDishes().get(dish).getAmount() != 0) {
			//Here logic to remove undone dishes.
			int previousAmount = order.getUnDoneDishes().get(dish).getAmount();
			order.setDish(dish, previousAmount - amount);
			updateTotal(order);

			// Update dish stock
			dishService.setStock(dish, dish.getStock() + amount);

			orderDao.update(order);

			LOGGER.info("Updated order (remove dish amount): {}", order);
		}

		if (order.getDishes().containsKey(dish))
			return order.getDishes().get(dish).getAmount();
		else
			return 0;
	}

	@Override
	public int removeAllUndoneDish(Order order, Dish dish) {
		if (order.getUnDoneDishes().containsKey(dish) && order.getUnDoneDishes().get(dish).getAmount() != 0) {
			return removeUndoneDish(order, dish, order.getDishes().get(dish).getAmount());
		}
		return 0;
	}

	@Override
	public void setNewUndoneDishAmount(Order order, Dish dish, int newAmount) {
		final int currentAmount = order.getDishes().get(dish).getAmount();
		if (currentAmount < newAmount) {
			addDishes(order, dish, newAmount - currentAmount);
		} else if (currentAmount > newAmount) {
			removeUndoneDish(order, dish, currentAmount - newAmount);
		}
	}

	@Override
	public boolean containsDish(Order order, int dishId) {
		return getDishById(order, dishId) != null;
	}

	@Override
	public Dish getDishById(Order order, int dishId) {
		return orderDao.findById(order.getId()).getDishes().keySet().stream()
				.filter(d -> d.getId() == dishId)
				.findFirst()
				.orElse(null);
	}

	@Override
	public int setDiners(Order order, int diners) {
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new DinersSetOnNotOpenOrderException();

		if (diners >= 0) {
			order.setDiners(diners);
			orderDao.update(order);

			LOGGER.info("Updated order (set diners): {}", order);

			return diners;
		}
		return 0;
	}

	@Override
	public Order findById(long id) {
		return orderDao.findById(id);
	}

	@Override
	public void open(Order order) {
		if (!order.getStatus().equals(OrderStatus.INACTIVE))
			throw new OrderStatusException(OrderStatus.INACTIVE, order.getStatus());

		order.setOpenedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		order.setStatus(OrderStatus.OPEN);
		orderDao.update(order);

		LOGGER.info("Opened order {}", order);
	}

	@Override
	public void close(Order order) {
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new OrderStatusException(OrderStatus.OPEN, order.getStatus());

		order.setStatus(OrderStatus.CLOSED);
		order.setClosedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		orderDao.update(order);

		LOGGER.info("Closed order {}", order);
	}

	@Override
	public void cancel(Order order) {
		if (!order.getStatus().equals(OrderStatus.OPEN))
			throw new OrderStatusException(OrderStatus.OPEN, order.getStatus());

		order.setStatus(OrderStatus.CANCELED);
		order.setClosedAt(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		orderDao.update(order);

		LOGGER.info("Canceled order {}", order);
	}

	@Override
	public Collection<Order> findAll(int maxResults, int offset) {
		Collection<Order> orders = orderDao.findAll(maxResults, offset);
		if (orders != null)
			return orders;
		else
			return new HashSet<Order>();
	}

	@Override
	public Collection<Order> findCancelledOrClosedOrders(int maxResults, int offset) {
		return orderDao.findCancelledOrClosedOrders(maxResults, offset);
	}

	@Override
	public Map getMonthlyOrderTotal() {
		return orderDao.getMonthlyOrderTotal();
	}

	@Override
	public Map getMonthlyOrderCancelled() {
		return orderDao.getMonthlyOrderCancelled();
	}

	@Override
	public void setDishAsDone(Order order, Dish dish) {
		if (order.getUnDoneDishes().containsKey(dish)) {
			int amount = order.getDishes().get(dish).getAmount();
			order.setDish(dish, 0);
			order.setDoneDish(dish, amount);
			orderDao.update(order);
		}
	}

	@Override
	public int getTotalCancelledOrClosedOrders() {
		return orderDao.getTotalCancelledOrClosedOrders();
	}

	@Override
	public Collection<Order> getActiveOrders(int maxResults, int offset) {
		return orderDao.getActiveOrders(maxResults, offset);
	}

	@Override
	public int getTotalActiveOrders() {
		return orderDao.getTotalActiveOrders();
	}

	@Override
	public Collection<Order> getOrdersFromLastMinutes(int minutes) {
		return orderDao.getOrdersFromLastMinutes(minutes);
	}

	@Override
	public Map getAllUndoneDishesFromAllActiveOrders() {
		return orderDao.getAllUndoneDishesFromAllActiveOrders();
	}

}
