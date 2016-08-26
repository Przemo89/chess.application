package com.capgemini.chess.exception;

public class ChallengeDataIntegrityViolationException extends Exception {

	private static final long serialVersionUID = -3467232341L;

	public ChallengeDataIntegrityViolationException(long idChallenge) {
		super("Challenge with id: " + idChallenge + 
				" is no longer valid, because players' level changed.");
	}
}
