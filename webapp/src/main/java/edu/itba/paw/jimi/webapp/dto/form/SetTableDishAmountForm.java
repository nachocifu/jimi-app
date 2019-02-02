package edu.itba.paw.jimi.webapp.dto.form;

import javax.validation.constraints.DecimalMin;

public class SetTableDishAmountForm {
	
	private int dishId;
	
	@DecimalMin(value = "0")
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
