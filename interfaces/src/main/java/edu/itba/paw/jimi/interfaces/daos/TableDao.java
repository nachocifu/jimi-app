package edu.itba.paw.jimi.interfaces.daos;

import edu.itba.paw.jimi.interfaces.exceptions.TableWithNullOrderException;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import edu.itba.paw.jimi.models.Utilities.QueryParams;

import java.util.Collection;

public interface TableDao {


	/**
	 * Returns a table with the passed id.
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
	 * Creates a Table.
	 * @param name Name of the table.
	 * @param ts Status of the table.
	 * @param order Tables order.
	 * @throws TableWithNullOrderException when a order not in the DB or null is passed.
	 * @return The created table.
	 */
	Table create(String name, TableStatus ts, Order order) throws TableWithNullOrderException;
}
