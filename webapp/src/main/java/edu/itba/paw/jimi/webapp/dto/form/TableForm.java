package edu.itba.paw.jimi.webapp.dto.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TableForm {
	
	@Size(min = 4, max = 20)
	@Pattern(regexp = "^[a-zA-Z0-9_]+( [a-zA-Z0-9_]+)*$")
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
