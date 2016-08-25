package com.capgemini.chess.exception;

public class PlayerNotExistException extends Exception {

	private static final long serialVersionUID = -538967341L;

	public PlayerNotExistException(long idPlayer) {
		super("Player with id = " + idPlayer + " does not exist anymore.");
	}

}
