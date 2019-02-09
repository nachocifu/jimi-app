package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.*;

@SuppressWarnings("unchecked")
@Repository
public class OrderHibernateDao implements OrderDao {

	@PersistenceContext(unitName = "testName")
	private EntityManager em;

	@Override
	public Order findById(long id) {
		return em.find(Order.class, id);
	}

	@Override
	public Order create(OrderStatus status, Timestamp openedAt, Timestamp closedAt, int diners, float total) {
		final Order order = new Order(openedAt, closedAt, status, diners, total);
		em.persist(order);
		return order;
	}

	@Override
	public void update(Order order) {
		em.merge(order);
	}

	@Override
	public Collection<Order> findAll(int maxResults, int offset) {
		return em.createQuery("from Order", Order.class)
				.setFirstResult(offset)
				.setMaxResults(maxResults)
				.getResultList();
	}

	@Override
	public Map<YearMonth, Double> getMonthlyOrderTotal() {
		Map<YearMonth, Double> response = new TreeMap<YearMonth, Double>() {
		};
		Query query = em.createNativeQuery(
				"SELECT CAST(extract(year FROM closedat) as INT) as year, CAST(extract(month FROM closedat) as INT) as month, CAST(SUM(total) AS FLOAT) " +
						"FROM orders " +
						"GROUP BY year, month " +
						"ORDER BY year, month");

		List<Object[]> res = query.getResultList();
		for (Object[] row : res) {
			if (Arrays.asList(row).contains(null)) continue;
			if ((double) row[2] == 0) continue;
			response.put(YearMonth.of(Integer.valueOf(row[0].toString()), Integer.valueOf(row[1].toString())), (double) row[2]);
		}
		return response;
	}

	@Override
	public Collection<Order> findCancelledOrClosedOrders(int maxResults, int offset) {
		return em.createQuery("FROM Order AS o WHERE o.status = :closed OR o.status = :canceled", Order.class)
				.setParameter("closed", OrderStatus.CLOSED)
				.setParameter("canceled", OrderStatus.CANCELED)
				.setFirstResult(offset)
				.setMaxResults(maxResults)
				.getResultList();
	}

	@Override
	public int getTotalCancelledOrClosedOrders() {
		return ((Long) em.createQuery("select count(*) from Order as o where o.status = :closed or o.status = :canceled")
				.setParameter("closed", OrderStatus.CLOSED)
				.setParameter("canceled", OrderStatus.CANCELED)
				.getSingleResult()).intValue();
	}

	@Override
	public int getTotalActiveOrders() {
		return ((Long) em.createQuery("select count(*) from Order as o where o.status = :opened")
				.setParameter("opened", OrderStatus.OPEN)
				.getSingleResult()).intValue();
	}

	@Override
	public Map<YearMonth, Integer> getMonthlyOrderCancelled() {
		Map<YearMonth, Integer> response = new TreeMap<YearMonth, Integer>() {
		};
		Query query = em.createNativeQuery(
				"SELECT CAST(extract(year FROM closedat) as INT) as year, CAST(extract(month FROM closedat) as INT) as month, CAST(COUNT(id) AS INT) as total " +
						"FROM orders " +
						"WHERE status = " + OrderStatus.CANCELED.ordinal() + " " +
						"GROUP BY year, month " +
						"ORDER BY year, month");

		List<Object[]> res = query.getResultList();
		for (Object[] row : res) {
			if (Arrays.asList(row).contains(null)) continue;
			if ((int) row[2] == 0) continue;
			response.put(YearMonth.of(Integer.valueOf(row[0].toString()), Integer.valueOf(row[1].toString())), (int) row[2]);
		}
		return response;
	}

	@Override
	public Collection<Order> getActiveOrders(int maxResults, int offset) {
		return em.createQuery("FROM Order o WHERE o.status = :opened ORDER BY o.openedAt ASC", Order.class)
				.setParameter("opened", OrderStatus.OPEN)
				.setFirstResult(offset)
				.setMaxResults(maxResults)
				.getResultList();
	}

	@Override
	public Collection<Order> getOrdersFromLastMinutes(int minutes) {
		final TypedQuery<Order> query = em.createQuery(
				"from Order as o join o.unDoneDishes as ud WHERE o.status = :opened and ud.orderedAt < :lastMinutes " +
						"order by o.openedAt ASC", Order.class);
		query.setParameter("opened", OrderStatus.OPEN);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, -minutes);
		query.setParameter("lastMinutes", cal.getTime());

		return query.getResultList();
	}

	@Override
	public Map<Dish, Long> getAllUndoneDishesFromAllActiveOrders() {
		TypedQuery<Object[]> query = em.createQuery(
				"SELECT KEY(u), SUM(u.amount) " +
						"FROM Order AS o JOIN o.unDoneDishes AS u " +
						"WHERE o.status = :opened " +
						"GROUP BY KEY(u), dishid ", Object[].class);
		query.setParameter("opened", OrderStatus.OPEN);
		Map<Dish, Long> totalDishes = new HashMap<>();
		for (Object[] result : query.getResultList())
			totalDishes.put((Dish) result[0], (Long) result[1]);
		return totalDishes;
	}
}
