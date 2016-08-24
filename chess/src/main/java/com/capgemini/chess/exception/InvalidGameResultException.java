package com.capgemini.chess.exception;

public class InvalidGameResultException extends IllegalArgumentException {

	public InvalidGameResultException() {
		super("Invalid Game Result provided.");
	}
}
