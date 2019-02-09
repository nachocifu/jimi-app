package edu.itba.paw.jimi.webapp.dto.form.dish;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

public class SetDishStockForm {

	@DecimalMin(value = "1")
	@DecimalMax(value = "10000")
	private int newStock;

	public int getNewStock() {
		return newStock;
	}

	public void setNewStock(int newStock) {
		this.newStock = newStock;
	}
}
