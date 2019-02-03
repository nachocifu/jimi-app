package edu.itba.paw.jimi.webapp.dto.form.table;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class TableDinersForm {
	
	@NotNull
	@DecimalMin(value = "0")
	private int diners;
	
	public int getDiners() {
		return diners;
	}
	
	public void setDiners(int diners) {
		this.diners = diners;
	}
}
