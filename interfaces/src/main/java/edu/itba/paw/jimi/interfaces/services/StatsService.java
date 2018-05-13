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
     * Calculates number of diners of the last 24hs.
     * @return amount of diners.
     */
    int getDinersToday();

    /**
     * Calculates number of diners sold today.
     * @return number of dishes.
     */
    int getDishesSold();

    /**
     * Calculates percentage of dishes under bounded limit.
     * @return percentage.
     */
    int getStockState();

}
