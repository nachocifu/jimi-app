package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.StatsService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.*;
import org.junit.Assert;
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

import java.sql.Timestamp;
import java.util.LinkedList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StatsServiceTestConfig.class)
public class StatsServiceTest {
	
	private static final int ID = 123;
	private static final String NAME = "Table221";
	private static final Order ORDER = new Order(ID, new Timestamp(10000), new Timestamp(100002), OrderStatus.INACTIVE, 0, 0);
	
	@InjectMocks
	@Autowired
	private StatsService statsService;
	
	@Autowired
	@Mock
	private DishService dishService;
	
	@Autowired
	@Mock
	private TableService tableService;
	
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void getBusyTablesUnitsWithNoTables() {
		Mockito.when(tableService.findAll()).thenReturn(new LinkedList<Table>());
		
		Assert.assertEquals(0, tableService.findAll().size());
		Assert.assertEquals(0, statsService.getBusyTablesUnits());
	}
	
	@Test
	public void getBusyTablesUnitsWithOneTable() {
		LinkedList<Table> list = new LinkedList<Table>();
		list.add(new Table(NAME, ID, TableStatus.BUSY, ORDER));
		Mockito.when(tableService.findAll()).thenReturn(list);
		
		Assert.assertEquals(1, tableService.findAll().size());
		Assert.assertEquals(1, statsService.getBusyTablesUnits());
	}
	
	@Test
	public void getBusyTablesUnitsWithMultipleTables() {
		LinkedList<Table> list = new LinkedList<Table>();
		
		list.add(new Table(NAME, ID, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 1, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 2, TableStatus.FREE, ORDER));
		list.add(new Table(NAME, ID + 3, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 4, TableStatus.FREE, ORDER));
		list.add(new Table(NAME, ID + 5, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 6, TableStatus.FREE, ORDER));
		list.add(new Table(NAME, ID + 7, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 8, TableStatus.FREE, ORDER));
		
		Mockito.when(tableService.findAll()).thenReturn(list);
		
		Assert.assertEquals(9, tableService.findAll().size());
		Assert.assertEquals(5, statsService.getBusyTablesUnits());
	}
	
	@Test
	public void getBusyTablesPercentage() {
		int percentageExpected = (int) ((5.0 / 9.0) * 100.0);
		LinkedList<Table> list = new LinkedList<Table>();
		
		list.add(new Table(NAME, ID, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 1, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 2, TableStatus.FREE, ORDER));
		list.add(new Table(NAME, ID + 3, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 4, TableStatus.FREE, ORDER));
		list.add(new Table(NAME, ID + 5, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 6, TableStatus.FREE, ORDER));
		list.add(new Table(NAME, ID + 7, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 8, TableStatus.FREE, ORDER));
		
		Mockito.when(tableService.findAll()).thenReturn(list);
		
		Assert.assertTrue(statsService.getBusyTables() >= 0
				&& statsService.getBusyTables() <= 100);
		Assert.assertEquals(percentageExpected, statsService.getBusyTables());
	}
	
	@Test
	public void getFreeTablesUnitsWithNoTables() {
		Mockito.when(tableService.findAll()).thenReturn(new LinkedList<Table>());
		
		Assert.assertEquals(0, tableService.findAll().size());
		Assert.assertEquals(0, statsService.getFreeTablesUnits());
	}
	
	@Test
	public void getFreeTablesUnitsWithOneTable() {
		LinkedList<Table> list = new LinkedList<Table>();
		list.add(new Table(NAME, ID, TableStatus.FREE, ORDER));
		Mockito.when(tableService.findAll()).thenReturn(list);
		
		Assert.assertEquals(1, tableService.findAll().size());
		Assert.assertEquals(1, statsService.getFreeTablesUnits());
	}
	
	@Test
	public void getFreeTablesUnitsWithMultipleTables() {
		LinkedList<Table> list = new LinkedList<Table>();
		
		list.add(new Table(NAME, ID, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 1, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 2, TableStatus.FREE, ORDER));
		list.add(new Table(NAME, ID + 3, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 4, TableStatus.FREE, ORDER));
		list.add(new Table(NAME, ID + 5, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 6, TableStatus.FREE, ORDER));
		list.add(new Table(NAME, ID + 7, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 8, TableStatus.FREE, ORDER));
		
		Mockito.when(tableService.findAll()).thenReturn(list);
		
		Assert.assertEquals(9, tableService.findAll().size());
		Assert.assertEquals(4, statsService.getFreeTablesUnits());
	}
	
	@Test
	public void getFreeTablesPercentage() {
		int percentageExpected = (int) ((4.0 / 9.0) * 100.0);
		LinkedList<Table> list = new LinkedList<Table>();
		
		list.add(new Table(NAME, ID, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 1, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 2, TableStatus.FREE, ORDER));
		list.add(new Table(NAME, ID + 3, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 4, TableStatus.FREE, ORDER));
		list.add(new Table(NAME, ID + 5, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 6, TableStatus.FREE, ORDER));
		list.add(new Table(NAME, ID + 7, TableStatus.BUSY, ORDER));
		list.add(new Table(NAME, ID + 8, TableStatus.FREE, ORDER));
		
		Mockito.when(tableService.findAll()).thenReturn(list);
		
		Assert.assertTrue(statsService.getFreeTables() >= 0
				&& statsService.getFreeTables() <= 100);
		Assert.assertEquals(percentageExpected, statsService.getFreeTables());
	}

    @Test
    public void getPayingTablesUnitsWithNoTables() {
        Mockito.when(tableService.findAll()).thenReturn(new LinkedList<Table>());

        Assert.assertEquals(0, tableService.findAll().size());
        Assert.assertEquals(0, statsService.getPayingTablesUnits());
    }

    @Test
    public void getPayingTablesUnitsWithOneTable() {
        LinkedList<Table> list = new LinkedList<Table>();
        list.add(new Table(NAME, ID, TableStatus.PAYING, ORDER));
        Mockito.when(tableService.findAll()).thenReturn(list);

        Assert.assertEquals(1, tableService.findAll().size());
        Assert.assertEquals(1, statsService.getPayingTablesUnits());
    }

    @Test
    public void getPayingTablesUnitsWithMultipleTables() {
        LinkedList<Table> list = new LinkedList<Table>();

        list.add(new Table(NAME, ID, TableStatus.BUSY, ORDER));
        list.add(new Table(NAME, ID + 1, TableStatus.BUSY, ORDER));
        list.add(new Table(NAME, ID + 2, TableStatus.PAYING, ORDER));
        list.add(new Table(NAME, ID + 3, TableStatus.BUSY, ORDER));
        list.add(new Table(NAME, ID + 4, TableStatus.PAYING, ORDER));
        list.add(new Table(NAME, ID + 5, TableStatus.BUSY, ORDER));
        list.add(new Table(NAME, ID + 6, TableStatus.PAYING, ORDER));
        list.add(new Table(NAME, ID + 7, TableStatus.BUSY, ORDER));
        list.add(new Table(NAME, ID + 8, TableStatus.PAYING, ORDER));

        Mockito.when(tableService.findAll()).thenReturn(list);

        Assert.assertEquals(9, tableService.findAll().size());
        Assert.assertEquals(4, statsService.getPayingTablesUnits());
    }

    @Test
    public void getPayingTablesPercentage() {
        int percentageExpected = (int) ((4.0 / 9.0) * 100.0);
        LinkedList<Table> list = new LinkedList<Table>();

        list.add(new Table(NAME, ID, TableStatus.BUSY, ORDER));
        list.add(new Table(NAME, ID + 1, TableStatus.BUSY, ORDER));
        list.add(new Table(NAME, ID + 2, TableStatus.PAYING, ORDER));
        list.add(new Table(NAME, ID + 3, TableStatus.BUSY, ORDER));
        list.add(new Table(NAME, ID + 4, TableStatus.PAYING, ORDER));
        list.add(new Table(NAME, ID + 5, TableStatus.BUSY, ORDER));
        list.add(new Table(NAME, ID + 6, TableStatus.PAYING, ORDER));
        list.add(new Table(NAME, ID + 7, TableStatus.BUSY, ORDER));
        list.add(new Table(NAME, ID + 8, TableStatus.PAYING, ORDER));

        Mockito.when(tableService.findAll()).thenReturn(list);

        Assert.assertTrue(statsService.getPayingTables() >= 0
                && statsService.getPayingTables() <= 100);
        Assert.assertEquals(percentageExpected, statsService.getPayingTables());
    }
	
	@Test
	public void getStockStateNoItemsPercentage() {
		Mockito.when(dishService.findAll()).thenReturn(new LinkedList<Dish>());
		
		Assert.assertTrue(statsService.getStockState() >= 0
				&& statsService.getStockState() <= 100);
		Assert.assertEquals(0, statsService.getStockState());
	}
	
	@Test
	public void getStockStateMultipleItemsPercentage() {
		LinkedList<Dish> list = new LinkedList<Dish>();
		
		list.add(new Dish(NAME, (float) 0.0, ID, 20));
		list.add(new Dish(NAME + "a", (float) 0.0, ID + 1, 20));
		list.add(new Dish(NAME + "b", (float) 0.0, ID + 2, 20));
		list.add(new Dish(NAME + "c", (float) 0.0, ID + 3, 20));
		list.add(new Dish(NAME + "d", (float) 0.0, ID + 4, 20));
		
		Mockito.when(dishService.findAll()).thenReturn(list);
		
		Assert.assertTrue(statsService.getStockState() >= 0
				&& statsService.getStockState() <= 100);
		Assert.assertEquals(100, statsService.getStockState());
	}
	
	@Test
	public void getStockStateMultipleItems2Percentage() {
		LinkedList<Dish> list = new LinkedList<Dish>();
		int percentageExpected = (int) ((3.0 / 5.0) * 100.0);
		
		list.add(new Dish(NAME, (float) 0.0, ID, 20));
		list.add(new Dish(NAME + "a", (float) 0.0, ID + 1, 20));
		list.add(new Dish(NAME + "b", (float) 0.0, ID + 2, 60));
		list.add(new Dish(NAME + "c", (float) 0.0, ID + 3, 60));
		list.add(new Dish(NAME + "d", (float) 0.0, ID + 4, 20));
		
		Mockito.when(dishService.findAll()).thenReturn(list);
		
		Assert.assertTrue(statsService.getStockState() >= 0
				&& statsService.getStockState() <= 100);
		Assert.assertEquals(percentageExpected, statsService.getStockState());
	}
}
