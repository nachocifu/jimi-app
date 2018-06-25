package edu.itba.paw.jimi.interfaces.services;

public interface StatsService {
    /**
     * Calculates percentage of free tables.
     * @return percentage.
     */
    int getFreeTables();

    /**
     * Calculates percentage of busy tables.
     * @return percentage.
     */
    int getBusyTables();
    
    /**
     * Calculates percentage of paying tables.
     * @return percentage.
     */
    int getPayingTables();

    /**
     * Calculates number of busy tables.
     * @return percentage.
     */
    int getBusyTablesUnits();

    /**
     * Calculates number of free tables.
     * @return percentage.
     */
    int getFreeTablesUnits();
    
    /**
     * Calculates number of paying tables.
     * @return percentage.
     */
    int getPayingTablesUnits();

    /**
     * Calculates percentage of dishes under bounded limit.
     * @return percentage.
     */
    int getStockState();

}
