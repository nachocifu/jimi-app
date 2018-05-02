package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.OrderDao;
import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableDao tableDao;

    @Autowired
    private OrderDao orderDao;

    public Table findById(long id) {
        return tableDao.findById(id);
    }

    public Table create(String name) {
        Order order = orderDao.create();
        return tableDao.create(name, TableStatus.Free, order, 0);
    }

    public Collection<Table> findAll() {
        return tableDao.findAll();
    }

    public int setDiners(Table table, int diners) {
        Table t = tableDao.findById(table.getId());
        if (diners >= 0){
            t.setDiners(diners);
            table.setDiners(diners);
            tableDao.update(t);
            return diners;
        }
        return 0;
    }

    public void changeStatus(Table table, TableStatus status) {
        Table t = tableDao.findById(table.getId());
        table.setStatus(status);
        t.setStatus(status);
        tableDao.update(t);
    }
}
