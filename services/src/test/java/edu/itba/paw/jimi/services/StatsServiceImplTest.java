package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.TableStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;

@RunWith(MockitoJUnitRunner.class)
public class StatsServiceImplTest {
	
	private static final int ID = 123;
	private static final String NAME = "Table221";
	
	@InjectMocks
	private StatsServiceImpl statsServiceImpl;
	
	@Mock
	private DishService dishService;
	
	@Mock
	private TableService tableService;
	
	@Test
	public void getBusyTablesUnitsWithMultipleTables() {
		Mockito.when(tableService.getNumberOfTablesWithState(TableStatus.BUSY)).thenReturn(5);
		Assert.assertEquals(5, statsServiceImpl.getBusyTablesUnits());
	}
	
	@Test
	public void getBusyTablesPercentage() {
		int percentageExpected = (int) ((5.0 / 9.0) * 100.0);
		Mockito.when(tableService.getNumberOfTablesWithState(TableStatus.BUSY)).thenReturn(5);
		Mockito.when(tableService.getTotalTables()).thenReturn(9);
		Assert.assertTrue(statsServiceImpl.getBusyTables() >= 0 && statsServiceImpl.getBusyTables() <= 100);
		Assert.assertEquals(percentageExpected, statsServiceImpl.getBusyTables());
	}
	
	@Test
	public void getFreeTablesUnitsWithMultipleTables() {
		Mockito.when(tableService.getNumberOfTablesWithState(TableStatus.FREE)).thenReturn(5);
		Assert.assertEquals(5, statsServiceImpl.getFreeTablesUnits());
	}
	
	@Test
	public void getFreeTablesPercentage() {
		int percentageExpected = (int) ((4.0 / 9.0) * 100.0);
		Mockito.when(tableService.getNumberOfTablesWithState(TableStatus.FREE)).thenReturn(4);
		Mockito.when(tableService.getTotalTables()).thenReturn(9);
		Assert.assertTrue(statsServiceImpl.getFreeTables() >= 0 && statsServiceImpl.getFreeTables() <= 100);
		Assert.assertEquals(percentageExpected, statsServiceImpl.getFreeTables());
	}
	
	@Test
	public void getPayingTablesUnitsWithMultipleTables() {
		Mockito.when(tableService.getNumberOfTablesWithState(TableStatus.PAYING)).thenReturn(5);
		Assert.assertEquals(5, statsServiceImpl.getPayingTablesUnits());
	}
	
	@Test
	public void getPayingTablesPercentage() {
		int percentageExpected = (int) ((4.0 / 9.0) * 100.0);
		Mockito.when(tableService.getNumberOfTablesWithState(TableStatus.PAYING)).thenReturn(4);
		Mockito.when(tableService.getTotalTables()).thenReturn(9);
		Assert.assertTrue(statsServiceImpl.getPayingTables() >= 0 && statsServiceImpl.getPayingTables() <= 100);
		Assert.assertEquals(percentageExpected, statsServiceImpl.getPayingTables());
	}
	
	@Test
	public void getStockStateNoItemsPercentage() {
		Mockito.when(dishService.findAll()).thenReturn(new LinkedList<Dish>());
		
		Assert.assertTrue(statsServiceImpl.getStockState() >= 0
				&& statsServiceImpl.getStockState() <= 100);
		Assert.assertEquals(0, statsServiceImpl.getStockState());
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
		
		Assert.assertTrue(statsServiceImpl.getStockState() >= 0
				&& statsServiceImpl.getStockState() <= 100);
		Assert.assertEquals(100, statsServiceImpl.getStockState());
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
		
		Assert.assertTrue(statsServiceImpl.getStockState() >= 0
				&& statsServiceImpl.getStockState() <= 100);
		Assert.assertEquals(percentageExpected, statsServiceImpl.getStockState());
	}
}
