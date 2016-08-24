package com.capgemini.domain;

import javax.persistence.PrePersist;

import com.capgemini.chess.dataaccess.entities.PlayerProfileEntity;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;

public class PlayerProfileListener {

	@PrePersist
	public void createPlayerStatistics(PlayerProfileEntity playerProfile) {
		PlayerStatisticsEntity playerStatistics = new PlayerStatisticsEntity();
		playerStatistics.setId(playerProfile.getId());
		playerProfile.setPlayerStatistics(playerStatistics);
	}
}
