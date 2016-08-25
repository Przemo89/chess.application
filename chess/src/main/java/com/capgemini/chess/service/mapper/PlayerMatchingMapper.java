package com.capgemini.chess.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;
import com.capgemini.chess.service.to.PlayerMatchingTO;

public class PlayerMatchingMapper {

	public static PlayerMatchingTO map(PlayerStatisticsEntity playerStats) {
		if (playerStats != null && playerStats.getPlayerProfile() != null) {
			PlayerMatchingTO playerMatchingTo = new PlayerMatchingTO();
			playerMatchingTo.setLevel(playerStats.getLevel());
			playerMatchingTo.setPoints(playerStats.getPoints());
			playerMatchingTo.setGamesPlayed(playerStats.getGamesPlayed());
			playerMatchingTo.setGamesWon(playerStats.getGamesWon());
			playerMatchingTo.setGamesDrawn(playerStats.getGamesDrawn());
			playerMatchingTo.setGamesLost(playerStats.getGamesLost());
			playerMatchingTo.setPotentialBenefitForChallengingPlayer(playerMatchingTo.getPotentialBenefitForChallengingPlayer());
			playerMatchingTo.setPotentialLossForChallengingPlayer(playerStats.getPotentialLossForChallengingPlayer());
			playerMatchingTo.setRankingPosition(playerStats.getRankingPosition());
			playerMatchingTo.setLogin(playerStats.getPlayerProfile().getLogin());
			playerMatchingTo.setFirstName(playerStats.getPlayerProfile().getFirstName());
			playerMatchingTo.setLastName(playerStats.getPlayerProfile().getLastName());
		}
		return null;
	}
	
	public static List<PlayerMatchingTO> map2TOs(List<PlayerStatisticsEntity> playerStatisticsEntities) {
		return playerStatisticsEntities.stream().map(PlayerMatchingMapper::map).collect(Collectors.toList());
	}
}
