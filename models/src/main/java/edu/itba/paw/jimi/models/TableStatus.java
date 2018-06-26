package edu.itba.paw.jimi.models;

public enum TableStatus {
	BUSY,
	FREE,
	PAYING;
	
	public static TableStatus getTableStatus(int statusId) {
		for (TableStatus t : TableStatus.values()) {
			if (t.ordinal() == statusId) return t;
		}
		throw new IllegalArgumentException("TableStatus not found.");
	}

	@Override
	public String toString() {
		switch (TableStatus.getTableStatus(this.ordinal())) {
			case BUSY:
				return "BUSY";
			case FREE:
				return "FREE";
			case PAYING:
                return "PAYING";
			default:
				return "Not found";
		}
	}
}