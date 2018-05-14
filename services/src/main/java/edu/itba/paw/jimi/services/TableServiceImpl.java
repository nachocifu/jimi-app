package edu.itba.paw.jimi.services;

import edu.itba.paw.jimi.interfaces.daos.TableDao;
import edu.itba.paw.jimi.interfaces.exceptions.TableStatusTransitionInvalid;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.OrderStatus;
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
	private OrderService orderService;
	
	public Table findById(final long id) {
		return tableDao.findById(id);
	}
	
	public Table create(String name) {
		Order order = orderService.create(OrderStatus.INACTIVE, null, null, 0);
		return tableDao.create(name, TableStatus.Free, order);
	}
	
	public Collection<Table> findAll() {
		return tableDao.findAll();
	}
	
	public void changeStatus(Table table, TableStatus status) {
		
		Table t = tableDao.findById(table.getId());
		
		if (t.getStatus().equals(TableStatus.Busy) && !status.equals(TableStatus.CleaningRequired))
			throw new TableStatusTransitionInvalid(TableStatus.CleaningRequired, status);
		
		if (t.getStatus().equals(TableStatus.CleaningRequired) && !status.equals(TableStatus.Free))
			throw new TableStatusTransitionInvalid(TableStatus.Free, status);
		
		if (t.getStatus().equals(TableStatus.Free) && !status.equals(TableStatus.Busy))
			throw new TableStatusTransitionInvalid(TableStatus.Busy, status);
		
		
		switch (status) {
			case Busy: {
				orderService.open(t.getOrder());
				break;
			}
			case Free: {
				
				//Nothing to do.
				break;
			}
			case CleaningRequired: {
				
				// Lets close the current order.
				orderService.close(t.getOrder());
				//TODO: A pensar... Diners no tendria que ir en order? porque cuando lo cierro, quiero que quede cuanta gente comio ahi. Aparte, como cobras el servicio de mesa si no lo tiene order? Podrian tenerlo los 2?
				//@cappa: deberìa estar en order.
				// Now lets create a new inactive order for our table.
				Order newOrder = orderService.create(OrderStatus.INACTIVE, null, null, 0);
				t.setOrder(newOrder);
				
				break;
			}
		}
		
		table.setStatus(status);
		t.setStatus(status);
		tableDao.update(t);
	}
}
