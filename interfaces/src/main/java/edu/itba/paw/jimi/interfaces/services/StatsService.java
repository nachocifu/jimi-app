package edu.itba.paw.jimi.interfaces.services;

import java.util.Map;

public interface StatsService {

	/**
	 * Calculates percentage of free tables.
	 *
	 * @return percentage.
	 */
	int getFreeTables();

	/**
	 * Calculates percentage of busy tables.
	 *
	 * @return percentage.
	 */
	int getBusyTables();

	/**
	 * Calculates percentage of paying tables.
	 *
	 * @return percentage.
	 */
	int getPayingTables();

	/**
	 * Calculates number of busy tables.
	 *
	 * @return percentage.
	 */
	int getBusyTablesUnits();

	/**
	 * Calculates number of free tables.
	 *
	 * @return percentage.
	 */
	int getFreeTablesUnits();

	/**
	 * Calculates number of paying tables.
	 *
	 * @return percentage.
	 */
	int getPayingTablesUnits();

	/**
	 * Calculates percentage of dishes under limit.
	 *
	 * @return percentage.
	 */
	int getStockState(int limit);

	/**
	 * Calculates historic total for orders of a month.
	 *
	 * @return map of month to total.
	 */
	Map getMonthlyOrderTotal();

	/**
	 * Calculates cancelled orders of a month.
	 *
	 * @return map of month to number of cancelled orders.
	 */
	Map getMonthlyOrderCancelled();

	/**
	 * Calculates discontinued dishes.
	 *
	 * @return count of said dishes.
	 */
	int getDiscontinuedDishes();

}
