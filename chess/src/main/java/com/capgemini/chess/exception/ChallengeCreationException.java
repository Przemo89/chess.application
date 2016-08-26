package com.capgemini.chess.exception;

public class ChallengeCreationException extends Exception {
	
	private static final long serialVersionUID = 91212371L;

	public ChallengeCreationException() {
		super("Player cannot sent challenge to himself!");
	}

}
