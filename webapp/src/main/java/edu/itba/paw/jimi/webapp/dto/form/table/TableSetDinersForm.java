package edu.itba.paw.jimi.webapp.dto.form.table;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

public class TableSetDinersForm {
	
	@DecimalMin(value = "1")
	@DecimalMax(value = "100")
	private int diners;
	
	public int getDiners() {
		return diners;
	}
	
	public void setDiners(int diners) {
		this.diners = diners;
	}
}
