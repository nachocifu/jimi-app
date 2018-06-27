package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.StatsService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StatsServiceImpl implements StatsService {
	
	private final int infBound = 50;
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private TableService tableService;

	@Autowired
	@Qualifier(value = "userOrderService")
	private OrderService orderService;
	
	public int getBusyTablesUnits() {
		return getNumberOfTablesWithState(TableStatus.BUSY);
	}
	
	public int getBusyTables() {
		return (int) ((getBusyTablesUnits() * 100.0) / tableService.findAll().size());
	}
	
	public int getFreeTablesUnits() {
		return getNumberOfTablesWithState(TableStatus.FREE);
	}
	
	public int getFreeTables() {
		return (int) ((getFreeTablesUnits() * 100.0) / tableService.findAll().size());
	}

	public int getPayingTablesUnits() {
		return getNumberOfTablesWithState(TableStatus.PAYING);
	}

	public int getPayingTables() {
		return (int) ((getPayingTablesUnits() * 100.0) / tableService.findAll().size());
	}
	
	public int getStockState() {
		int underBound = 0;
		for (Dish d : dishService.findAll()) {
			if (d.getStock() <= infBound)
				underBound += 1;
		}
		return (int) ((underBound * 100.0) / dishService.findAll().size());
	}

	public Map getMonthlyOrderTotal() {
		return orderService.getMonthlyOrderTotal();
	}

	private int getNumberOfTablesWithState(TableStatus status) {

		int count = 0;

		//TODO: Hacer una llamada a SQL solo para esto.
		for (Table t : tableService.findAll()) {
			if (t.getStatus() == status)
				count += 1;
		}

		return count;
	}
	
	
}
