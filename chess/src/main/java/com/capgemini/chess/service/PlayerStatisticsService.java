package com.capgemini.chess.service;

import com.capgemini.chess.algorithms.data.enums.GameResult;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;

public interface PlayerStatisticsService {

	void updatePlayersStatistics(PlayerStatisticsEntity whitePiecesPlayer, PlayerStatisticsEntity blackPiecesPlayer, 
			GameResult gameResultForWhitePiecesPlayer, GameResult gameResultForBlackPiecesPlayer);
	
	int calculateWinnerProfit(PlayerStatisticsEntity winningPlayer, PlayerStatisticsEntity loosingPlayer);
	
	int calculateLooserLoss(PlayerStatisticsEntity winningPlayer, PlayerStatisticsEntity loosingPlayer);
	
	int calculateChallengingPlayerPotentialLoss(PlayerStatisticsEntity winningPlayer, 
			PlayerStatisticsEntity loosingPlayer);
}
