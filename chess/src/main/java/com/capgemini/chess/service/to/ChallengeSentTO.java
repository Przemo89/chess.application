package com.capgemini.chess.service.to;

import com.capgemini.chess.algorithms.data.enums.Level;

public class ChallengeSentTO {

	private long idChallenge;
	private Level levelPlayerChallenged;
	private int pointsPlayerChallenged;
	private int gamesPlayedPlayerChallenged;
	private int gamesWonPlayerChallenged;
	private int gamesDrawnPlayerChallenged;
	private int gamesLostPlayerChallenged;
	private String stringDateCreationChallenge;
	private int potentialBenefitForChallengingPlayer;
	private int potentialLossForChallengingPlayer;
	private String loginPlayerChallenged;
	private String namePlayerChallenged;
	
	public ChallengeSentTO() {
		
	}

	public long getIdChallenge() {
		return idChallenge;
	}

	public void setIdChallenge(long idChallenge) {
		this.idChallenge = idChallenge;
	}

	public Level getLevelPlayerChallenged() {
		return levelPlayerChallenged;
	}

	public void setLevelPlayerChallenged(Level levelPlayerChallenged) {
		this.levelPlayerChallenged = levelPlayerChallenged;
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

	public String getLoginPlayerChallenged() {
		return loginPlayerChallenged;
	}

	public void setLoginPlayerChallenged(String loginPlayerChallenged) {
		this.loginPlayerChallenged = loginPlayerChallenged;
	}

	public String getNamePlayerChallenged() {
		return namePlayerChallenged;
	}

	public void setNamePlayerChallenged(String namePlayerChallenged) {
		this.namePlayerChallenged = namePlayerChallenged;
	}
	
	public int getPointsPlayerChallenged() {
		return pointsPlayerChallenged;
	}

	public void setPointsPlayerChallenged(int pointsPlayerChallenged) {
		this.pointsPlayerChallenged = pointsPlayerChallenged;
	}

	public int getGamesPlayedPlayerChallenged() {
		return gamesPlayedPlayerChallenged;
	}

	public void setGamesPlayedPlayerChallenged(int gamesPlayedPlayerChallenged) {
		this.gamesPlayedPlayerChallenged = gamesPlayedPlayerChallenged;
	}

	public int getGamesWonPlayerChallenged() {
		return gamesWonPlayerChallenged;
	}

	public void setGamesWonPlayerChallenged(int gamesWonPlayerChallenged) {
		this.gamesWonPlayerChallenged = gamesWonPlayerChallenged;
	}

	public int getGamesDrawnPlayerChallenged() {
		return gamesDrawnPlayerChallenged;
	}

	public void setGamesDrawnPlayerChallenged(int gamesDrawnPlayerChallenged) {
		this.gamesDrawnPlayerChallenged = gamesDrawnPlayerChallenged;
	}

	public int getGamesLostPlayerChallenged() {
		return gamesLostPlayerChallenged;
	}

	public void setGamesLostPlayerChallenged(int gamesLostPlayerChallenged) {
		this.gamesLostPlayerChallenged = gamesLostPlayerChallenged;
	}
	
	public String getStringDateCreationChallenge() {
		return stringDateCreationChallenge;
	}

	public void setStringDateCreationChallenge(String stringDateCreationChallenge) {
		this.stringDateCreationChallenge = stringDateCreationChallenge;
	}
}
