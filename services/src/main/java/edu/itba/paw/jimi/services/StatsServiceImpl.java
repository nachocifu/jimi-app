package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.StatsService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Map;

@Service
public class StatsServiceImpl implements StatsService {

	@Autowired
	private DishService dishService;

	@Autowired
	private TableService tableService;

	@Autowired
	private OrderService orderService;

	@Override
	public int getBusyTablesUnits() {
		return tableService.getNumberOfTablesWithState(TableStatus.BUSY);
	}

	@Override
	public int getBusyTables() {
		return (int) ((getBusyTablesUnits() * 100.0) / tableService.getTotalTables());
	}

	@Override
	public int getFreeTablesUnits() {
		return tableService.getNumberOfTablesWithState(TableStatus.FREE);
	}

	@Override
	public int getFreeTables() {
		return (int) ((getFreeTablesUnits() * 100.0) / tableService.getTotalTables());
	}

	@Override
	public int getPayingTablesUnits() {
		return tableService.getNumberOfTablesWithState(TableStatus.PAYING);
	}

	@Override
	public int getPayingTables() {
		return (int) ((getPayingTablesUnits() * 100.0) / tableService.getTotalTables());
	}

	@Override
	public int getStockState(int limit) {
		return (int) ((dishService.getAllDishesWithStockLessThanLimit(limit) * 100.0) / dishService.getTotalDishes());
	}

	@Override
	public Map<YearMonth, Double> getMonthlyOrderTotal() {
		return orderService.getMonthlyOrderTotal();
	}

	@Override
	public Map<YearMonth, Integer> getMonthlyOrderCancelled() {
		return orderService.getMonthlyOrderCancelled();
	}

	@Override
	public int getDiscontinuedDishes() {
		return dishService.getDiscontinuedDishes();
	}

}
