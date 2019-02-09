package edu.itba.paw.jimi.webapp.dto;

import java.time.YearMonth;
import java.util.Map;

public class StatsDTO {

	private int totalAmountOfFreeTables;
	private int totalAmountOfBusyTables;
	private int totalAmountOfPayingTables;
	private int totalAmountOfTables;
	private int freeTablesPercentage;
	private int stockStatePercentage;
	private Map<YearMonth, Double> monthOrderTotals;
	private Map<YearMonth, Integer> monthlyOrdersCancelled;

	public StatsDTO() {
	}

	public StatsDTO(int totalAmountOfFreeTables, int totalAmountOfBusyTables, int totalAmountOfPayingTables,
	                int totalAmountOfTables, int freeTablesPercentage, int stockStatePercentage,
	                Map<YearMonth, Double> monthOrderTotals, Map<YearMonth, Integer> monthlyOrdersCancelled) {
		this.totalAmountOfFreeTables = totalAmountOfFreeTables;
		this.totalAmountOfBusyTables = totalAmountOfBusyTables;
		this.totalAmountOfPayingTables = totalAmountOfPayingTables;
		this.totalAmountOfTables = totalAmountOfTables;
		this.freeTablesPercentage = freeTablesPercentage;
		this.stockStatePercentage = stockStatePercentage;
		this.monthOrderTotals = monthOrderTotals;
		this.monthlyOrdersCancelled = monthlyOrdersCancelled;
	}

	public int getTotalAmountOfFreeTables() {
		return totalAmountOfFreeTables;
	}

	public void setTotalAmountOfFreeTables(int totalAmountOfFreeTables) {
		this.totalAmountOfFreeTables = totalAmountOfFreeTables;
	}

	public int getTotalAmountOfBusyTables() {
		return totalAmountOfBusyTables;
	}

	public void setTotalAmountOfBusyTables(int totalAmountOfBusyTables) {
		this.totalAmountOfBusyTables = totalAmountOfBusyTables;
	}

	public int getTotalAmountOfPayingTables() {
		return totalAmountOfPayingTables;
	}

	public void setTotalAmountOfPayingTables(int totalAmountOfPayingTables) {
		this.totalAmountOfPayingTables = totalAmountOfPayingTables;
	}

	public int getTotalAmountOfTables() {
		return totalAmountOfTables;
	}

	public void setTotalAmountOfTables(int totalAmountOfTables) {
		this.totalAmountOfTables = totalAmountOfTables;
	}

	public int getFreeTablesPercentage() {
		return freeTablesPercentage;
	}

	public void setFreeTablesPercentage(int freeTablesPercentage) {
		this.freeTablesPercentage = freeTablesPercentage;
	}

	public int getStockStatePercentage() {
		return stockStatePercentage;
	}

	public void setStockStatePercentage(int stockStatePercentage) {
		this.stockStatePercentage = stockStatePercentage;
	}

	public Map<YearMonth, Double> getMonthOrderTotals() {
		return monthOrderTotals;
	}

	public void setMonthOrderTotals(Map<YearMonth, Double> monthOrderTotals) {
		this.monthOrderTotals = monthOrderTotals;
	}

	public Map<YearMonth, Integer> getMonthlyOrdersCancelled() {
		return monthlyOrdersCancelled;
	}

	public void setMonthlyOrdersCancelled(Map<YearMonth, Integer> monthlyOrdersCancelled) {
		this.monthlyOrdersCancelled = monthlyOrdersCancelled;
	}
}
