package com.capgemini.chess.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.capgemini.chess.dataaccess.entities.PlayerProfileEntity;
import com.capgemini.chess.service.to.PlayerProfileAndStatisticsTO;

public class PlayerProfileAndStatisticsMapper {

	public static PlayerProfileAndStatisticsTO map(PlayerProfileEntity playerProfile) {
		if (playerProfile != null && playerProfile.getPlayerStatistics() != null) {
			PlayerProfileAndStatisticsTO playerProfileAndStatisticsTo = new PlayerProfileAndStatisticsTO();
			playerProfileAndStatisticsTo.setIdProfile(playerProfile.getId());
			playerProfileAndStatisticsTo.setIdPlayerStatistics(playerProfile.getPlayerStatistics().getId());
			playerProfileAndStatisticsTo.setLevel(playerProfile.getPlayerStatistics().getLevel());
			playerProfileAndStatisticsTo.setPoints(playerProfile.getPlayerStatistics().getPoints());
			playerProfileAndStatisticsTo.setGamesPlayed(playerProfile.getPlayerStatistics().getGamesPlayed());
			playerProfileAndStatisticsTo.setGamesWon(playerProfile.getPlayerStatistics().getGamesWon());
			playerProfileAndStatisticsTo.setGamesDrawn(playerProfile.getPlayerStatistics().getGamesDrawn());
			playerProfileAndStatisticsTo.setGamesLost(playerProfile.getPlayerStatistics().getGamesLost());
			playerProfileAndStatisticsTo.setRankingPosition(playerProfile.getPlayerStatistics().getRankingPosition());
			playerProfileAndStatisticsTo.setLogin(playerProfile.getLogin());
			playerProfileAndStatisticsTo.setFirstName(playerProfile.getFirstName());
			playerProfileAndStatisticsTo.setLastName(playerProfile.getLastName());
			playerProfileAndStatisticsTo.setEmail(playerProfile.getEmail());
			return playerProfileAndStatisticsTo;
		}
		return null;
	}
	
	public static List<PlayerProfileAndStatisticsTO> map2TOs(List<PlayerProfileEntity> playerProfileEntities) {
		return playerProfileEntities.stream().map(PlayerProfileAndStatisticsMapper::map).collect(Collectors.toList());
	}
}
