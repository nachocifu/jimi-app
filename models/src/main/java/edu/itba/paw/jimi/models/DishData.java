package edu.itba.paw.jimi.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Embeddable
public class DishData {
	
	@Column(precision = 10, nullable = false)
	private int amount;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderedAt;
	
	public DishData() {
		this.amount = 0;
	}
	
	public DishData(int amount) {
		this.amount = amount;
		this.orderedAt = new Timestamp(Calendar.getInstance().getTimeInMillis());
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public Date getOrderedAt() {
		return orderedAt;
	}
	
	public void setOrderedAt(Date orderedAt) {
		this.orderedAt = orderedAt;
	}
	
}
