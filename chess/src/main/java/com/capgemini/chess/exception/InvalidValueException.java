package com.capgemini.chess.exception;

public class InvalidValueException extends IllegalArgumentException {

	private static final long serialVersionUID = -478562371L;

	public InvalidValueException(String errorMessage) {
		super(errorMessage);
	}
}
