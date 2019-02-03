package edu.itba.paw.jimi.webapp.dto.form.table;

import edu.itba.paw.jimi.models.TableStatus;

import javax.validation.constraints.NotNull;

public class TableStatusForm {
	
	@NotNull(message = "{table.status.not.found}")
	private TableStatus status;
	
	public TableStatus getStatus() {
		return status;
	}
	
	public void setStatus(TableStatus status) {
		this.status = status;
	}
}
