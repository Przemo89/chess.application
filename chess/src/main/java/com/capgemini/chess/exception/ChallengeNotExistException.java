package com.capgemini.chess.exception;

public class ChallengeNotExistException extends Exception{

	public ChallengeNotExistException(long idChallenge) {
		super("Challenge with id: " + idChallenge + " does not exist");
	}
}
