package edu.itba.paw.jimi.webapp.dto;

import edu.itba.paw.jimi.models.Table;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class TableListDTO {
	
	private List<TableDTO> tables;
	
	public TableListDTO() {
	}
	
	public TableListDTO(List<Table> allTables, URI baseUri) {
		this.tables = new LinkedList<>();
		
		for (Table table : allTables)
			this.tables.add(new TableDTO(table, baseUri));
	}
	
	public List<TableDTO> getTables() {
		return tables;
	}
	
	public void setTables(List<TableDTO> tables) {
		this.tables = tables;
	}
}
