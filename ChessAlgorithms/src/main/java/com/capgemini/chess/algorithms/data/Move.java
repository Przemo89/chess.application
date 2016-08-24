package com.capgemini.chess.algorithms.data;

import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.Piece;

/**
 * Chess move definition.
 * 
 * @author Michal Bejm
 *
 */
public class Move {

	private Coordinate from;
	private Coordinate to;
	private MoveType type;
	private Piece movedPiece;
	
	public Move() {

	}

	public Move setNewCoordinateFrom(Coordinate from) {
		this.from = from;
		return this;
	}
	
	public Move setNewCoordinateTo(Coordinate to) {
		this.to = to;
		return this;
	}
	public Move setNewMoveType(MoveType type) {
		this.type = type;
		return this;
	}
	
	public Move setNewMovedPiece(Piece movedPiece) {
		this.movedPiece = movedPiece;
		return this;
	}
	
	public Coordinate getFrom() {
		return from;
	}

	public void setFrom(Coordinate from) {
		this.from = from;
	}

	public Coordinate getTo() {
		return to;
	}

	public void setTo(Coordinate to) {
		this.to = to;
	}

	public MoveType getType() {
		return type;
	}

	public void setType(MoveType type) {
		this.type = type;
	}

	public Piece getMovedPiece() {
		return movedPiece;
	}

	public void setMovedPiece(Piece movedPiece) {
		this.movedPiece = movedPiece;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (movedPiece != other.movedPiece)
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
