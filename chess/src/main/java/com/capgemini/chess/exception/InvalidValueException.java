package com.capgemini.chess.exception;

public class InvalidValueException extends IllegalArgumentException {

	public InvalidValueException(String errorMessage) {
		super(errorMessage);
	}
}
