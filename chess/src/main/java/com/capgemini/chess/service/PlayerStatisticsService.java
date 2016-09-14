package com.capgemini.chess.service;

import com.capgemini.chess.algorithms.data.enums.GameResult;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;

public interface PlayerStatisticsService {

	void updatePlayersStatistics(PlayerStatisticsEntity whitePiecesPlayer, PlayerStatisticsEntity blackPiecesPlayer, 
			GameResult gameResultForWhitePiecesPlayer, GameResult gameResultForBlackPiecesPlayer);
	
	public int calculateWinnerProfit(PlayerStatisticsEntity winningPlayer, PlayerStatisticsEntity loosingPlayer);
	
	public int calculateLooserLoss(PlayerStatisticsEntity winningPlayer, PlayerStatisticsEntity loosingPlayer);
	
	public int calculateChallengingPlayerPotentialLoss(PlayerStatisticsEntity winningPlayer, 
			PlayerStatisticsEntity loosingPlayer);
}
