package edu.itba.paw.jimi.models;

public enum DishStatus {
	AVAILABLE,
	UNAVAILABLE;
	
	@Override
	public String toString() {
		switch (this) {
			case AVAILABLE:
				return "Available";
			
			case UNAVAILABLE:
				return "Unavailable";
			
			default:
				return "No status found";
		}
	}
}
