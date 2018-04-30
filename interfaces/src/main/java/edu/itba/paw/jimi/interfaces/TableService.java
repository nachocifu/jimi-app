package edu.itba.paw.jimi.interfaces;

import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Table;

import java.util.Collection;

public interface TableService {
	
	Table findById(long id);
	
	/**
	 * Adds dish to specified table.
	 * @param table The table to have the new dish.
	 * @param dish The dish to be added.
	 * @return Booleand if success or failure.
	 */
	Boolean addDish(Table table, Dish dish);
	
	/**
	 * Removes dish to specified table.
	 * @param table The table to have the dish removed.
	 * @param dish The dish to be removed.
	 * @return Booleand if success or failure.
	 */
	Boolean removeDish(Table table, Dish dish);
	
	/**
	 * Returns all the tables.
	 *
	 * @return all the tables.
	 */
	Collection<Table> findAll();
}
