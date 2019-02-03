package edu.itba.paw.jimi.webapp.dto.form.table;

import javax.validation.constraints.DecimalMin;

public class TableAddDishForm {
	
	private int dishId;
	
	@DecimalMin(value = "1")
	private int amount;
	
	public int getDishId() {
		return dishId;
	}
	
	public void setDishId(int dishId) {
		this.dishId = dishId;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
