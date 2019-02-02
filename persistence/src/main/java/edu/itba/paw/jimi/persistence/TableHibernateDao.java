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

	public Collection<Table> findAll(int maxResults, int offset) {
        return em.createQuery("from Table order by name", Table.class)
                .setFirstResult(offset)
                .setMaxResults(maxResults)
                .getResultList();
	}

	@Override
	public Collection<Table> findTablesWithStatus(TableStatus tableStatus) {
        return em.createQuery("from Table as t where t.status = :tableStatus order by t.order.openedAt", Table.class)
                .setParameter("tableStatus", tableStatus)
                .getResultList();
	}

	@Override
	public int getTotalTables() {
        return em.createQuery("select count(*) from Table", Long.class).getSingleResult().intValue();
	}

	public Table create(String name, TableStatus ts, Order order) {
		if (order == null || orderDao.findById(order.getId()) == null) //TODO Are we sure order can be null?
			throw new TableWithNullOrderException();
		final Table table = new Table(name, ts, order);
		em.persist(table);
		return table;
	}

	@Override
	public int getNumberOfTablesWithState(TableStatus tableStatus) {
        return em.createQuery("select count(*) from Table as t where t.status = :tableStatus", Long.class)
                .setParameter("tableStatus", tableStatus)
                .getSingleResult().intValue();
	}

	@Override
	public void delete(long id) {
		Table tableToDelete = em.find(Table.class, id);
		em.remove(tableToDelete);
	}

	@Override
	public Collection<Table> getTablesWithOrdersFromLastMinutes(int minutes) {
		final TypedQuery<Table> query = em.createQuery("from Table as t where t.status = :tableStatus " +
				"and t.order in " +
				"(from Order as o join o.unDoneDishes as ud " +
				"where o.status = :opened and ud.orderedAt < :lastMinutes)" +
				"order by t.order.openedAt", Table.class);
		query.setParameter("tableStatus", TableStatus.BUSY);
		query.setParameter("opened", OrderStatus.OPEN);

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, -minutes);
		query.setParameter("lastMinutes", cal.getTime());

		return query.getResultList();
	}

	public boolean tableNameExists(String tableName) {
        return !em.createQuery("from Table as t where t.name = :tableName", Table.class)
                .setParameter("tableName", tableName).getResultList().isEmpty();
	}
}
