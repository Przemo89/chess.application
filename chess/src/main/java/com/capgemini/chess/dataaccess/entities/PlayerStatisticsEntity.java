package com.capgemini.chess.dataaccess.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.capgemini.chess.algorithms.data.enums.Level;

@Entity
@Table(name = "player_statistics")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PlayerStatisticsEntity extends VersionControl implements Serializable {
	
	private static final long serialVersionUID = -3489571L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToMany(mappedBy = "playerChallenging", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ChallengeEntity> challengesSent;
	
	@OneToMany(mappedBy = "playerChallenged", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ChallengeEntity> challengesReceived;
	
	@OneToMany(mappedBy = "playerStatisticsWhitePieces", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<GameEntity> currentGamesWithWhitePieces;
	
	@OneToMany(mappedBy = "playerStatisticsBlackPieces", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<GameEntity> currentGamesWithBlackPieces;
	
	@OneToOne(mappedBy = "playerStatistics", fetch = FetchType.LAZY)
	private PlayerProfileEntity playerProfile;
	
	@Column(name = "points_total", nullable = false, columnDefinition = "int DEFAULT 0")
	private int points;
	
	@Column(name = "games_played", nullable = false, columnDefinition = "int DEFAULT 0")
	private int gamesPlayed;
	
	@Column(name = "games_won", nullable = false, columnDefinition = "int default 0")
	private int gamesWon;
	
	@Column(name = "games_drawn", nullable = false, columnDefinition = "int default 0")
	private int gamesDrawn;
	
	@Column(name = "games_lost", nullable = false, columnDefinition = "int default 0")
	private int gamesLost;
	
	@Column(name = "player_level", nullable = false)
	@Enumerated(EnumType.STRING)
	private Level level = Level.NEWBIE;
	
	@Transient
	private int potentialBenefitForOtherPlayer;
	
	@Transient
	private int potentialLossForOtherPlayer;
	
	@Transient
	private long rankingPosition;
	
	public PlayerStatisticsEntity() {
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<ChallengeEntity> getChallengesSent() {
		return challengesSent;
	}

	public void setChallengesSent(List<ChallengeEntity> challengesSent) {
		this.challengesSent = challengesSent;
	}

	public List<ChallengeEntity> getChallengesReceived() {
		return challengesReceived;
	}

	public void setChallengesReceived(List<ChallengeEntity> challengesReceived) {
		this.challengesReceived = challengesReceived;
	}
	
	public List<GameEntity> getCurrentGamesWithWhitePieces() {
		return currentGamesWithWhitePieces;
	}

	public void setCurrentGamesWithWhitePieces(List<GameEntity> currentGamesWithWhitePieces) {
		this.currentGamesWithWhitePieces = currentGamesWithWhitePieces;
	}

	public List<GameEntity> getCurrentGamesWithBlackPieces() {
		return currentGamesWithBlackPieces;
	}

	public void setCurrentGamesWithBlackPieces(List<GameEntity> currentGamesWithBlackPieces) {
		this.currentGamesWithBlackPieces = currentGamesWithBlackPieces;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public int getGamesWon() {
		return gamesWon;
	}

	public void setGamesWon(int gamesWon) {
		this.gamesWon = gamesWon;
	}

	public int getGamesDrawn() {
		return gamesDrawn;
	}

	public void setGamesDrawn(int gamesDrawn) {
		this.gamesDrawn = gamesDrawn;
	}

	public int getGamesLost() {
		return gamesLost;
	}

	public void setGamesLost(int gamesLost) {
		this.gamesLost = gamesLost;
	}
	
	public PlayerProfileEntity getPlayerProfile() {
		return playerProfile;
	}

	public void setPlayerProfile(PlayerProfileEntity playerProfile) {
		this.playerProfile = playerProfile;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getPotentialBenefitForOtherPlayer() {
		return potentialBenefitForOtherPlayer;
	}

	public void setPotentialBenefitForOtherPlayer(int potentialBenefitForChallengingPlayer) {
		this.potentialBenefitForOtherPlayer = potentialBenefitForChallengingPlayer;
	}

	public int getPotentialLossForOtherPlayer() {
		return potentialLossForOtherPlayer;
	}

	public void setPotentialLossForOtherPlayer(int potentialLossForChallengingPlayer) {
		this.potentialLossForOtherPlayer = potentialLossForChallengingPlayer;
	}

	public long getRankingPosition() {
		return rankingPosition;
	}

	public void setRankingPosition(long rankingPosition) {
		this.rankingPosition = rankingPosition;
	}

}
