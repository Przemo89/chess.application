package com.capgemini.chess.service.to;

import java.time.LocalDate;

import com.capgemini.chess.algorithms.data.enums.Level;

public class ChallengeTO {

	private long idChallenge = 0;
	private long idOfChallengingPlayer = 0;
	private long idOfChallengedPlayer = 0;
	private Level levelOfChallengingPlayer;
	private Level levelOfChallengedPlayer;
	private LocalDate dateOfChallengeCreation;
	private PlayerStatisticsTO challengingPlayer;
	private PlayerStatisticsTO challengedPlayer;

	public ChallengeTO(long idChallenge, long idOfChallengingPlayer, long idOfChallengedPlayer,
			Level levelOfChallengingPlayer, Level levelOfChallengedPlayer, LocalDate dateOfChallengeCreation,
			PlayerStatisticsTO challengingPlayer, PlayerStatisticsTO challengedPlayer) {
		this.idChallenge = idChallenge;
		this.idOfChallengingPlayer = idOfChallengingPlayer;
		this.idOfChallengedPlayer = idOfChallengedPlayer;
		this.levelOfChallengingPlayer = levelOfChallengingPlayer;
		this.levelOfChallengedPlayer = levelOfChallengedPlayer;
		this.dateOfChallengeCreation = dateOfChallengeCreation;
		this.challengingPlayer = challengingPlayer;
		this.challengedPlayer = challengedPlayer;
	}
	
	public ChallengeTO() {
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChallengeTO other = (ChallengeTO) obj;
		if (challengedPlayer == null) {
			if (other.challengedPlayer != null)
				return false;
		} else if (!challengedPlayer.equals(other.challengedPlayer))
			return false;
		if (challengingPlayer == null) {
			if (other.challengingPlayer != null)
				return false;
		} else if (!challengingPlayer.equals(other.challengingPlayer))
			return false;
		return true;
	}



	public long getIdChallenge() {
		return idChallenge;
	}

	public void setIdChallenge(long idChallenge) {
		this.idChallenge = idChallenge;
	}

	public long getIdOfChallengingPlayer() {
		return idOfChallengingPlayer;
	}

	public void setIdOfChallengingPlayer(long idOfChallengingPlayer) {
		this.idOfChallengingPlayer = idOfChallengingPlayer;
	}

	public long getIdOfChallengedPlayer() {
		return idOfChallengedPlayer;
	}

	public void setIdOfChallengedPlayer(long idOfChellengedPlayer) {
		this.idOfChallengedPlayer = idOfChellengedPlayer;
	}

	public Level getLevelOfChallengingPlayer() {
		return levelOfChallengingPlayer;
	}

	public void setLevelOfChallengingPlayer(Level levelOfChallengingPlayer) {
		this.levelOfChallengingPlayer = levelOfChallengingPlayer;
	}

	public Level getLevelOfChallengedPlayer() {
		return levelOfChallengedPlayer;
	}

	public void setLevelOfChallengedPlayer(Level levelOfChallengedPlayer) {
		this.levelOfChallengedPlayer = levelOfChallengedPlayer;
	}

	public LocalDate getDateOfChallengeCreation() {
		return dateOfChallengeCreation;
	}

	public void setDateOfChallengeCreation(LocalDate dateOfChallengeCreation) {
		this.dateOfChallengeCreation = dateOfChallengeCreation;
	}
	
	public PlayerStatisticsTO getChallengingPlayer() {
		return challengingPlayer;
	}

	public void setChallengingPlayer(PlayerStatisticsTO challengingPlayer) {
		this.challengingPlayer = challengingPlayer;
	}

	public PlayerStatisticsTO getChallengedPlayer() {
		return challengedPlayer;
	}

	public void setChallengedPlayer(PlayerStatisticsTO challengedPlayer) {
		this.challengedPlayer = challengedPlayer;
	}

}
