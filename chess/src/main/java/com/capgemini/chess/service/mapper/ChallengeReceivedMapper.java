package com.capgemini.chess.service.mapper;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.service.to.ChallengeReceivedTO;

public class ChallengeReceivedMapper {

	public static ChallengeReceivedTO map(ChallengeEntity challengeEntity) {
		if (challengeEntity != null && challengeEntity.getPlayerChallenging() != null) {
			ChallengeReceivedTO challengeTo = new ChallengeReceivedTO();
			challengeTo.setIdChallenge(challengeEntity.getId());
			challengeTo.setLevelPlayerChallenging(challengeEntity.getLevelPlayerChallenging());
			challengeTo.setPointsPlayerChallenging(challengeEntity.getPlayerChallenging().getPoints());
			challengeTo.setGamesPlayedPlayerChallenging(challengeEntity.getPlayerChallenging().getGamesPlayed());
			challengeTo.setGamesWonPlayerChallenging(challengeEntity.getPlayerChallenging().getGamesWon());
			challengeTo.setGamesDrawnPlayerChallenging(challengeEntity.getPlayerChallenging().getGamesDrawn());
			challengeTo.setGamesLostPlayerChallenging(challengeEntity.getPlayerChallenging().getGamesLost());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			challengeTo.setStringDateCreationChallenge(simpleDateFormat.format(challengeEntity.getDateCreated().getTime()));
			challengeTo.setPotentialBenefitForChallengedPlayer(challengeEntity.getPlayerChallenging().getPotentialBenefitForOtherPlayer());
			challengeTo.setPotentialLossForChallengedPlayer(challengeEntity.getPlayerChallenging().getPotentialLossForOtherPlayer());
			challengeTo.setLoginPlayerChallenging(challengeEntity.getPlayerChallenging().getPlayerProfile().getLogin());
			StringBuilder nameBuilder = new StringBuilder();
			challengeTo.setNamePlayerChallenging(nameBuilder.append(challengeEntity.getPlayerChallenging().getPlayerProfile().getFirstName())
					.append(" ").append(challengeEntity.getPlayerChallenging().getPlayerProfile().getLastName()).toString());
			return challengeTo;
		}
		return null;
	}
	
	public static List<ChallengeReceivedTO> map2TOs(List<ChallengeEntity> challengeEntities) {
		return challengeEntities.stream().map(ChallengeReceivedMapper::map).collect(Collectors.toList());
	}
	
}
