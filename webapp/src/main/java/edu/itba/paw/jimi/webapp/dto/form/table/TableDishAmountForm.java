package edu.itba.paw.jimi.webapp.dto.form.table;

import javax.validation.constraints.DecimalMin;

public class TableDishAmountForm {
	
	@DecimalMin(value = "0")
	private int amount;
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
