package com.capgemini.chess.exception;

public class InvalidGameResultException extends IllegalArgumentException {

	private static final long serialVersionUID = -897125641L;

	public InvalidGameResultException() {
		super("Invalid Game Result provided.");
	}
}
