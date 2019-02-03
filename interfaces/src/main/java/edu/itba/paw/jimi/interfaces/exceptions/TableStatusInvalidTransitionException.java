package edu.itba.paw.jimi.interfaces.exceptions;

import edu.itba.paw.jimi.models.TableStatus;

public class TableStatusInvalidTransitionException extends RuntimeException {
	public TableStatusInvalidTransitionException(TableStatus expected, TableStatus got) {
		super("Expected status was: " + expected.name() + ", and got status: " + got.name());
	}
}
