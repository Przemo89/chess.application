package com.capgemini.chess.service.to;

import com.capgemini.chess.algorithms.data.enums.Level;

public class PlayerProfileAndStatisticsTO {

	private long idProfile;
	private long idPlayerStatistics;
	private Level level;
	private int points;
	private int gamesPlayed;
	private int gamesWon;
	private int gamesDrawn;
	private int gamesLost;
	private long rankingPosition;
	private String login;
	private String firstName;
	private String lastName;
	private String email;

	public PlayerProfileAndStatisticsTO() {
		
	}
	
	public long getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(long idProfile) {
		this.idProfile = idProfile;
	}

	public long getIdPlayerStatistics() {
		return idPlayerStatistics;
	}

	public void setIdPlayerStatistics(long idPlayerStatistics) {
		this.idPlayerStatistics = idPlayerStatistics;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
