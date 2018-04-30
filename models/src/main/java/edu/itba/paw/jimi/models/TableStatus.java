package edu.itba.paw.jimi.models;

public enum TableStatus {
	Busy(1),
	Free(2),
	CleaningRequired(3);
	
	
	private int id;
	
	TableStatus(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static TableStatus getTableStatus(int statusId) {
		for (TableStatus t : TableStatus.values()) {
			if (t.id == statusId) return t;
		}
		throw new IllegalArgumentException("TableStatus not found.");
	}
}


