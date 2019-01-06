package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import edu.itba.paw.jimi.models.Utilities.QueryParams;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.*;

@Repository
public class OrderHibernateDao implements OrderDao{

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
        return ((Long)query.getSingleResult()).intValue();
    }

    @Override
    public int getTotalActiveOrders() {
        Query query = em.createQuery("select count(*) from Order as o where o.status = :opened");
        query.setParameter("opened", OrderStatus.OPEN);
        return ((Long)query.getSingleResult()).intValue();
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
    public Collection<Order> getActiveOrders(QueryParams qp) {
        String ordering = "";
        if (qp.getOrderBy() != null)
            ordering += " order by " + qp.getOrderBy();

        if (qp.isAscending())
            ordering += " ASC";
        else
            ordering += " DESC";

        final Query query = em.createQuery("from Order as o where o.status = :opened" + ordering, Order.class);
        query.setParameter("opened", OrderStatus.OPEN);

        if (qp.getStartAt() != QueryParams.NO_VALUE) {
            query.setFirstResult(qp.getStartAt());
            query.setMaxResults(qp.getPageSize());
        }

        return (Collection<Order>) query.getResultList();
    }
}
