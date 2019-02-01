package edu.itba.paw.jimi.models;

public enum TableStatus {
	BUSY,
	FREE,
	PAYING;
	
	public static TableStatus getTableStatus(int statusId) {
		for (TableStatus t : TableStatus.values()) {
			if (t.ordinal() == statusId) return t;
		}
		return null;
	}
}