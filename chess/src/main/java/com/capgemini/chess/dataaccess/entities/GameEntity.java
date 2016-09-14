package com.capgemini.chess.dataaccess.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "games")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class GameEntity extends VersionControl implements Serializable {

	private static final long serialVersionUID = -78973411L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_player_statistics_white_pieces", nullable = false, updatable = false)
	private PlayerStatisticsEntity playerStatisticsWhitePieces;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_player_statistics_black_pieces", nullable = false, updatable = false)
	private PlayerStatisticsEntity playerStatisticsBlackPieces;
	
	@ElementCollection
	@CollectionTable(name = "moves", joinColumns = @JoinColumn(name = "id_game_entity"))
	private List<GameBoard> gameBoard;
	
	public GameEntity() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PlayerStatisticsEntity getPlayerStatisticsWhitePieces() {
		return playerStatisticsWhitePieces;
	}

	public void setPlayerStatisticsWhitePieces(PlayerStatisticsEntity playerStatisticsWhitePieces) {
		this.playerStatisticsWhitePieces = playerStatisticsWhitePieces;
	}

	public PlayerStatisticsEntity getPlayerStatisticsBlackPieces() {
		return playerStatisticsBlackPieces;
	}

	public void setPlayerStatisticsBlackPieces(PlayerStatisticsEntity playerStatisticsBlackPieces) {
		this.playerStatisticsBlackPieces = playerStatisticsBlackPieces;
	}

	public List<GameBoard> getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(List<GameBoard> gameBoard) {
		this.gameBoard = gameBoard;
	}
}
