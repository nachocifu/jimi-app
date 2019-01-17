package edu.itba.paw.jimi.services;


import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.exceptions.TableStatusTransitionInvalid;
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
	@Qualifier(value = "adminOrderService")
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
	
	@Test(expected = TableStatusTransitionInvalid.class)
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
	
	@Test(expected = TableStatusTransitionInvalid.class)
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
	
	@Test(expected = TableStatusTransitionInvalid.class)
	public void setStatusFromCleaningToNOTFreeTest() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.PAYING, order);
		
		// Mockito mocking
		Mockito.when(tableDao.findById(1)).thenReturn(table);
		// Mockito mocking
		
		tableServiceImpl.changeStatus(table, TableStatus.BUSY);
	}
	
	@Test
	public void findAllNotNullEmpty() {
		Mockito.when(tableServiceImpl.findAll()).thenReturn(new LinkedList<Table>());
		Assert.assertNotNull(tableServiceImpl.findAll());
	}
	
	@Test
	public void findAllNotNull() {
		Mockito.when(tableServiceImpl.findAll()).thenReturn(null);
		Assert.assertNotNull(tableServiceImpl.findAll());
	}
	
	
}
