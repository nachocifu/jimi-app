package edu.itba.paw.jimi.interfaces.daos;

import edu.itba.paw.jimi.interfaces.exceptions.TableWithNullOrderException;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;

import java.util.Collection;

public interface TableDao {


	/**
	 * Returns a table with the passed id.
	 * @param id the id to look for.
	 * @return the table with said id.
	 */
	Table findById(long id);
	
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
	 * Creates a Table.
	 * @param name Name of the table.
	 * @param ts Status of the table.
	 * @param order Tables order.
	 * @param diners Number of diners.
	 * @throws TableWithNullOrderException when a order not in the DB or null is passed.
	 * @return The created table.
	 */
	Table create(String name, TableStatus ts, Order order, int diners) throws TableWithNullOrderException;
}
