package com.capgemini.chess.domain.statistics;

import com.capgemini.chess.algorithms.data.enums.Level;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;
import com.capgemini.chess.exception.InvalidValueException;

public class PointsCalculator {
	
	/**winningPlayer and losingPlayer CANNOT be injected but they should be set each 
	 * time ChallengeService will want to get potential profit/loss for specific players.
	 * StatisticsPointCounter should be singleton, no need to do it in other way.
	 */
	private PlayerStatisticsEntity winningPlayer;
	private PlayerStatisticsEntity loosingPlayer;
	
	public PointsCalculator(PlayerStatisticsEntity winningPlayer, PlayerStatisticsEntity losingPlayer) {
		this.winningPlayer = winningPlayer;
		this.loosingPlayer = losingPlayer;
	}
	
	private double calculateProgressPoints(PlayerStatisticsEntity player) {
		if (player.getLevel() == Level.CHUCK_NORRIS_OF_CHESS) {
			return 0.0;
		}
		double progressPoints = (player.getPoints() - player.getLevel().getPointsRequired())
				/(returnPointsPerRequiredLevel(player, 1) - returnPointsPerRequiredLevel(player, 0));
		return Math.min(progressPoints, 1.0);
	}
	
	/**It simply first calculate the value of desired level (in relation to player's current level).
	 * @param player - specifies, for which player should be returned points
	 * @param adjustLevelValue - method first gets player's current level and add adjustLevelValue.
	 * @return required points for adjusted level.
	 */
	private double returnPointsPerRequiredLevel(PlayerStatisticsEntity player, int adjustLevelValue) {
		checkAdjustedValue(adjustLevelValue);
		if (player.getLevel().getValue() + adjustLevelValue > 10) {
			return Level.CHUCK_NORRIS_OF_CHESS.getPointsRequired();
		}
		double points = Level.getLevelByValue(player.getLevel().getValue() + adjustLevelValue).getPointsRequired();
		return points;
	}
	
	/**Checks, if current player's level value + adjustedLevelValue are > 0.
	 * @param adjustLevelValue - this value is checked after addition to player's current level's value.
	 * @throws InvalidValueException when calculated value is less than 0.
	 */
	private void checkAdjustedValue(int adjustLevelValue) throws InvalidValueException {
		if (adjustLevelValue < 0) {
			throw new InvalidValueException("Provided value cannot be negative in order to artficially lower Player's level");
		}
	}
	
	private double calculateProgressGames(PlayerStatisticsEntity player) {
		if (player.getLevel() == Level.CHUCK_NORRIS_OF_CHESS) {
			return 0.0;
		}
		double progressGames = (player.getGamesPlayed() - player.getLevel().getGamesRequired())
				/(returnGamesPerRequiredLevel(player, 1) - returnGamesPerRequiredLevel(player, 0));
		return Math.min(progressGames, 1.0);
	}
	
	/**It simply first calculate the value of desired level (in relation to player's current level).
	 * @param player - specifies, for which player should be returned games
	 * @param adjustLevelValue - method first gets player's current level and add adjustLevelValue.
	 * @return required number of played games for adjusted level.
	 */
	private double returnGamesPerRequiredLevel(PlayerStatisticsEntity player, int adjustLevelValue) {
		checkAdjustedValue(adjustLevelValue);
		if (player.getLevel().getValue() + adjustLevelValue > 10) {
			return Level.CHUCK_NORRIS_OF_CHESS.getGamesRequired();
		}
		double games = Level.getLevelByValue(player.getLevel().getValue() + adjustLevelValue).getGamesRequired();
		return games;
	}
	
	private double calculateProgressWins(PlayerStatisticsEntity player) {
		if (player.getLevel() == Level.CHUCK_NORRIS_OF_CHESS || player.getLevel() == Level.NEWBIE) {
			return 0.0;
		}
		double playerGamesWon = player.getGamesWon();
		double playerGamesPlayed = player.getGamesPlayed();
		double playerWins = playerGamesWon/playerGamesPlayed;
		double progressWins = (playerWins - player.getLevel().getWinsRequired())
				/(returnWinsPerRequiredLevel(player, 1) - returnWinsPerRequiredLevel(player, 0));
		return progressWins;
	}
	
	/**It simply first calculate the value of desired level (in relation to player's current level).
	 * @param player - specifies, for which player should be returned wins
	 * @param adjustLevelValue - method first gets player's current level and add adjustLevelValue.
	 * @return required number of wins for adjusted level.
	 */
	private double returnWinsPerRequiredLevel(PlayerStatisticsEntity player, int adjustLevelValue) {
		checkAdjustedValue(adjustLevelValue);
		if (player.getLevel().getValue() + adjustLevelValue > 10) {
			return Level.CHUCK_NORRIS_OF_CHESS.getWinsRequired();
		}
		double wins = Level.getLevelByValue(player.getLevel().getValue() + adjustLevelValue).getWinsRequired();
		return wins;
	}
	
	private double calculateProgress(PlayerStatisticsEntity player) {
		if (player.getLevel() == Level.CHUCK_NORRIS_OF_CHESS) {
			return 0.0;
		}
		double progressPoints = calculateProgressPoints(player);
		double progressGames = calculateProgressGames(player);
		double progressWins = calculateProgressWins(player);
		double progress = (progressPoints + progressGames + progressWins)/3;
		return progress;
	}
	
	private int calculateBonusForWinner(PlayerStatisticsEntity winningPlayer, PlayerStatisticsEntity loosingPlayer, int baseProfit) {
		double progressDifference = calculateProgress(loosingPlayer) - calculateProgress(winningPlayer);
		int result = (int) Math.floor(progressDifference*baseProfit*0.5);
		return result;
	}
	
	private int calculateBaseProfit(PlayerStatisticsEntity winningPlayer, PlayerStatisticsEntity loosingPlayer) {
		int baseProfit = 0;
		int levelDifference = loosingPlayer.getLevel().getValue() - winningPlayer.getLevel().getValue();
		if (levelDifference <= -5) {
			baseProfit = 1;
			return baseProfit;
		}
		if (levelDifference == -4) {
			baseProfit = 2;
			return baseProfit;
		}
		double powerNumber = levelDifference + 3.0;
		baseProfit = 5 * (int) Math.pow(2, powerNumber);
		return baseProfit;
	}
	
	private int calculateBonusForLoser(PlayerStatisticsEntity winningPlayer, PlayerStatisticsEntity loosingPlayer, int baseLoss) {
		double progressDifference = calculateProgress(winningPlayer) - calculateProgress(loosingPlayer);
		int result = (int) Math.floor(progressDifference*baseLoss*0.5);
		return result;
	}
	
	private int calculateBaseLoss(PlayerStatisticsEntity winningPlayer, PlayerStatisticsEntity loosingPlayer) {
		int baseLoss = 0;
		int levelDifference = loosingPlayer.getLevel().getValue() - winningPlayer.getLevel().getValue();
		if (levelDifference <= -4) {
			baseLoss = 1;
			return baseLoss;
		}
		if (levelDifference == -3) {
			baseLoss = 3;
			return baseLoss;
		}
		if (levelDifference == -2) {
			baseLoss = 7;
			return baseLoss;
		}
		double powerNumber = levelDifference + 1.0;
		baseLoss =  3 * 5 * (int) Math.pow(2, powerNumber);
		return baseLoss;
	}
	
	public int calculateWinnerProfit() {
		int baseProfit = calculateBaseProfit(this.winningPlayer, this.loosingPlayer);
		return baseProfit + calculateBonusForWinner(this.winningPlayer, this.loosingPlayer, baseProfit);
	}
	
	public int calculateLooserLoss() {
		int baseLoss = calculateBaseLoss(this.winningPlayer, this.loosingPlayer);
		return baseLoss - calculateBonusForLoser(this.winningPlayer, this.loosingPlayer, baseLoss);
	}
	
	public int calculateChallengingPlayerPotentialLoss() {
		int baseLoss = calculateBaseLoss(this.loosingPlayer, this.winningPlayer);
		return baseLoss - calculateBonusForLoser(this.loosingPlayer, this.winningPlayer, baseLoss);
	}
	
}
