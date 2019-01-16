package edu.itba.paw.jimi.interfaces.exceptions;

import javax.persistence.PersistenceException;

public class ExistingTableNameException extends PersistenceException {
	public ExistingTableNameException(String existingTableName) {
		super("Existing table name: " + existingTableName);
	}
}
