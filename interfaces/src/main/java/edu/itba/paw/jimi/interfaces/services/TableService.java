package edu.itba.paw.jimi.interfaces.services;

import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;

import java.util.Collection;

public interface TableService {
	
	Table findById(final long id);
	
	/**
	 * Creates a new table with a order.
	 *
	 * @param name
	 * @return
	 */
	Table create(String name);
	
	/**
	 * Returns all the tables.
	 *
	 * @return all the tables.
	 */
	Collection<Table> findAll();
	
	
	/**
	 * Sets the status of the table.
	 *
	 * @param table  The table to modify.
	 * @param status The new status of the table.
	 */
	void changeStatus(Table table, TableStatus status);
	
	//TODO: Implementar.
//	/**
//	 * Closes the order and changes the status.
//	 * A new order object is created with no dishes.
//	 * @param table
//	 */
//	Order closeOrder(Table table);
}
