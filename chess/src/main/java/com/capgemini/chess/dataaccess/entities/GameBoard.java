package com.capgemini.chess.dataaccess.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.Piece;

@Embeddable
public class GameBoard implements Serializable {

	private static final long serialVersionUID = -980548121L;
	
	@Column(name = "from_x_coordinate", nullable = false)
	private int fromX;
	
	@Column(name = "from_y_coordinate", nullable = false)
	private int fromY;
	
	@Column(name = "to_x_coordinate", nullable = false)
	private int toX;
	
	@Column(name = "to_y_coordinate", nullable = false)
	private int toY;
	
	@Column(name = "move_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private MoveType moveType;
	
	@Column(name = "moved_piece", nullable = false)
	@Enumerated(EnumType.STRING)
	private Piece piece;
	
	public GameBoard() {
		
	}

	public int getFromX() {
		return fromX;
	}

	public void setFromX(int fromX) {
		this.fromX = fromX;
	}

	public int getFromY() {
		return fromY;
	}

	public void setFromY(int fromY) {
		this.fromY = fromY;
	}

	public int getToX() {
		return toX;
	}

	public void setToX(int toX) {
		this.toX = toX;
	}

	public int getToY() {
		return toY;
	}

	public void setToY(int toY) {
		this.toY = toY;
	}

	public MoveType getMoveType() {
		return moveType;
	}

	public void setMoveType(MoveType moveType) {
		this.moveType = moveType;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}
}
