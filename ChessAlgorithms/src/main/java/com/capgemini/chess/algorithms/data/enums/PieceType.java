package com.capgemini.chess.algorithms.data.enums;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.generated.Board;

/**
 *Chess piece types
 *@author PLENIK
 *@BISHOP - laufer
 *@KNIGHT - skoczek/koń
 *@ROOK - wieża
 */
public enum PieceType implements iPiecesMoves {
	KING {
		@Override
		public boolean isMovePossible(Move moveToValidate) {
			if (moveToValidate.getFrom().getX() == moveToValidate.getTo().getX()) {
				return Math.abs(moveToValidate.getFrom().getY() - moveToValidate.getTo().getY()) == 1;
			}
			if (moveToValidate.getFrom().getY() == moveToValidate.getTo().getY()) {
				return Math.abs(moveToValidate.getFrom().getX() - moveToValidate.getTo().getX()) == 1;
			}
			return Math.abs(moveToValidate.getFrom().getX() - moveToValidate.getTo().getX()) == 1 && 
					Math.abs(moveToValidate.getFrom().getY() - moveToValidate.getTo().getY()) == 1;
		}
		@Override
		public List<Move> possibleMoveGenerator(Coordinate from, Piece piece) {
			List<Move> possibleMovesList = new ArrayList<Move>();
			for (int x = from.getX() - 1; x <= from.getX() + 1; x++){
				for (int y = from.getY() - 1; y <= from.getY() + 1; y++){
					if (x != from.getX() || y != from.getY()) {
						possibleMovesList.add(new Move().setNewCoordinateFrom(from)
								.setNewCoordinateTo(new Coordinate(x, y))
								.setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK));
					}
				}
			}
			return possibleMovesList;
		}
	},
    QUEEN {
		@Override
		public boolean isMovePossible(Move moveToValidate) {
			if (moveToValidate.getFrom().getX() == moveToValidate.getTo().getX()) {
				return moveToValidate.getFrom().getY() != moveToValidate.getTo().getY();
			}
			if (moveToValidate.getFrom().getY() == moveToValidate.getTo().getY()) {
				return moveToValidate.getFrom().getX() != moveToValidate.getTo().getX();
			}
			return Math.abs(moveToValidate.getFrom().getY() - moveToValidate.getTo().getY()) == 
					Math.abs(moveToValidate.getFrom().getX() - moveToValidate.getTo().getX());
		}
		@Override
		public List<Move> possibleMoveGenerator(Coordinate from, Piece piece) {
			List<Move> possibleMovesList = new ArrayList<Move>();
			generateDiagonalMoves(from, piece, possibleMovesList);
			generateVerticalAndHorizontalMoves(from, piece, possibleMovesList);

			return possibleMovesList;
		}
	},
    BISHOP {
		@Override
		public boolean isMovePossible(Move moveToValidate) {
			if (moveToValidate.getFrom().getY() != moveToValidate.getTo().getY()) {
				return Math.abs(moveToValidate.getFrom().getY() - moveToValidate.getTo().getY()) == 
						Math.abs(moveToValidate.getFrom().getX() - moveToValidate.getTo().getX());
			}
			return false;
		}
		@Override
		public List<Move> possibleMoveGenerator(Coordinate from, Piece piece) {
			List<Move> possibleMovesList = new ArrayList<Move>();
			generateDiagonalMoves(from, piece, possibleMovesList);
			
			return possibleMovesList;
		}
	},
    KNIGHT {
		@Override
		public boolean isMovePossible(Move moveToValidate) {
			if (Math.abs(moveToValidate.getFrom().getX() - moveToValidate.getTo().getX()) == 1){
				return Math.abs(moveToValidate.getFrom().getY() - moveToValidate.getTo().getY()) == 2;
			}
			if (Math.abs(moveToValidate.getFrom().getX() - moveToValidate.getTo().getX()) == 2){
				return Math.abs(moveToValidate.getFrom().getY() - moveToValidate.getTo().getY()) == 1;
			}
			return false;
		}
		@Override
		public List<Move> possibleMoveGenerator(Coordinate from, Piece piece) {
			List<Move> possibleMovesList = new ArrayList<Move>();
			
			int y1 = from.getY() - 2;
			int x1 = from.getX() - 1;
			for (int i = 1; i <= 4; i++) {
				if (i > 2) {
					x1 = from.getX() + 1;
				}
				if (i % 2 == 0) {
					y1 = from.getY() + 2;
				}
					possibleMovesList.add(
							new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(x1, y1))
									.setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK));
					y1 = y1 - 4;
			}
			
			int x2 = from.getX() - 2;
			int y2 = from.getY() - 1;
			for (int i = 1; i <= 4; i++) {
				if (i > 2) {
					y2 = from.getY() + 1;
				}
				if (i % 2 == 0) {
					x2 = from.getX() + 2;
				}
					possibleMovesList.add(
							new Move().setNewCoordinateFrom(from).setNewCoordinateTo(new Coordinate(x2, y2))
									.setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK));
					x2 = x2 - 4;
			}
			return possibleMovesList;
		}
	},
    ROOK {
		@Override
		public boolean isMovePossible(Move moveToValidate) {
			if (moveToValidate.getFrom().getX() == moveToValidate.getTo().getX()) {
				return moveToValidate.getFrom().getY() != moveToValidate.getTo().getY();
			}
			if (moveToValidate.getFrom().getY() == moveToValidate.getTo().getY()) {
				return moveToValidate.getFrom().getX() != moveToValidate.getTo().getX();
			}
			return false;
		}
		@Override
		public List<Move> possibleMoveGenerator(Coordinate from, Piece piece) {
			List<Move> possibleMovesList = new ArrayList<Move>();
			generateVerticalAndHorizontalMoves(from, piece, possibleMovesList);
			return possibleMovesList;
		}
	},
    PAWN {
		@Override
		public boolean isMovePossible(Move moveToValidate) {
			if (moveToValidate.getType() == MoveType.CAPTURE) {
				return isCaptureMovePossible(moveToValidate);
			}
			boolean isXMoveValid = false;
			boolean isYMoveValid = false;
			isXMoveValid = moveToValidate.getFrom().getX() == moveToValidate.getTo().getX();
			if (moveToValidate.getMovedPiece().getColor() == Color.WHITE ){
				if (moveToValidate.getFrom().getY() == 1 && moveToValidate.getTo().getY() == 3){
					return isXMoveValid;
				}
				isYMoveValid = moveToValidate.getFrom().getY() - moveToValidate.getTo().getY() == -1;
				return isXMoveValid && isYMoveValid;
			}
			if (moveToValidate.getFrom().getY() == 6 && moveToValidate.getTo().getY() == 4){
				return isXMoveValid;
			}
			isYMoveValid = moveToValidate.getFrom().getY() - moveToValidate.getTo().getY() == 1;
			return isXMoveValid && isYMoveValid;
		}

		public boolean isCaptureMovePossible(Move moveToValidate) {
			boolean isXMoveValid;
			boolean isYMoveValid;
				if (moveToValidate.getMovedPiece().getColor() == Color.WHITE){
					isXMoveValid = Math.abs(moveToValidate.getFrom().getX() - moveToValidate.getTo().getX()) == 1;
					isYMoveValid = moveToValidate.getFrom().getY() - moveToValidate.getTo().getY() == -1;
					return isXMoveValid && isYMoveValid;
				}
				isXMoveValid = Math.abs(moveToValidate.getFrom().getX() - moveToValidate.getTo().getX()) == 1;
				isYMoveValid = moveToValidate.getFrom().getY() - moveToValidate.getTo().getY() == 1;
				return isXMoveValid && isYMoveValid;
		}
		
		@Override
		public List<Move> possibleMoveGenerator(Coordinate from, Piece piece) {

			List<Move> possibleMovesList = new ArrayList<Move>();
			if (piece == Piece.WHITE_PAWN) {
				for (int i = -1; i <= 1; i++) {
					possibleMovesList.add(new Move()
							.setNewCoordinateFrom(from)
							.setNewCoordinateTo(new Coordinate(from.getX() + i, from.getY()+1))
							.setNewMovedPiece(piece).setNewMoveType(MoveType.CAPTURE));
				}
			} else {
				for (int i = -1; i <= 1; i++) {
					possibleMovesList.add(new Move()
							.setNewCoordinateFrom(from)
							.setNewCoordinateTo(new Coordinate(from.getX() + i, from.getY()-1))
							.setNewMovedPiece(piece).setNewMoveType(MoveType.CAPTURE));
				}
			}
			possibleMovesList.get(1).setType(MoveType.ATTACK);
			return possibleMovesList;
		}
	};
	
	private static List<Move> generateVerticalAndHorizontalMoves(Coordinate from, Piece piece, List<Move> possibleMovesList) {
		for (int x = 0; x < Board.SIZE; x++){
			if (x != from.getX()) {
				possibleMovesList.add(new Move().setNewCoordinateFrom(from)
						.setNewCoordinateTo(new Coordinate(x, from.getY()))
						.setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK));
			}
		}
		for (int y = 0; y < Board.SIZE; y++) {
			if (y != from.getY()) {
				possibleMovesList.add(new Move().setNewCoordinateFrom(from)
						.setNewCoordinateTo(new Coordinate(from.getX(), y))
						.setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK));
			}
		}
		return possibleMovesList;
	}

	private static List<Move> generateDiagonalMoves(Coordinate from, Piece piece, List<Move> possibleMovesList) {
			for (int x = from.getX() -1, y = from.getY() -1; x >= 0 && y >= 0; x--, y--) {
				possibleMovesList
				.add(new Move().setNewCoordinateFrom(from)
						.setNewCoordinateTo(new Coordinate(x, y))
						.setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK));
			} 
			for (int x = from.getX() +1, y = from.getY() +1; x < Board.SIZE && y < Board.SIZE; x++, y++) {
				possibleMovesList
				.add(new Move().setNewCoordinateFrom(from)
						.setNewCoordinateTo(new Coordinate(x, y))
						.setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK));
			}
			for (int x = from.getX() +1, y = from.getY() -1; x < Board.SIZE && y >= 0; x++, y--) {
				possibleMovesList
				.add(new Move().setNewCoordinateFrom(from)
						.setNewCoordinateTo(new Coordinate(x, y))
						.setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK));
			}
			for (int x = from.getX() -1, y = from.getY() +1; x >= 0 && y < Board.SIZE; x--, y++) {
				possibleMovesList
				.add(new Move().setNewCoordinateFrom(from)
						.setNewCoordinateTo(new Coordinate(x, y))
						.setNewMovedPiece(piece).setNewMoveType(MoveType.ATTACK));
			}
		return possibleMovesList;
	}
}
