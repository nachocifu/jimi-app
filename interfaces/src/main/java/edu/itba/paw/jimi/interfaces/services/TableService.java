package edu.itba.paw.jimi.interfaces.services;

import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import org.hibernate.service.spi.ServiceException;

import java.util.Collection;

public interface TableService {

	Table findById(final long id);

	/**
	 * Creates a new table with an order.
	 *
	 * @param name the name of the new table
	 * @return created table
	 */
	Table create(String name) throws ServiceException;

	/**
	 * Returns all the tables paginated
	 *
	 * @return all the tables based
	 */
	Collection<Table> findAll(int pageSize, int offset);

	/**
	 * Returns all the tables with the given status.
	 *
	 * @return all the active with the given status.
	 */
	Collection<Table> findTablesWithStatus(TableStatus tableStatus);

	/**
	 * Returns true if a table exists with tableName.
	 */
	boolean tableNameExists(String tableName);

	/**
	 * Returns total number of tables.
	 */
	int getTotalTables();

	/**
	 * Sets the status of the table.
	 *
	 * @param table  The table to modify.
	 * @param status The new status of the table.
	 */
	void changeStatus(Table table, TableStatus status);

	/**
	 * Gets number of tables with status tableStatus.
	 *
	 * @return umber of tables with status tableStatus.
	 */
	int getNumberOfTablesWithState(TableStatus tableStatus);

	/**
	 * Sets a new name for the table.
	 *
	 * @param table The table to modify.
	 * @param name  The new name of the table.
	 */
	void setName(Table table, String name);

	/**
	 * Returns tables with orders from the last given quantity of minutes.
	 * <p>
	 * If minutes is less than 0, empty collection is returned.
	 */
	Collection<Table> getTablesWithOrdersFromLastMinutes(int minutes);

	/**
	 * Deletes a Table.
	 *
	 * @param id Id of the table.
	 */
	void delete(final long id);
}
