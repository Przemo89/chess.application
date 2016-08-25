package com.capgemini.chess.domain.statistics;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.capgemini.chess.algorithms.data.enums.Level;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;

/**TestCase 1. Calculating profit for Winning player, when difference between 
 * Losing Player (Weakling) and Winning (Advanced) is a negative number 
 * (Level(loser) - Level(Winner)) = 7 - 2 = -5).  
 * TestCase 2. Calculating profit for Winning player, when difference between 
 * Losing Player (Advanced) and Winning (Weakling) is a positive number 
 * (Level(loser) - Level(Winner)) = 7 - 2 = 5).
 * TestCase 3. Calculating loss for Losing player, when difference between 
 * Losing Player (Weakling) and Winning (Advanced) is a negative number 
 * (Level(loser) - Level(Winner)) = 7 - 2 = -5).   
 * TestCase 4. Calculating loss for Losing player, when difference between 
 * Losing Player (Advanced) and Winning (Weakling) is a positive number 
 * (Level(loser) - Level(Winner)) = 10 - 4 = 6).
 */

public class PointsCalculatorTest {

	private PlayerStatisticsEntity winningPlayer;
	private PlayerStatisticsEntity loosingPlayer;
	private PointsCalculator statisticsTest;
	
	@Before
	public void createPlayerObject() {
		this.winningPlayer = new PlayerStatisticsEntity();
		this.loosingPlayer = new PlayerStatisticsEntity();
	}
	
	@After
	public void resetStatisticsObject() {
		statisticsTest = null;
	}

	
	/**TestCase 1. Calculating profit for Winning player, when difference between 
	 * Losing Player (Weakling) and Winning (Advanced) is a negative number 
	 * (Level(loser) - Level(Winner)) = 2 - 7 = -5).
	 */
	@Test
	public void shouldReturn1AsProfitForWinner() {
		//given
		winningPlayer.setLevel(Level.ADVANCED);
		winningPlayer.setGamesPlayed(200);
		winningPlayer.setPoints(12000);
		winningPlayer.setGamesWon(110);
		loosingPlayer.setLevel(Level.WEAKLING);
		loosingPlayer.setPoints(400);
		loosingPlayer.setGamesPlayed(10);
		loosingPlayer.setGamesWon(2);
		statisticsTest = new PointsCalculator(winningPlayer, loosingPlayer);
		int properResult = 1;

		//when
		int resultBonusForWinner = statisticsTest.calculateWinnerProfit();
		
		//then
		assertEquals(properResult, resultBonusForWinner);
	}
	
	/**TestCase 2. Calculating profit for Winning player, when difference between 
	 * Losing Player (Advanced) and Winning (Weakling) is a positive number 
	 * (Level(loser) - Level(Winner)) = 7 - 2 = 5).
	 */
	@Test
	public void shouldReturn1124AsProfitForWinningPlayer() {
		//given
		loosingPlayer.setLevel(Level.ADVANCED);
		loosingPlayer.setGamesPlayed(200);
		loosingPlayer.setPoints(12000);
		loosingPlayer.setGamesWon(110);
		winningPlayer.setLevel(Level.WEAKLING);
		winningPlayer.setPoints(400);
		winningPlayer.setGamesPlayed(10);
		winningPlayer.setGamesWon(2);
		statisticsTest = new PointsCalculator(winningPlayer, loosingPlayer);
		int properResult = 1124;
		
		//when
		int resultBonusForWinner = statisticsTest.calculateWinnerProfit();
		
		//then
		assertEquals(properResult, resultBonusForWinner);
	}
	
	/**TestCase 3. Calculating loss for Losing player, when difference between 
	 * Losing Player (Weakling) and Winning (Advanced) is a negative number 
	 * (Level(loser) - Level(Winner)) = 2 - 7 = -5).   
	 */
	@Test
	public void shouldReturn2AsLossForLoser() {
		//given
		winningPlayer.setLevel(Level.ADVANCED);
		winningPlayer.setGamesPlayed(200);
		winningPlayer.setPoints(12000);
		winningPlayer.setGamesWon(110);
		loosingPlayer.setLevel(Level.WEAKLING);
		loosingPlayer.setPoints(400);
		loosingPlayer.setGamesPlayed(10);
		loosingPlayer.setGamesWon(2);
		statisticsTest = new PointsCalculator(winningPlayer, loosingPlayer);
		int properResult = 2;

		//when
		int resultBonusForLoser = statisticsTest.calculateLooserLoss();
		
		//then
		assertEquals(properResult, resultBonusForLoser);
	}
	
	/**TestCase 4. Calculating loss for Losing player, when difference between 
	 * Losing Player (Advanced) and Winning (Weakling) is a positive number 
	 * (Level(loser) - Level(Winner)) = 7 - 2 = 5).
	 */
	@Test
	public void shouldReturn844AsBonusPointsForLosingPlayer() {
		//given
		loosingPlayer.setLevel(Level.ADVANCED);
		loosingPlayer.setGamesPlayed(200);
		loosingPlayer.setPoints(12000);
		loosingPlayer.setGamesWon(110);
		winningPlayer.setLevel(Level.WEAKLING);
		winningPlayer.setPoints(400);
		winningPlayer.setGamesPlayed(10);
		winningPlayer.setGamesWon(2);
		statisticsTest = new PointsCalculator(winningPlayer, loosingPlayer);
		int properResult = 844;
		
		//when
		int resultBonusForLoser = statisticsTest.calculateLooserLoss();
		
		//then
		assertEquals(properResult, resultBonusForLoser);
	}
	
	@Test
	public void shouldReturn844AsPotentialLossForChallengingPlayer() {
		//given
		winningPlayer.setLevel(Level.ADVANCED);
		winningPlayer.setGamesPlayed(200);
		winningPlayer.setPoints(12000);
		winningPlayer.setGamesWon(110);
		loosingPlayer.setLevel(Level.WEAKLING);
		loosingPlayer.setPoints(400);
		loosingPlayer.setGamesPlayed(10);
		loosingPlayer.setGamesWon(2);
		statisticsTest = new PointsCalculator(winningPlayer, loosingPlayer);
		int properResult = 844;

		//when
		int resultBonusForLoser = statisticsTest.calculateChallengingPlayerPotentialLoss();
		
		//then
		assertEquals(properResult, resultBonusForLoser);
	}
	
}
