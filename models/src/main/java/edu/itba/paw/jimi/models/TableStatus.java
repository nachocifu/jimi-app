package edu.itba.paw.jimi.models;

public enum TableStatus {
	BUSY(1),
	FREE(2),
	PAYING(3);
	
	
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

	@Override
	public String toString() {
		switch (TableStatus.getTableStatus(id)) {
			case BUSY:
				return "BUSY";
			case FREE:
				return "FREE";
			case PAYING:
				return "Cleaning required";
			default:
				return "Not found";
		}
	}
}