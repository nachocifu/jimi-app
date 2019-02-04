package edu.itba.paw.jimi.webapp.dto;

public class ExceptionDTO {

	private String message;

	public ExceptionDTO() {
	}

	public ExceptionDTO(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
