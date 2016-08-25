package com.capgemini.chess.service.mapper;

import java.time.LocalDate;

import com.capgemini.chess.algorithms.data.enums.Level;
import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.service.to.ChallengeTO;

public class ChallengeMapper {
	
	public static ChallengeTO map(ChallengeEntity challengeEntity) {
		if (challengeEntity != null && challengeEntity.getPlayerChallenged() != null) {
			ChallengeTO challengeTo = new ChallengeTO();
			challengeTo.setIdChallenge(challengeEntity.getId());
			challengeTo.setLevelPlayerChallenged(challengeEntity.getLevelPlayerChallenged());
			challengeTo.setPointsPlayerChallenged(challengeEntity.getPlayerChallenged().getPoints());
			challengeTo.setGamesPlayedPlayerChallenged(challengeEntity.getPlayerChallenged().getGamesPlayed());
			challengeTo.setGamesWonPlayerChallenged(challengeEntity.getPlayerChallenged().getGamesWon());
			challengeTo.setGamesDrawnPlayerChallenged(challengeEntity.getPlayerChallenged().getGamesDrawn());
			challengeTo.setGamesLostPlayerChallenged(challengeEntity.getPlayerChallenged().getGamesLost());
			challengeTo.setDateCreationChallenge(challengeEntity.getDateCreated());
			challengeTo.setPotentialBenefitForChallengingPlayer(challengeEntity.getPlayerChallenged().getPotentialBenefitForChallengingPlayer());
			challengeTo.setPotentialLossForChallengingPlayer(challengeEntity.getPlayerChallenged().getPotentialLossForChallengingPlayer());
			challengeTo.setLoginPlayerChallenged(challengeEntity.getPlayerChallenged().getPlayerProfile().getLogin());
			StringBuilder nameBuilder = new StringBuilder();
			challengeTo.setNamePlayerChallenged(nameBuilder.append(challengeEntity.getPlayerChallenged().getPlayerProfile().getFirstName())
					.append(" ").append(challengeEntity.getPlayerChallenged().getPlayerProfile().getLastName()).toString());
			return challengeTo;
		}
		return null;
	}
	
	public static ChallengeEntity map(ChallengeTO challengeTo) {
		if (challengeTo != null) {
			ChallengeEntity challengeEntity = new ChallengeEntity();
			challengeEntity.setId(challengeTo.getIdChallenge());
//			challengeEntity.set
		}
		return null;
	}

}
