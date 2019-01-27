package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import edu.itba.paw.jimi.models.utils.QueryParams;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.*;

@Repository
public class OrderHibernateDao implements OrderDao {
	
	@PersistenceContext(unitName = "testName")
	private EntityManager em;
	
	public Order findById(long id) {
		return em.find(Order.class, id);
	}
	
	public Order create(OrderStatus status, Timestamp openedAt, Timestamp closedAt, int diners, float total) {
		final Order order = new Order(openedAt, closedAt, status, diners, total);
		em.persist(order);
		return order;
	}
	
	public void update(Order order) {
		em.merge(order);
	}
	
	public Collection<Order> findAll() {
		final TypedQuery<Order> query = em.createQuery("from Order", Order.class);
		return query.getResultList();
	}
	
	public Collection<Order> findAll(QueryParams qp) {
		
		String ordering = "";
		if (qp.getOrderBy() != null)
			ordering += "order by " + qp.getOrderBy();
		
		if (qp.isAscending())
			ordering += " ASC";
		else
			ordering += " DESC";
		
		final Query query = em.createQuery("from Order " + ordering, Order.class);
		
		if (qp.getStartAt() != QueryParams.NO_VALUE) {
			query.setFirstResult(qp.getStartAt());
			query.setMaxResults(qp.getPageSize());
		}
		
		return (Collection<Order>) query.getResultList();
	}
	
	public Map getMonthlyOrderTotal() {
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
	public Collection<Order> findAllRelevant(QueryParams qp) {
		String ordering = "";
		if (qp.getOrderBy() != null)
			ordering += " order by " + qp.getOrderBy();
		
		if (qp.isAscending())
			ordering += " ASC";
		else
			ordering += " DESC";
		
		final Query query = em.createQuery("from Order as o where o.status = :closed or o.status = :canceled" + ordering, Order.class);
		query.setParameter("closed", OrderStatus.CLOSED);
		query.setParameter("canceled", OrderStatus.CANCELED);
		
		if (qp.getStartAt() != QueryParams.NO_VALUE) {
			query.setFirstResult(qp.getStartAt());
			query.setMaxResults(qp.getPageSize());
		}
		
		return (Collection<Order>) query.getResultList();
	}
	
	@Override
	public int getTotalRelevantOrders() {
		Query query = em.createQuery("select count(*) from Order as o where o.status = :closed or o.status = :canceled");
		query.setParameter("closed", OrderStatus.CLOSED);
		query.setParameter("canceled", OrderStatus.CANCELED);
		return ((Long) query.getSingleResult()).intValue();
	}
	
	@Override
	public int getTotalActiveOrders() {
		Query query = em.createQuery("select count(*) from Order as o where o.status = :opened");
		query.setParameter("opened", OrderStatus.OPEN);
		return ((Long) query.getSingleResult()).intValue();
	}
	
	@Override
	public Map getMonthlyOrderCancelled() {
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
	public Collection<Order> getActiveOrders() {
		final Query query = em.createQuery("FROM Order o WHERE o.status = :opened ORDER BY o.openedAt ASC", Order.class);
		query.setParameter("opened", OrderStatus.OPEN);
		return (Collection<Order>) query.getResultList();
	}
	
	@Override
	public Collection<Order> get30MinutesWaitOrders() {
		final Query query = em.createQuery(
				"select o from Order as o join o.unDoneDishes as ud WHERE o.status = :opened and ud.orderedAt < :last30 " +
						"order by o.openedAt ASC");
		query.setParameter("opened", OrderStatus.OPEN);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, -30);
		query.setParameter("last30", cal.getTime());
		
		return (Collection<Order>) query.getResultList();
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
