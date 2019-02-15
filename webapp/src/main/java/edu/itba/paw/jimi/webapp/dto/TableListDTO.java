package edu.itba.paw.jimi.webapp.dto;

import edu.itba.paw.jimi.models.Table;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class TableListDTO {

	private List<TableDTO> tables;
	private PaginationDTO links;

	public TableListDTO() {
	}

	public TableListDTO(List<Table> allTables, URI baseUri, PaginationDTO links) {
		this.tables = new LinkedList<>();

		for (Table table : allTables)
			this.tables.add(new TableDTO(table, baseUri));

		this.links = links;
	}

	public PaginationDTO getLinks() {
		return links;
	}

	public void setLinks(PaginationDTO links) {
		this.links = links;
	}

	public List<TableDTO> getTables() {
		return tables;
	}

	public void setTables(List<TableDTO> tables) {
		this.tables = tables;
	}
}
