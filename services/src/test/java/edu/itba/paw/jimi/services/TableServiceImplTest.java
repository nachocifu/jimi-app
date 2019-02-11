package edu.itba.paw.jimi.services;


import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.exceptions.FreeTableDeletionAttemptException;
import edu.itba.paw.jimi.interfaces.exceptions.TableStatusInvalidTransitionException;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class TableServiceImplTest {

	private static final String TABLE_NAME = "Table 1";
	private static final int offset = 0;

	@InjectMocks
	private TableServiceImpl tableServiceImpl;

	@Mock
	private TableDao tableDao;

	@Mock
	private OrderService orderService;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createTest() {
		// Mockito mocking
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);

		Mockito.when(orderService.create(OrderStatus.INACTIVE, null, null, 0)).thenReturn(order);
		Mockito.when(tableDao.create(TABLE_NAME, TableStatus.FREE, order)).thenReturn(new Table(TABLE_NAME, 1, TableStatus.FREE, order));
		// Mockito mocking

		Table table = tableServiceImpl.create(TABLE_NAME);
		assertEquals(TABLE_NAME, table.getName());
		assertEquals(TableStatus.FREE, table.getStatus());
		assertEquals(order.getId(), table.getOrder().getId());
		assertEquals(OrderStatus.INACTIVE, order.getStatus());
		assertEquals(0, table.getOrder().getDiners());
	}

	@Test
	public void setStatusFromFreeToBusyTest() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.FREE, order);

		// Mockito mocking
		Mockito.when(tableDao.findById(1)).thenReturn(table);
		// Mockito mocking

		tableServiceImpl.changeStatus(table, TableStatus.BUSY);

		assertEquals(TableStatus.BUSY, table.getStatus());
	}

	@Test(expected = TableStatusInvalidTransitionException.class)
	public void setStatusFromFreeToNOTBusyTest() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.FREE, order);

		// Mockito mocking
		Mockito.when(tableDao.findById(1)).thenReturn(table);
		// Mockito mocking

		tableServiceImpl.changeStatus(table, TableStatus.FREE);

	}

	@Test
	public void setStatusFromBusyToCleaningTest() {
		Order order = new Order(1, null, null, OrderStatus.OPEN, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.BUSY, order);

		// Mockito mocking
		Mockito.when(tableDao.findById(1)).thenReturn(table);
		// Mockito mocking

		tableServiceImpl.changeStatus(table, TableStatus.PAYING);

		assertEquals(TableStatus.PAYING, table.getStatus());
	}

	@Test
	public void setStatusFromBusyToFreeCANCELEDTest() {
		Order order = new Order(1, null, null, OrderStatus.OPEN, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.BUSY, order);

		// Mockito mocking
		Mockito.when(tableDao.findById(1)).thenReturn(table);
		// Mockito mocking

		tableServiceImpl.changeStatus(table, TableStatus.FREE);

		assertEquals(TableStatus.FREE, table.getStatus());
	}

	@Test(expected = TableStatusInvalidTransitionException.class)
	public void setStatusFromBusyToNOTCleaningORFreeTest() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.BUSY, order);

		// Mockito mocking
		Mockito.when(tableDao.findById(1)).thenReturn(table);
		// Mockito mocking

		tableServiceImpl.changeStatus(table, TableStatus.BUSY);

	}

	@Test
	public void setStatusFromCleaningToFreeTest() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.PAYING, order);

		// Mockito mocking
		Mockito.when(tableDao.findById(1)).thenReturn(table);
		// Mockito mocking

		tableServiceImpl.changeStatus(table, TableStatus.FREE);

		assertEquals(TableStatus.FREE, table.getStatus());
	}

	@Test(expected = TableStatusInvalidTransitionException.class)
	public void setStatusFromCleaningToNOTFreeTest() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.PAYING, order);

		// Mockito mocking
		Mockito.when(tableDao.findById(1)).thenReturn(table);
		// Mockito mocking

		tableServiceImpl.changeStatus(table, TableStatus.BUSY);
	}

	@Test
	public void findAllNotNullEmptyTest() {
		Mockito.when(tableServiceImpl.findAll(any(Integer.class), any(Integer.class))).thenReturn(new LinkedList<Table>());
		assertEquals(0, tableServiceImpl.findAll(100, offset).size());
	}

	@Test
	public void findAllNotNullTest() {
		Mockito.when(tableServiceImpl.findAll(any(Integer.class), any(Integer.class))).thenReturn(null);
		Assert.assertNotNull(tableServiceImpl.findAll(100, offset));
	}

	@Test
	public void setNameTest() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.FREE, order);
		tableServiceImpl.setName(table, TABLE_NAME.concat(TABLE_NAME));
		assertEquals(TABLE_NAME.concat(TABLE_NAME), table.getName());
	}

	@Test
	public void getTablesWithOrdersFromLast30MinutesTest() {
		Order urgentOrder1 = new Order(1, null, null, OrderStatus.OPEN, 0, 0);
		Order urgentOrder2 = new Order(2, null, null, OrderStatus.OPEN, 0, 0);
		Table busyTable1 = new Table(TABLE_NAME, 1, TableStatus.BUSY, urgentOrder1);
		Table busyTable2 = new Table(TABLE_NAME, 2, TableStatus.BUSY, urgentOrder2);
		Collection<Table> expectedUrgentTables = new LinkedList<>();
		expectedUrgentTables.add(busyTable1);
		expectedUrgentTables.add(busyTable2);
		Mockito.when(tableServiceImpl.getTablesWithOrdersFromLastMinutes(30)).thenReturn(expectedUrgentTables);
		assertEquals(expectedUrgentTables, tableServiceImpl.getTablesWithOrdersFromLastMinutes(30));
	}

	@Test
	public void getTablesWithOrdersFromLastInvalidMinutesTest() {
		assertEquals(0, tableServiceImpl.getTablesWithOrdersFromLastMinutes(-1).size());
	}

	@Test
	public void getBusyTablesWithOrdersOrderedByOrderedAtTest() {
		Dish testDish = new Dish("dish name", 1.0F, 10);
		Order firstOrder = new Order(1, null, null, OrderStatus.OPEN, 0, 0);
		Order secondOrder = new Order(2, null, null, OrderStatus.OPEN, 0, 0);
		firstOrder.setUndoneDish(testDish, 1);
		firstOrder.getUnDoneDishes().get(testDish).setOrderedAt(new Timestamp(1525467178));
		secondOrder.setUndoneDish(testDish, 1);
		secondOrder.getUnDoneDishes().get(testDish).setOrderedAt(new Timestamp(1525467180));
		Table busyTable1 = new Table(TABLE_NAME, 1, TableStatus.BUSY, firstOrder);
		Table busyTable2 = new Table(TABLE_NAME, 2, TableStatus.BUSY, secondOrder);
		Collection<Table> expectedBusyTables = new LinkedList<>();
		expectedBusyTables.add(busyTable1);
		expectedBusyTables.add(busyTable2);
		Mockito.when(tableServiceImpl.getBusyTablesWithOrdersOrderedByOrderedAt(expectedBusyTables.size(), 0)).thenReturn(expectedBusyTables);
		assertEquals(expectedBusyTables, tableServiceImpl.getBusyTablesWithOrdersOrderedByOrderedAt(expectedBusyTables.size(), 0));
	}

	@Test
	public void getBusyTablesWithOrdersOrderedByOrderedAtInvalidTest() {
		Mockito.when(tableServiceImpl.getBusyTablesWithOrdersOrderedByOrderedAt(any(Integer.class), any(Integer.class))).thenReturn(new HashSet<>());
		assertEquals(new HashSet<>(), tableServiceImpl.getBusyTablesWithOrdersOrderedByOrderedAt(any(Integer.class), any(Integer.class)));
	}

	@Test(expected = FreeTableDeletionAttemptException.class)
	public void deleteBusyTable() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.BUSY, order);
		Mockito.when(tableDao.findById(1)).thenReturn(table);
		tableServiceImpl.delete(1);
	}

	@Test(expected = FreeTableDeletionAttemptException.class)
	public void deletePayingTable() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.PAYING, order);
		Mockito.when(tableDao.findById(1)).thenReturn(table);
		tableServiceImpl.delete(1);
	}
}
