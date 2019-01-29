package edu.itba.paw.jimi.interfaces.exceptions;

public class Http409Error extends HttpError {
	public Http409Error(String title, String body) {
		super(title, body, 409);
	}
}
