package edu.itba.paw.jimi.interfaces.daos;

import edu.itba.paw.jimi.interfaces.exceptions.TableWithNullOrderException;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import edu.itba.paw.jimi.models.Utilities.QueryParams;

import javax.persistence.PersistenceException;
import java.util.Collection;

public interface TableDao {
	
	/**
	 * Returns a table with the passed id.
	 *
	 * @param id the id to look for.
	 * @return the table with said id.
	 */
	Table findById(final long id);
	
	/**
	 * Updates the table.
	 *
	 * @param table The table to be updated.
	 */
	void update(Table table);
	
	/**
	 * Returns all the tables.
	 *
	 * @return all the tables.
	 */
	Collection<Table> findAll();
	
	/**
	 * Returns all the tables.
	 *
	 * @return all the tables.
	 */
	Collection<Table> findAll(QueryParams qp);
	
	int getTotalTables();
	
	/**
	 * Returns true if a table exists with tableName.
	 */
	boolean tableNameExists(String tableName);
	
	/**
	 * Creates a Table.
	 *
	 * @param name  Name of the table.
	 * @param ts    Status of the table.
	 * @param order Tables order.
	 * @return The created table.
	 * @throws TableWithNullOrderException when a order not in the DB or null is passed.
	 */
	Table create(String name, TableStatus ts, Order order) throws PersistenceException;
	
	/**
	 * Gets number of tables with status tableStatus.
	 *
	 * @return umber of tables with status tableStatus.
	 */
	int getNumberOfTablesWithState(TableStatus tableStatus);
	
	/**
	 * Deletes a Table.
	 *
	 * @param id Id of the table.
	 */
	void delete(final long id);
	
}
