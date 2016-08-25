package com.capgemini.chess.domain.statistics;

import com.capgemini.chess.algorithms.data.enums.GameResult;
import com.capgemini.chess.algorithms.data.enums.Level;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;
import com.capgemini.chess.exception.InvalidGameResultException;

/**
 * This class will update Player's statistics. Here is only one public method
 * - @updatePlayersStatistics. To eliminate possibility, to update player's
 * statistics few times, flag @isStatisticAlreadyUpdated is set up as true after
 * first update.
 * 
 * @author PLENIK
 *
 */

public class PlayersStatisticsUpdate {

	private PlayerStatisticsEntity winningPlayer;
	private PlayerStatisticsEntity loosingPlayer;
	private final boolean isDraw;
	private boolean isStatisticAlreadyUpdated = false;
	private PointsCalculator statistics;

	public PlayersStatisticsUpdate(PlayerStatisticsEntity whitePiecesPlayer, GameResult gameResultForWhitePiecesPlayer,
			PlayerStatisticsEntity blackPiecesPlayer, GameResult gameResultForBlackPiecesPlayer) {
		checkInputDataCorectness(gameResultForWhitePiecesPlayer, gameResultForBlackPiecesPlayer);
		if (gameResultForWhitePiecesPlayer == GameResult.DRAW) {
			this.isDraw = true;
		} else {
			this.isDraw = false;
		}
		setWinningAndLoosingPlayer(whitePiecesPlayer, gameResultForWhitePiecesPlayer, blackPiecesPlayer,
				gameResultForBlackPiecesPlayer);
	}

	/**
	 * It's called in constructor after method which checks provided data
	 * corectness ( @checkInputDataCorectness). It always initialize winning and
	 * losing player, even if it's a draw.
	 * 
	 * @param whitePiecesPlayer
	 * @param gameResultForWhitePiecesPlayer
	 * @param blackPiecesPlayer
	 * @param gameResultForBlackPiecesPlayer
	 */
	private void setWinningAndLoosingPlayer(PlayerStatisticsEntity whitePiecesPlayer, GameResult gameResultForWhitePiecesPlayer,
			PlayerStatisticsEntity blackPiecesPlayer, GameResult gameResultForBlackPiecesPlayer) {
		if (gameResultForWhitePiecesPlayer == GameResult.WON) {
			this.winningPlayer = whitePiecesPlayer;
			this.loosingPlayer = blackPiecesPlayer;
			this.statistics = new PointsCalculator(this.winningPlayer, this.loosingPlayer);
			return;
		}
		this.winningPlayer = blackPiecesPlayer;
		this.loosingPlayer = whitePiecesPlayer;
		this.statistics = new PointsCalculator(this.winningPlayer, this.loosingPlayer);
	}

	private void checkInputDataCorectness(GameResult gameResultForWhitePiecesPlayer,
			GameResult gameResultForBlackPiecesPlayer) throws InvalidGameResultException {
		if (gameResultForWhitePiecesPlayer == GameResult.WON && gameResultForBlackPiecesPlayer == GameResult.WON) {
			throw new InvalidGameResultException();
		}
		if (gameResultForWhitePiecesPlayer == GameResult.LOST && gameResultForBlackPiecesPlayer == GameResult.LOST) {
			throw new InvalidGameResultException();
		}
		if (gameResultForWhitePiecesPlayer == GameResult.DRAW && gameResultForBlackPiecesPlayer != GameResult.DRAW) {
			throw new InvalidGameResultException();
		}
		if (gameResultForWhitePiecesPlayer != GameResult.DRAW && gameResultForBlackPiecesPlayer == GameResult.DRAW) {
			throw new InvalidGameResultException();
		}

	}

	public void updatePlayersStatistics() {
		if (this.isStatisticAlreadyUpdated == true) {
			return;
		}
		if (this.isDraw == true) {
			updatePlayersStatisticsInCaseOfDraw();
			this.isStatisticAlreadyUpdated = true;
			return;
		}
		updateWinnerStatistics();
		updateLooserStatistics();
		this.isStatisticAlreadyUpdated = true;

	}

	private void updatePlayersStatisticsInCaseOfDraw() {
		this.winningPlayer.setGamesPlayed(this.winningPlayer.getGamesPlayed() + 1);
		this.winningPlayer.setGamesDrawn(this.winningPlayer.getGamesDrawn() + 1);
		this.loosingPlayer.setGamesPlayed(this.loosingPlayer.getGamesPlayed() + 1);
		this.loosingPlayer.setGamesDrawn(this.loosingPlayer.getGamesDrawn() + 1);
		determinePlayersNewLevel(this.winningPlayer);
		determinePlayersNewLevel(this.loosingPlayer);

	}

	private void updateWinnerStatistics() {
		this.winningPlayer.setPoints(this.winningPlayer.getPoints() + statistics.calculateWinnerProfit());
		this.winningPlayer.setGamesPlayed(this.winningPlayer.getGamesPlayed() + 1);
		this.winningPlayer.setGamesWon(this.winningPlayer.getGamesWon() + 1);

		determinePlayersNewLevel(this.winningPlayer);
	}

	private void updateLooserStatistics() {
		int newPlayersPoints = this.loosingPlayer.getPoints() - statistics.calculateLooserLoss();
		if (newPlayersPoints < 0) {
			this.loosingPlayer.setPoints(0);
		} else {
			this.loosingPlayer.setPoints(newPlayersPoints);
		}
		this.loosingPlayer.setGamesPlayed(this.loosingPlayer.getGamesPlayed() + 1);
		this.loosingPlayer.setGamesLost(this.loosingPlayer.getGamesLost() + 1);

		determinePlayersNewLevel(this.loosingPlayer);
	}

	private void determinePlayersNewLevel(PlayerStatisticsEntity playerToUpdate) {
		double playerWins = (double) playerToUpdate.getGamesWon() / playerToUpdate.getGamesPlayed();
		for (int i = 10; i >= 1; i--) {
			if (playerToUpdate.getPoints() >= Level.getLevelByValue(i).getPointsRequired()) {
				if (playerToUpdate.getGamesPlayed() >= Level.getLevelByValue(i).getGamesRequired()
						&& playerWins >= Level.getLevelByValue(i).getWinsRequired()) {
					playerToUpdate.setLevel(Level.getLevelByValue(i));
					return;
				}
			}
		}
	}

}
