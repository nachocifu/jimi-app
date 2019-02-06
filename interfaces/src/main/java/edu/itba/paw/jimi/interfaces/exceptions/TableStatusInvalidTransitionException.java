package edu.itba.paw.jimi.interfaces.exceptions;

import edu.itba.paw.jimi.models.TableStatus;

public class TableStatusInvalidTransitionException extends RuntimeException {

	private TableStatus expected;
	private TableStatus actual;

	public TableStatusInvalidTransitionException(TableStatus expected, TableStatus actual) {
		super();
		this.expected = expected;
		this.actual = actual;
	}

	public TableStatus getExpected() {
		return expected;
	}

	public TableStatus getActual() {
		return actual;
	}
}
