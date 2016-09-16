package com.capgemini.chess.service.mapper;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.service.to.ChallengeSentTO;

public class ChallengeSentMapper {
	
	public static ChallengeSentTO map(ChallengeEntity challengeEntity) {
		if (challengeEntity != null && challengeEntity.getPlayerChallenged() != null) {
			ChallengeSentTO challengeTo = new ChallengeSentTO();
			challengeTo.setIdChallenge(challengeEntity.getId());
			challengeTo.setLevelPlayerChallenged(challengeEntity.getLevelPlayerChallenged());
			challengeTo.setPointsPlayerChallenged(challengeEntity.getPlayerChallenged().getPoints());
			challengeTo.setGamesPlayedPlayerChallenged(challengeEntity.getPlayerChallenged().getGamesPlayed());
			challengeTo.setGamesWonPlayerChallenged(challengeEntity.getPlayerChallenged().getGamesWon());
			challengeTo.setGamesDrawnPlayerChallenged(challengeEntity.getPlayerChallenged().getGamesDrawn());
			challengeTo.setGamesLostPlayerChallenged(challengeEntity.getPlayerChallenged().getGamesLost());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			challengeTo.setStringDateCreationChallenge(simpleDateFormat.format(challengeEntity.getDateCreated().getTime()));
			challengeTo.setPotentialBenefitForChallengingPlayer(challengeEntity.getPlayerChallenged().getPotentialBenefitForOtherPlayer());
			challengeTo.setPotentialLossForChallengingPlayer(challengeEntity.getPlayerChallenged().getPotentialLossForOtherPlayer());
			challengeTo.setLoginPlayerChallenged(challengeEntity.getPlayerChallenged().getPlayerProfile().getLogin());
			StringBuilder nameBuilder = new StringBuilder();
			challengeTo.setNamePlayerChallenged(nameBuilder.append(challengeEntity.getPlayerChallenged().getPlayerProfile().getFirstName())
					.append(" ").append(challengeEntity.getPlayerChallenged().getPlayerProfile().getLastName()).toString());
			return challengeTo;
		}
		return null;
	}
	
	public static List<ChallengeSentTO> map2TOs(List<ChallengeEntity> challengeEntities) {
		return challengeEntities.stream().map(ChallengeSentMapper::map).collect(Collectors.toList());
	}

}
