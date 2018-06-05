package edu.itba.paw.jimi.models;

public enum OrderStatus {
	OPEN,
	CLOSED,
	INACTIVE;
	
	
	public static OrderStatus getOrderStatus(int statusId) {
		for (OrderStatus t : OrderStatus.values()) {
			if (t.ordinal() == statusId) return t;
		}
		throw new IllegalArgumentException("OrderStatus not found.");
	}
	
	@Override
	public String toString() {
		switch (this) {
			case OPEN:
				return "Open";
			case CLOSED:
				return "Closed";
			case INACTIVE:
				return "Inactive";
			default:
				return "No status found";
		}
	}
}


