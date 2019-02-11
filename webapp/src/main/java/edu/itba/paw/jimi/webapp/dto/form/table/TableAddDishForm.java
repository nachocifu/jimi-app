package edu.itba.paw.jimi.webapp.dto.form.table;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class TableAddDishForm {

	@NotNull
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
