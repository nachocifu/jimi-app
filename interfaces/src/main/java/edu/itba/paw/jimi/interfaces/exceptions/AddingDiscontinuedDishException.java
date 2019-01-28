package edu.itba.paw.jimi.interfaces.exceptions;

public class AddingDiscontinuedDishException extends Http400Error {
	public AddingDiscontinuedDishException(String title, String body) {
		super(title, body);
	}
}
