package edu.itba.paw.jimi.interfaces.services;

import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;

import java.util.Collection;

public interface TableService {
	
	Table findById(long id);

	Table create(String name);
	
	/**
	 * Returns all the tables.
	 *
	 * @return all the tables.
	 */
	Collection<Table> findAll();

	int setDiners(Table table, int diners);
}
