package com.capgemini.chess.exception;

public class ChallengeNotExistException extends Exception{

	private static final long serialVersionUID = 1348957349L;

	public ChallengeNotExistException(long idChallenge) {
		super("Challenge with id: " + idChallenge + " does not exist.");
	}
}
