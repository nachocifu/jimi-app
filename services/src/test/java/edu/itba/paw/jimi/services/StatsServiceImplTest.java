package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.TableStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;

import static org.mockito.Matchers.any;

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
		Mockito.when(dishService.getAllDishesWithStockLessThanLimit(any(Integer.class))).thenReturn(0);
		int limit = new Random().nextInt();
		Assert.assertTrue(statsServiceImpl.getStockState(limit) >= 0
				&& statsServiceImpl.getStockState(limit) <= 100);
		Assert.assertEquals(0, statsServiceImpl.getStockState(limit));
	}

	@Test
	public void getStockStateMultipleItemsPercentage() {
		Mockito.when(dishService.getAllDishesWithStockLessThanLimit(any(Integer.class))).thenReturn(5);
		Mockito.when(dishService.getTotalDishes()).thenReturn(5);
		int limit = new Random().nextInt();
		Assert.assertTrue(statsServiceImpl.getStockState(limit) >= 0
				&& statsServiceImpl.getStockState(limit) <= 100);
		Assert.assertEquals(100, statsServiceImpl.getStockState(limit));
	}

	@Test
	public void getStockStateMultipleItems2Percentage() {
		int percentageExpected = (int) ((3.0 / 5.0) * 100.0);
		Mockito.when(dishService.getAllDishesWithStockLessThanLimit(any(Integer.class))).thenReturn(3);
		Mockito.when(dishService.getTotalDishes()).thenReturn(5);
		int limit = new Random().nextInt();
		Assert.assertTrue(statsServiceImpl.getStockState(limit) >= 0
				&& statsServiceImpl.getStockState(limit) <= 100);
		Assert.assertEquals(percentageExpected, statsServiceImpl.getStockState(limit));
	}
}
