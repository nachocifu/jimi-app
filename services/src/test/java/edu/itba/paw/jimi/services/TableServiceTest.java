package edu.itba.paw.jimi.services;


import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.exceptions.DinersSetOnNotBusyTableException;
import edu.itba.paw.jimi.interfaces.exceptions.DinersSetOnNotOpenOrderException;
import edu.itba.paw.jimi.interfaces.exceptions.OrderStatusException;
import edu.itba.paw.jimi.interfaces.exceptions.TableStatusTransitionInvalid;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TableServiceTestConfig.class)
public class TableServiceTest {

    private static final String TABLE_NAME = "Table 1";
    @InjectMocks
    @Autowired
    private TableService tableService;

    @Autowired
    @Mock
    private TableDao tableDao;

    @Autowired
    @Mock
    private OrderService orderService;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void createTest() {

        // Mockito mocking
        Order order = new Order(1, null, null, OrderStatus.INACTIVE);

        Mockito.when(orderService.create(OrderStatus.INACTIVE, null, null)).thenReturn(order);
        Mockito.when(tableDao.create(TABLE_NAME, TableStatus.Free, order, 0)).thenReturn(new Table(TABLE_NAME, 1, TableStatus.Free, order, 0));
        // Mockito mocking

        Table table = tableService.create(TABLE_NAME);
        assertEquals(TABLE_NAME, table.getName());
        assertEquals(TableStatus.Free, table.getStatus());
        assertEquals(order.getId(), table.getOrder().getId());
        assertEquals(OrderStatus.INACTIVE, order.getStatus());
        assertEquals(0, table.getDiners());
    }

    @Test
    public void setDinersTest(){
        Order order = new Order(1, null, null, OrderStatus.OPEN);
        Table table = new Table(TABLE_NAME, 1, TableStatus.Busy, order, 0);

        // Mockito mocking
        Mockito.when(tableDao.findById(1)).thenReturn(table);
        // Mockito mocking

        tableService.setDiners(table, 5);

        assertEquals(5, table.getDiners());
    }

    @Test(expected = DinersSetOnNotBusyTableException.class)
    public void setDinersOnNotBusyTableTest(){
        Order order = new Order(1, null, null, OrderStatus.OPEN);
        Table table = new Table(TABLE_NAME, 1, TableStatus.Free, order, 0);

        // Mockito mocking
        Mockito.when(tableDao.findById(1)).thenReturn(table);
        // Mockito mocking

        tableService.setDiners(table, 5);
    }

    @Test(expected = DinersSetOnNotOpenOrderException.class)
    public void setDinersOnNotOpenOrderTest(){
        Order order = new Order(1, null, null, OrderStatus.INACTIVE);
        Table table = new Table(TABLE_NAME, 1, TableStatus.Busy, order, 0);

        // Mockito mocking
        Mockito.when(tableDao.findById(1)).thenReturn(table);
        // Mockito mocking

        tableService.setDiners(table, 5);
    }


    @Test
    public void setDinersNegativeTest(){
        Order order = new Order(1, null, null, OrderStatus.OPEN);
        Table table = new Table(TABLE_NAME, 1, TableStatus.Busy, order, 0);

        // Mockito mocking
        Mockito.when(tableDao.findById(1)).thenReturn(table);
        // Mockito mocking

        tableService.setDiners(table, -5);

        assertEquals(0, table.getDiners());
    }


    @Test
    public void setStatusFromFreeToBusyTest(){
        Order order = new Order(1, null, null, OrderStatus.INACTIVE);
        Table table = new Table(TABLE_NAME, 1, TableStatus.Free, order, 0);

        // Mockito mocking
        Mockito.when(tableDao.findById(1)).thenReturn(table);
        // Mockito mocking

        tableService.changeStatus(table, TableStatus.Busy);

        assertEquals(TableStatus.Busy, table.getStatus());
    }

    @Test(expected = TableStatusTransitionInvalid.class)
    public void setStatusFromFreeToNOTBusyTest(){
        Order order = new Order(1, null, null, OrderStatus.INACTIVE);
        Table table = new Table(TABLE_NAME, 1, TableStatus.Free, order, 0);

        // Mockito mocking
        Mockito.when(tableDao.findById(1)).thenReturn(table);
        // Mockito mocking

        tableService.changeStatus(table, TableStatus.Free);

    }

    @Test
    public void setStatusFromBusyToCleaningTest(){
        Order order = new Order(1, null, null, OrderStatus.OPEN);
        Table table = new Table(TABLE_NAME, 1, TableStatus.Busy, order, 0);

        // Mockito mocking
        Mockito.when(tableDao.findById(1)).thenReturn(table);
        // Mockito mocking

        tableService.changeStatus(table, TableStatus.CleaningRequired);

        assertEquals(TableStatus.CleaningRequired, table.getStatus());
    }

    @Test(expected = TableStatusTransitionInvalid.class)
    public void setStatusFromBusyToNOTCleaningTest(){
        Order order = new Order(1, null, null, OrderStatus.INACTIVE);
        Table table = new Table(TABLE_NAME, 1, TableStatus.Busy, order, 0);

        // Mockito mocking
        Mockito.when(tableDao.findById(1)).thenReturn(table);
        // Mockito mocking

        tableService.changeStatus(table, TableStatus.Free);

    }

    @Test
    public void setStatusFromCleaningToFreeTest(){
        Order order = new Order(1, null, null, OrderStatus.INACTIVE);
        Table table = new Table(TABLE_NAME, 1, TableStatus.CleaningRequired, order, 0);

        // Mockito mocking
        Mockito.when(tableDao.findById(1)).thenReturn(table);
        // Mockito mocking

        tableService.changeStatus(table, TableStatus.Free);

        assertEquals(TableStatus.Free, table.getStatus());
    }

    @Test(expected = TableStatusTransitionInvalid.class)
    public void setStatusFromCleaningToNOTFreeTest(){
        Order order = new Order(1, null, null, OrderStatus.INACTIVE);
        Table table = new Table(TABLE_NAME, 1, TableStatus.CleaningRequired, order, 0);

        // Mockito mocking
        Mockito.when(tableDao.findById(1)).thenReturn(table);
        // Mockito mocking

        tableService.changeStatus(table, TableStatus.Busy);
    }
}
