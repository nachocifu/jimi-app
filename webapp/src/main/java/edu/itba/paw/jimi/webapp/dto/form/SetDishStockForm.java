package edu.itba.paw.jimi.webapp.dto.form;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

public class SetDishStockForm {
	
	@DecimalMin(value = "1")
	@DecimalMax(value = "10000")
	private int oldStock;
	
	@DecimalMin(value = "1")
	@DecimalMax(value = "10000")
	private int newStock;
	
	public int getOldStock() {
		return oldStock;
	}
	
	public void setOldStock(int oldStock) {
		this.oldStock = oldStock;
	}
	
	public int getNewStock() {
		return newStock;
	}
	
	public void setNewStock(int newStock) {
		this.newStock = newStock;
	}
}
