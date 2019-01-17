package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.exceptions.TableWithNullOrderException;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import edu.itba.paw.jimi.models.Utilities.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class TableHibernateDao implements TableDao {
	
	@PersistenceContext(unitName = "testName")
	private EntityManager em;
	
	@Autowired
	private OrderDao orderDao;
	
	@Override
	public Table findById(long id) {
		return em.find(Table.class, id);
	}
	
	@Override
	public void update(Table table) {
		em.merge(table);
	}
	
	@Override
	public Collection<Table> findAll() {
		final TypedQuery<Table> query = em.createQuery("from Table order by name", Table.class);
		return query.getResultList();
	}
	
	@Override
	public Collection<Table> findAll(QueryParams qp) {
		final Query query = em.createQuery("from Table order by name", Table.class);
		query.setFirstResult(qp.getStartAt());
		query.setMaxResults(qp.getPageSize());
		
		return (Collection<Table>) query.getResultList();
	}
	
	@Override
	public int getTotalTables() {
		Long query = em.createQuery("select count(*) from Table", Long.class).getSingleResult();
		return query.intValue();
	}
	
	@Override
	public Table create(String name, TableStatus ts, Order order) throws TableWithNullOrderException {
		
		if (order == null || orderDao.findById(order.getId()) == null)
			throw new TableWithNullOrderException();
		
		
		final Table table = new Table(name, ts, order);
		em.persist(table);
		return table;
	}
}
