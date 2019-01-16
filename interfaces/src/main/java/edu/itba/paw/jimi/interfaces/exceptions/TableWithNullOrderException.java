package edu.itba.paw.jimi.interfaces.exceptions;

import javax.persistence.PersistenceException;

public class TableWithNullOrderException extends PersistenceException {
	public TableWithNullOrderException() {
		super();
	}
}
