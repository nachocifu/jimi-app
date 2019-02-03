package edu.itba.paw.jimi.models;


import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "tables")
public class Table {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tables_tableid_seq")
	@SequenceGenerator(sequenceName = "tables_tableid_seq", name = "tables_tableid_seq", allocationSize = 1)
	@Column(name = "tableid")
	private long id;
	
	@Column(length = 20, nullable = false, unique = true)
	private String name;
	
	@Enumerated(EnumType.ORDINAL)
	private TableStatus status;
	
	@OneToOne(fetch = FetchType.EAGER, orphanRemoval = false)
	@Where(clause = "statusid = 0")
	private Order order;
	
	public Table() {
	}
	
	public Table(String name, TableStatus status, Order order) {
		this.name = name;
		this.status = status;
		this.order = order;
	}
	
	public Table(String name, long id, TableStatus status, Order order) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.order = order;
	}
	
	@Override
	public String toString() {
		return "Table{" +
				", name='" + name + '\'' +
				", status=" + status +
				", order=" + order +
				'}';
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStatus(TableStatus status) {
		this.status = status;
	}
	
	public String getName() {
		return name;
	}
	
	public TableStatus getStatus() {
		return status;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public long getId() {
		return id;
	}
}
