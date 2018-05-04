package edu.itba.paw.jimi.models;

public enum OrderStatus {
	OPEN(1),
	CLOSED(2),
	INACTIVE(3);


	private int id;

	OrderStatus(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static OrderStatus getTableStatus(int statusId) {
		for (OrderStatus t : OrderStatus.values()) {
			if (t.id == statusId) return t;
		}
		throw new IllegalArgumentException("OrderStatus not found."); //TODO
	}

	@Override
	public String toString() {
		switch (OrderStatus.getTableStatus(id)){
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


