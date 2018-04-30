package edu.itba.paw.jimi.interfaces;

import edu.itba.paw.jimi.models.Table;

import java.util.Collection;

public interface TableDao {
	
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
}
