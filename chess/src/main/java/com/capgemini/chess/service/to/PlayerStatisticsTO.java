package com.capgemini.chess.service.to;

import com.capgemini.chess.algorithms.data.enums.Level;

public class PlayerStatisticsTO {

	private long id;
	private Level level;
	private int points;
	private int gamesPlayed;
	private int gamesWon;
	private int gamesDrawn;
	private int gamesLost;
	private int potentialBenefitForChallengingPlayer;
	private int potentialLossForChallengingPlayer;
	private int rankingPosition;
	private UserProfileTO userProfileTo;

	public PlayerStatisticsTO() {
		
	}
	
	public PlayerStatisticsTO(long id, Level level, int points, int gamesPlayed, int gamesWon, int gamesDrawn, int gamesLost, 
			int potentialBenefitForChallengingPlayer, int potentialLossForChallengingPlayer, int rankingPosition, UserProfileTO userProfileTo) {
		this.id = id;
		this.level = level;
		this.points = points;
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.gamesDrawn = gamesDrawn;
		this.gamesLost = gamesLost;
		this.potentialBenefitForChallengingPlayer = potentialBenefitForChallengingPlayer;
		this.potentialLossForChallengingPlayer = potentialLossForChallengingPlayer;
		this.rankingPosition = rankingPosition;
		this.userProfileTo = userProfileTo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
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

	public int getPotentialBenefitForChallengingPlayer() {
		return potentialBenefitForChallengingPlayer;
	}

	public void setPotentialBenefitForChallengingPlayer(int potentialBenefit) {
		this.potentialBenefitForChallengingPlayer = potentialBenefit;
	}

	public int getPotentialLossForChallengingPlayer() {
		return potentialLossForChallengingPlayer;
	}

	public void setPotentialLossForChallengingPlayer(int potentialLoss) {
		this.potentialLossForChallengingPlayer = potentialLoss;
	}

	public int getRankingPosition() {
		return rankingPosition;
	}

	public void setRankingPosition(int rankingPosition) {
		this.rankingPosition = rankingPosition;
	}

	public UserProfileTO getUserProfileTo() {
		return userProfileTo;
	}

	public void setUserProfileTo(UserProfileTO userProfileTo) {
		this.userProfileTo = userProfileTo;
	}

}
