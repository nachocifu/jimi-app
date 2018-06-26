package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.exceptions.TableWithNullOrderException;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class TableHibernateDao implements TableDao{

    @PersistenceContext(unitName = "testName", type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Autowired
    private OrderDao orderDao;

    public Table findById(long id) {
        return em.find(Table.class, id);
    }

    public void update(Table table) {
        em.merge(table);
    }

    public Collection<Table> findAll() {
        final TypedQuery<Table> query = em.createQuery("from Table", Table.class);
        return query.getResultList();
    }

    public Table create(String name, TableStatus ts, Order order) throws TableWithNullOrderException {

        if (order == null || orderDao.findById(order.getId()) == null)
            throw new TableWithNullOrderException();


        final Table table = new Table(name, ts, order);
        em.persist(table);
        return table;
    }
}
