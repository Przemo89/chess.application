package com.capgemini.chess.service.to;

public class ChallengeToBeCreatedTO {

	private long idPlayerStatisticsChallenging;
	private long idPlayerStatisticsChallenged;
	
	public ChallengeToBeCreatedTO() {
		
	}

	public long getIdPlayerStatisticsChallenging() {
		return idPlayerStatisticsChallenging;
	}

	public void setIdPlayerStatisticsChallenging(long idPlayerStatisticsChallenging) {
		this.idPlayerStatisticsChallenging = idPlayerStatisticsChallenging;
	}

	public long getIdPlayerStatisticsChallenged() {
		return idPlayerStatisticsChallenged;
	}

	public void setIdPlayerStatisticsChallenged(long idPlayerStatisticsChallenged) {
		this.idPlayerStatisticsChallenged = idPlayerStatisticsChallenged;
	}
	
}
