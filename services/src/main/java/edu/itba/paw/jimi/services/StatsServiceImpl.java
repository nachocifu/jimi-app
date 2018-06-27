package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.StatsService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import edu.itba.paw.jimi.models.Utilities.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static jdk.nashorn.internal.objects.NativeMath.round;

@Service
public class StatsServiceImpl implements StatsService {
	
	private final int infBound = 50;
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private TableService tableService;

	@Autowired
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

		for (Table t : tableService.findAll()) {
			if (t.getStatus() == status)
				count += 1;
		}

		return count;
	}


	public Double average() {
		Collection<Order> orders = orderService.findAll(new QueryParams("id", false));
		long totalSec = 0;
		int number = 0;
		Double total = 0.0;
		DecimalFormat df = new DecimalFormat("#.##");

		for(Order o : orders){
			if(o.getClosedAt() != null && o.getOpenedAt() != null){
				totalSec += (o.getClosedAt().getTime() - o.getOpenedAt().getTime());
				number++;
			}
		}
		if(number > 0)
			total =  (totalSec / number) / 60000.0;

		return Double.valueOf(df.format(total));
	}
	
}
