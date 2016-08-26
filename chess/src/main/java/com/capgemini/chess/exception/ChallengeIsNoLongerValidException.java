package com.capgemini.chess.exception;

public class ChallengeIsNoLongerValidException extends Exception {
	
	private static final long serialVersionUID = -6345786341L;

	public ChallengeIsNoLongerValidException(long idChallenge) {
		super("Challenge with id: " + idChallenge + " is no longer valid, because players' level changed.");
	}

}
