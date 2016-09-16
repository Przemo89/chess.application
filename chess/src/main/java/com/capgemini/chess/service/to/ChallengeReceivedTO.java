package com.capgemini.chess.service.to;

import com.capgemini.chess.algorithms.data.enums.Level;

public class ChallengeReceivedTO {

	private long idChallenge;
	private Level levelPlayerChallenging;
	private int pointsPlayerChallenging;
	private int gamesPlayedPlayerChallenging;
	private int gamesWonPlayerChallenging;
	private int gamesDrawnPlayerChallenging;
	private int gamesLostPlayerChallenging;
	private String stringDateCreationChallenge;
	private int potentialBenefitForChallengedPlayer;
	private int potentialLossForChallengedPlayer;
	private String loginPlayerChallenging;
	private String namePlayerChallenging;
	
	public ChallengeReceivedTO() {
		
	}

	public long getIdChallenge() {
		return idChallenge;
	}

	public void setIdChallenge(long idChallenge) {
		this.idChallenge = idChallenge;
	}

	public Level getLevelPlayerChallenging() {
		return levelPlayerChallenging;
	}

	public void setLevelPlayerChallenging(Level levelPlayerChallenging) {
		this.levelPlayerChallenging = levelPlayerChallenging;
	}

	public int getPointsPlayerChallenging() {
		return pointsPlayerChallenging;
	}

	public void setPointsPlayerChallenging(int pointsPlayerChallenging) {
		this.pointsPlayerChallenging = pointsPlayerChallenging;
	}

	public int getGamesPlayedPlayerChallenging() {
		return gamesPlayedPlayerChallenging;
	}

	public void setGamesPlayedPlayerChallenging(int gamesPlayedPlayerChallenging) {
		this.gamesPlayedPlayerChallenging = gamesPlayedPlayerChallenging;
	}

	public int getGamesWonPlayerChallenging() {
		return gamesWonPlayerChallenging;
	}

	public void setGamesWonPlayerChallenging(int gamesWonPlayerChallenging) {
		this.gamesWonPlayerChallenging = gamesWonPlayerChallenging;
	}

	public int getGamesDrawnPlayerChallenging() {
		return gamesDrawnPlayerChallenging;
	}

	public void setGamesDrawnPlayerChallenging(int gamesDrawnPlayerChallenging) {
		this.gamesDrawnPlayerChallenging = gamesDrawnPlayerChallenging;
	}

	public int getGamesLostPlayerChallenging() {
		return gamesLostPlayerChallenging;
	}

	public void setGamesLostPlayerChallenging(int gamesLostPlayerChallenging) {
		this.gamesLostPlayerChallenging = gamesLostPlayerChallenging;
	}

	public String getStringDateCreationChallenge() {
		return stringDateCreationChallenge;
	}

	public void setStringDateCreationChallenge(String stringDateCreationChallenge) {
		this.stringDateCreationChallenge = stringDateCreationChallenge;
	}

	public int getPotentialBenefitForChallengedPlayer() {
		return potentialBenefitForChallengedPlayer;
	}

	public void setPotentialBenefitForChallengedPlayer(int potentialBenefitForChallengedPlayer) {
		this.potentialBenefitForChallengedPlayer = potentialBenefitForChallengedPlayer;
	}

	public int getPotentialLossForChallengedPlayer() {
		return potentialLossForChallengedPlayer;
	}

	public void setPotentialLossForChallengedPlayer(int potentialLossForChallengedPlayer) {
		this.potentialLossForChallengedPlayer = potentialLossForChallengedPlayer;
	}

	public String getLoginPlayerChallenging() {
		return loginPlayerChallenging;
	}

	public void setLoginPlayerChallenging(String loginPlayerChallenging) {
		this.loginPlayerChallenging = loginPlayerChallenging;
	}

	public String getNamePlayerChallenging() {
		return namePlayerChallenging;
	}

	public void setNamePlayerChallenging(String namePlayerChallenging) {
		this.namePlayerChallenging = namePlayerChallenging;
	}
}
