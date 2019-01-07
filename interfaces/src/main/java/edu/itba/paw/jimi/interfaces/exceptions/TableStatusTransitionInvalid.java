package edu.itba.paw.jimi.interfaces.exceptions;

import edu.itba.paw.jimi.models.TableStatus;

public class TableStatusTransitionInvalid extends RuntimeException {
    public TableStatusTransitionInvalid(TableStatus expected, TableStatus got) {
        super("Expected status was: " + expected.name() + ", and got status: " + got.name());
    }
}
