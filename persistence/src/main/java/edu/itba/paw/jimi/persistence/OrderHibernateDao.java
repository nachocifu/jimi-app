package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.Collection;

@Repository
public class OrderHibernateDao implements OrderDao{

    @PersistenceContext(unitName = "testName", type = PersistenceContextType.EXTENDED)
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
        em.persist(order);
    }

    public Collection<Order> findAll() {
        final TypedQuery<Order> query = em.createQuery("from Order", Order.class);
        return query.getResultList();
    }
}
