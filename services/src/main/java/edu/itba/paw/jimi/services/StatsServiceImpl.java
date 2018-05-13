package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.services.StatsService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.DishStatus;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StatsServiceImpl implements StatsService {

    private final int infBound = 50;

    private TableStatus tableStatus;

    private DishStatus dishStatus;

    @Autowired
    private DishServiceImpl dishService;

    @Autowired
    private TableServiceImpl tableService;

    @Autowired
    private OrderServiceImpl orderService;

    public int getBusyTables(){
        return (int) ((getFreeTablesUnits() * 100.0) / tableService.findAll().size());
    }

    public int getBusyTablesUnits() {
        int busy = 0;
        for (Table t : tableService.findAll()) {
            if(t.getStatus() == tableStatus.Busy)
                busy += 1;
        }
        return busy;
    }

    public int getFreeTablesUnits() {
        int free = 0;
        for (Table t : tableService.findAll()) {
            if(t.getStatus() == tableStatus.Free)
                free += 1;
        }
        return free;
    }

    public int getFreeTables() {
        return (int) ((getBusyTablesUnits() * 100.0) / tableService.findAll().size());
    }

    public int getStockState() {
        int underBound = 0;
        for(Dish d : dishService.findAll()){
            if(d.getStock() <= infBound)
                underBound += 1;
        }
        return (int) ((underBound * 100.0) / dishService.findAll().size()) ;
    }

    public int getDinersToday() {
        return 0;
    }

    public int getDishesSold() {
        return 0;
    }

}
