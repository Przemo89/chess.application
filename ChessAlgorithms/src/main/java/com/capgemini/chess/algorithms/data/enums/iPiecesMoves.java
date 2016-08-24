package com.capgemini.chess.algorithms.data.enums;

import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;

public interface iPiecesMoves {

	boolean isMovePossible(Move moveToValidate);
	List<Move> possibleMoveGenerator(Coordinate from, Piece piece);
}
