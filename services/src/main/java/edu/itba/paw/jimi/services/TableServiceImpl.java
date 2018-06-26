package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.exceptions.TableStatusTransitionInvalid;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;

@Service
public class TableServiceImpl implements TableService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableServiceImpl.class);

    @Autowired
    private TableDao tableDao;

    @Autowired
    private OrderService orderService;

    public Table findById(final long id) {
        return tableDao.findById(id);
    }

    @Transactional
    public Table create(String name) {
        Order order = orderService.create(OrderStatus.INACTIVE, null, null, 0);
        LOGGER.info("Created table {}", name);
        return tableDao.create(name, TableStatus.FREE, order);
    }

    public Collection<Table> findAll() {
        Collection<Table> tables = tableDao.findAll();
        if (tables != null)
            return tables;
        else
            return new HashSet<Table>();
    }

    @Transactional
    public void changeStatus(Table table, TableStatus status) {

        if (table.getStatus().equals(TableStatus.BUSY) && !status.equals(TableStatus.PAYING))
            throw new TableStatusTransitionInvalid(TableStatus.PAYING, status);

        if (table.getStatus().equals(TableStatus.PAYING) && !status.equals(TableStatus.FREE))
            throw new TableStatusTransitionInvalid(TableStatus.FREE, status);

        if (table.getStatus().equals(TableStatus.FREE) && !status.equals(TableStatus.BUSY))
            throw new TableStatusTransitionInvalid(TableStatus.BUSY, status);


        switch (status) {
            case BUSY: {
                orderService.open(table.getOrder());
                break;
            }
            case FREE: {
                Order newOrder = orderService.create(OrderStatus.INACTIVE, null, null, 0);
                table.setOrder(newOrder);
                break;
            }
            case PAYING: {
                orderService.close(table.getOrder());
                break;
            }
        }

        table.setStatus(status);
        table.setStatus(status);
        tableDao.update(table);
        LOGGER.info("Updated table {}", table);
    }

}
