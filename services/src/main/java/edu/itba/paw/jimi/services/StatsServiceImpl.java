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
	
	@Override
	public int getBusyTablesUnits() {
		return getNumberOfTablesWithState(TableStatus.BUSY);
	}
	
	@Override
	public int getBusyTables() {
		return (int) ((getBusyTablesUnits() * 100.0) / tableService.findAll().size());
	}
	
	@Override
	public int getFreeTablesUnits() {
		return getNumberOfTablesWithState(TableStatus.FREE);
	}
	
	@Override
	public int getFreeTables() {
		return (int) ((getFreeTablesUnits() * 100.0) / tableService.findAll().size());
	}
	
	@Override
	public int getPayingTablesUnits() {
		return getNumberOfTablesWithState(TableStatus.PAYING);
	}
	
	@Override
	public int getPayingTables() {
		return (int) ((getPayingTablesUnits() * 100.0) / tableService.findAll().size());
	}
	
	@Override
	public int getStockState() {
		int underBound = 0;
		for (Dish d : dishService.findAll()) {
			if (d.getStock() <= infBound)
				underBound += 1;
		}
		return (int) ((underBound * 100.0) / dishService.findAll().size());
	}
	
	@Override
	public Map getMonthlyOrderTotal() {
		return orderService.getMonthlyOrderTotal();
	}
	
	@Override
	public Map getMonthlyOrderCancelled() {
		return orderService.getMonthlyOrderCancelled();
	}
	
	private int getNumberOfTablesWithState(TableStatus status) {
		
		int count = 0;
		
		for (Table t : tableService.findAll()) {
			if (t.getStatus() == status)
				count += 1;
		}
		
		return count;
	}
	
}
