package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.exceptions.FreeTableDeletionAttemptException;
import edu.itba.paw.jimi.interfaces.exceptions.TableStatusInvalidTransitionException;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import edu.itba.paw.jimi.models.utils.QueryParams;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

@Transactional
@Service
public class TableServiceImpl implements TableService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TableServiceImpl.class);
	
	@Autowired
	private TableDao tableDao;
	
	@Autowired
	private OrderService orderService;
	
	@Override
	public Table findById(final long id) {
		return tableDao.findById(id);
	}
	
	@Override
	public Table create(String name) {
		Order order = orderService.create(OrderStatus.INACTIVE, null, null, 0);
		LOGGER.info("Created table {}", name);
		Table table;
		try {
			table = tableDao.create(name, TableStatus.FREE, order);
		} catch (PersistenceException e) {
			LOGGER.error("Error while storing", e);
			throw new ServiceException(e.getMessage());
		}
		return table;
	}
	
	@Override
	public Collection<Table> findAll() {
		Collection<Table> tables = tableDao.findAll();
		if (tables != null)
			return tables;
		else
			return new HashSet<Table>();
	}
	
	@Override
	public Collection<Table> findAll(QueryParams qp) {
		return findAll(qp.getPageSize(), qp.getStartAt());
	}

	@Override
	public Collection<Table> findAll(int maxResults, int offset) {
		Collection<Table> tables = tableDao.findAll(maxResults, offset);
		if (tables != null)
			return tables;
		else
			return new HashSet<Table>();
	}
	
	@Override
	public Collection<Table> findTablesWithStatus(TableStatus tableStatus) {
		return tableDao.findTablesWithStatus(tableStatus);
	}
	
	@Override
	public boolean tableNameExists(String tableName) {
		return tableDao.tableNameExists(tableName);
	}
	
	@Override
	public int getTotalTables() {
		return tableDao.getTotalTables();
	}
	
	@Override
	public void changeStatus(Table table, TableStatus status) {
		
		if (table.getStatus().equals(TableStatus.BUSY) && (!status.equals(TableStatus.PAYING) && !status.equals(TableStatus.FREE)))
			throw new TableStatusInvalidTransitionException(TableStatus.PAYING, status);
		
		if (table.getStatus().equals(TableStatus.PAYING) && !status.equals(TableStatus.FREE))
			throw new TableStatusInvalidTransitionException(TableStatus.FREE, status);
		
		if (table.getStatus().equals(TableStatus.FREE) && !status.equals(TableStatus.BUSY))
			throw new TableStatusInvalidTransitionException(TableStatus.BUSY, status);
		
		
		switch (status) {
			case BUSY: {
				orderService.open(table.getOrder());
				break;
			}
			case FREE: {
				if (table.getStatus().equals(TableStatus.BUSY)) {
					orderService.cancel(table.getOrder());
				}
				Order newOrder = orderService.create(OrderStatus.INACTIVE, null, null, 0);
				table.setOrder(newOrder);
				break;
			}
			case PAYING: {
				orderService.close(table.getOrder());
				break;
			}
		}
		
		table.setStatus(status);
		table.setStatus(status);
		tableDao.update(table);
		LOGGER.info("Updated table {}", table);
	}
	
	@Override
	public int getNumberOfTablesWithState(TableStatus tableStatus) {
		return tableDao.getNumberOfTablesWithState(tableStatus);
	}
	
	@Override
	public void setName(Table table, String name) {
		table.setName(name);
		tableDao.update(table);
		LOGGER.info("Updated table name {}", table);
	}
	
	@Override
	public void delete(long id) {
		Table table = tableDao.findById(id);
		if (table.getStatus() != TableStatus.FREE) {
			throw new FreeTableDeletionAttemptException();
		} else {
			tableDao.delete(id);
			LOGGER.info("Deleted table {}", table);
		}
	}
	
	@Override
	public Collection<Table> getTablesWithOrdersFromLastMinutes(int minutes) {
		if (minutes < 0)
			return new LinkedList<>();
		return tableDao.getTablesWithOrdersFromLastMinutes(minutes);
	}
	
}
