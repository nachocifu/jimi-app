package edu.itba.paw.jimi.Exceptions;

public class LessStockAvailableException extends IllegalArgumentException {

    public LessStockAvailableException() {
        super("Stock available is not enough.");
    }
}
