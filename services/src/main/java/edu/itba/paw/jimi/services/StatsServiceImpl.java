package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.StatsService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StatsServiceImpl implements StatsService {

	private static final int infBound = 50;

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
	public int getStockState() {
		int underBound = 0;
		for (Dish d : dishService.findAll()) {
			if (d.getStock() <= infBound)
				underBound += 1;
		}
		return (int) ((underBound * 100.0) / dishService.getTotalDishes());
	}

	@Override
	public Map getMonthlyOrderTotal() {
		return orderService.getMonthlyOrderTotal();
	}

	@Override
	public Map getMonthlyOrderCancelled() {
		return orderService.getMonthlyOrderCancelled();
	}

	@Override
	public int getDiscontinuedDishes() {
		return dishService.getDiscontinuedDishes();
	}

}
