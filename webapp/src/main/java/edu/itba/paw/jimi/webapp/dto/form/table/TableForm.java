package edu.itba.paw.jimi.webapp.dto.form.table;

import edu.itba.paw.jimi.models.TableStatus;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TableForm {

	@Size(min = 4, max = 20)
	@Pattern(regexp = "^[a-zA-Z0-9_]+( [a-zA-Z0-9_]+)*$")
	private String name;

	private TableStatus status;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public TableStatus getStatus() {
		return status;
	}

	public void setStatus(TableStatus status) {
		this.status = status;
	}
}
