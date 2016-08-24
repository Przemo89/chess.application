package com.capgemini.chess.exception;

public class PlayerNotExistException extends IllegalArgumentException {

	public PlayerNotExistException() {
		super("Player does not exist anymore.");
	}

}
