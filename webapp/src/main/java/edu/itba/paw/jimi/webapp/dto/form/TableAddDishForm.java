package edu.itba.paw.jimi.webapp.dto.form;

import javax.validation.constraints.DecimalMin;

public class TableAddDishForm {
	
	private int dishid;
	
	@DecimalMin(value = "1")
	private int amount;
	
	public int getDishid() {
		return dishid;
	}
	
	public void setDishid(int dishid) {
		this.dishid = dishid;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
