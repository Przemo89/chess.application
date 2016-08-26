package com.capgemini.chess.service.to;

import com.capgemini.chess.algorithms.data.enums.Level;

public class PlayerMatchingTO {

	private long id;
	private Level level;
	private int points;
	private int gamesPlayed;
	private int gamesWon;
	private int gamesDrawn;
	private int gamesLost;
	private int potentialBenefitForChallengingPlayer;
	private int potentialLossForChallengingPlayer;
	private long rankingPosition;
	private String login;
	private String firstName;
	private String lastName;
	
	public PlayerMatchingTO() {
		
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

	public void setPotentialBenefitForChallengingPlayer(int potentialBenefitForChallengingPlayer) {
		this.potentialBenefitForChallengingPlayer = potentialBenefitForChallengingPlayer;
	}

	public int getPotentialLossForChallengingPlayer() {
		return potentialLossForChallengingPlayer;
	}

	public void setPotentialLossForChallengingPlayer(int potentialLossForChallengingPlayer) {
		this.potentialLossForChallengingPlayer = potentialLossForChallengingPlayer;
	}

	public long getRankingPosition() {
		return rankingPosition;
	}

	public void setRankingPosition(long rankingPosition) {
		this.rankingPosition = rankingPosition;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
