package com.capgemini.chess.exception;

public class ChallengeDeclineException extends Exception {

	private static final long serialVersionUID = -8489571L;

	public ChallengeDeclineException() {
		super("Cannot decline challenge, which wasn't received!");
	}
}
