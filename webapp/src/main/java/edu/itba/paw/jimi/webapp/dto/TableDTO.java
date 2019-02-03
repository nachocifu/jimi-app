package edu.itba.paw.jimi.webapp.dto;

import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;

import java.net.URI;

public class TableDTO {
	
	private long id;
	private String name;
	private TableStatus status;
	private OrderDTO order;
	private URI uri;
	
	public TableDTO() {}
	
	public TableDTO(Table table, URI baseURI) {
		this.id = table.getId();
		this.name = table.getName();
		this.status = table.getStatus();
		this.order = new OrderDTO(table.getOrder());
		this.uri = baseURI.resolve(String.valueOf(this.id));
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public TableStatus getStatus() {
		return status;
	}
	
	public void setStatus(TableStatus status) {
		this.status = status;
	}
	
	public OrderDTO getOrder() {
		return order;
	}
	
	public void setOrder(OrderDTO order) {
		this.order = order;
	}
	
	public URI getUri() {
		return uri;
	}
	
	public void setUri(URI uri) {
		this.uri = uri;
	}
}
