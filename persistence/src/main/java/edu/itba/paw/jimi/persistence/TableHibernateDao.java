package edu.itba.paw.jimi.persistence;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.exceptions.TableWithNullOrderException;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import edu.itba.paw.jimi.models.utils.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public class TableHibernateDao implements TableDao {
	
	@PersistenceContext(unitName = "testName")
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
		final TypedQuery<Table> query = em.createQuery("from Table order by name", Table.class);
		return query.getResultList();
	}
	
	public Collection<Table> findAll(QueryParams qp) {
		final Query query = em.createQuery("from Table order by name", Table.class);
		query.setFirstResult(qp.getStartAt());
		query.setMaxResults(qp.getPageSize());
		
		return (Collection<Table>) query.getResultList();
	}
	
	@Override
	public Collection<Table> findTablesWithStatus(TableStatus tableStatus) {
		final TypedQuery<Table> query = em.createQuery("from Table as t where t.status = :tableStatus order by t.order.openedAt", Table.class);
		query.setParameter("tableStatus", tableStatus);
		return query.getResultList();
	}
	
	@Override
	public int getTotalTables() {
		Long query = em.createQuery("select count(*) from Table", Long.class).getSingleResult();
		return query.intValue();
	}
	
	public Table create(String name, TableStatus ts, Order order) {
		if (order == null || orderDao.findById(order.getId()) == null)
			throw new TableWithNullOrderException();
		final Table table = new Table(name, ts, order);
		em.persist(table);
		return table;
	}
	
	@Override
	public int getNumberOfTablesWithState(TableStatus tableStatus) {
		final TypedQuery<Long> query = em.createQuery("select count(*) from Table as t where t.status = :tableStatus", Long.class);
		query.setParameter("tableStatus", tableStatus);
		return query.getSingleResult().intValue();
	}
	
	@Override
	public void delete(long id) {
		Table tableToDelete = em.find(Table.class, id);
		em.remove(tableToDelete);
	}
	
	@Override
	public Collection<Table> getUrgentTables() {
		final TypedQuery<Table> query = em.createQuery("from Table as t where t.status = :tableStatus " +
				"and t.order in " +
				"(from Order as o join o.unDoneDishes as ud " +
				"where o.status = :opened and ud.orderedAt < :last30)" +
				"order by t.order.openedAt", Table.class);
		query.setParameter("tableStatus", TableStatus.BUSY);
		query.setParameter("opened", OrderStatus.OPEN);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, -30);
		query.setParameter("last30", cal.getTime());
		
		return query.getResultList();
	}
	
	public boolean tableNameExists(String tableName) {
		final TypedQuery<Table> query = em.createQuery("from Table as t where t.name = :tableName", Table.class);
		query.setParameter("tableName", tableName);
		List<Table> l = query.getResultList();
		return !l.isEmpty();
	}
}
