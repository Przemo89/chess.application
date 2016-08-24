package com.capgemini.chess.exception;

public class ChallengeIsNoLongerValidException extends Exception {
	
	public ChallengeIsNoLongerValidException(long idChallenge) {
		super("Challenge with id: " + idChallenge + " is no longer valid, beccause players' level changed.");
	}

}
