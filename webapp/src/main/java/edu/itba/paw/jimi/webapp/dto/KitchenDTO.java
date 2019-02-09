package edu.itba.paw.jimi.webapp.dto;

import edu.itba.paw.jimi.models.Table;

import java.net.URI;
import java.util.List;

public class KitchenDTO {

	private TableListDTO busyTables;

	public KitchenDTO() {
	}

	public KitchenDTO(List<Table> busyTables, URI uri) {
		this.busyTables = new TableListDTO(busyTables, uri);
	}

	public TableListDTO getBusyTables() {
		return busyTables;
	}

	public void setBusyTables(TableListDTO busyTables) {
		this.busyTables = busyTables;

	}
}
