package edu.itba.paw.jimi.services;


import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.exceptions.FreeTableDeletionAttemptException;
import edu.itba.paw.jimi.interfaces.exceptions.TableStatusInvalidTransitionException;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TableServiceImplTest {
	
	private static final String TABLE_NAME = "Table 1";
	
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
		Mockito.when(tableServiceImpl.findAll()).thenReturn(new LinkedList<Table>());
		Assert.assertNotNull(tableServiceImpl.findAll());
	}
	
	@Test
	public void findAllNotNullTest() {
		Mockito.when(tableServiceImpl.findAll()).thenReturn(null);
		Assert.assertNotNull(tableServiceImpl.findAll());
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
