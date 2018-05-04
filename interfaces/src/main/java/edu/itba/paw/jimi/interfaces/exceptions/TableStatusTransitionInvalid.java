package edu.itba.paw.jimi.interfaces.exceptions;

import edu.itba.paw.jimi.models.TableStatus;

public class TableStatusTransitionInvalid extends RuntimeException {
    public TableStatusTransitionInvalid(TableStatus expected, TableStatus got) {
        super("Expected status was: " + expected.toString() + ", and got status: " + got.toString());
    }
}
