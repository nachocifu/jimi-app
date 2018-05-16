package edu.itba.paw.jimi.services;


import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.exceptions.TableStatusTransitionInvalid;
import edu.itba.paw.jimi.interfaces.services.OrderService;
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

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
		
		Table table = tableService.create(TABLE_NAME);
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
		
		tableService.changeStatus(table, TableStatus.BUSY);
		
		assertEquals(TableStatus.BUSY, table.getStatus());
	}
	
	@Test(expected = TableStatusTransitionInvalid.class)
	public void setStatusFromFreeToNOTBusyTest() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.FREE, order);
		
		// Mockito mocking
		Mockito.when(tableDao.findById(1)).thenReturn(table);
		// Mockito mocking
		
		tableService.changeStatus(table, TableStatus.FREE);
		
	}
	
	@Test
	public void setStatusFromBusyToCleaningTest() {
		Order order = new Order(1, null, null, OrderStatus.OPEN, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.BUSY, order);
		
		// Mockito mocking
		Mockito.when(tableDao.findById(1)).thenReturn(table);
		// Mockito mocking
		
		tableService.changeStatus(table, TableStatus.PAYING);
		
		assertEquals(TableStatus.PAYING, table.getStatus());
	}
	
	@Test(expected = TableStatusTransitionInvalid.class)
	public void setStatusFromBusyToNOTCleaningTest() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.BUSY, order);
		
		// Mockito mocking
		Mockito.when(tableDao.findById(1)).thenReturn(table);
		// Mockito mocking
		
		tableService.changeStatus(table, TableStatus.FREE);
		
	}
	
	@Test
	public void setStatusFromCleaningToFreeTest() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.PAYING, order);
		
		// Mockito mocking
		Mockito.when(tableDao.findById(1)).thenReturn(table);
		// Mockito mocking
		
		tableService.changeStatus(table, TableStatus.FREE);
		
		assertEquals(TableStatus.FREE, table.getStatus());
	}
	
	@Test(expected = TableStatusTransitionInvalid.class)
	public void setStatusFromCleaningToNOTFreeTest() {
		Order order = new Order(1, null, null, OrderStatus.INACTIVE, 0, 0);
		Table table = new Table(TABLE_NAME, 1, TableStatus.PAYING, order);
		
		// Mockito mocking
		Mockito.when(tableDao.findById(1)).thenReturn(table);
		// Mockito mocking
		
		tableService.changeStatus(table, TableStatus.BUSY);
	}

	@Test
	public void findAllNotNullEmpty(){
		Mockito.when(tableService.findAll()).thenReturn(new LinkedList<Table>());
		Assert.assertNotNull(tableService.findAll());
	}

	@Test
	public void findAllNotNull(){
		Mockito.when(tableService.findAll()).thenReturn(null);
		Assert.assertNotNull(tableService.findAll());
	}


}
