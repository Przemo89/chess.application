package com.capgemini.chess.exception;

public class InsufficientDataProvidedException extends IllegalArgumentException {

	public InsufficientDataProvidedException() {
		super("Please provide White- and Black Pieces Players and Game Results for both Players.");
	}
	
}
