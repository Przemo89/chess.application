package com.capgemini.chess.service;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.chess.ChessApplication;
import com.capgemini.chess.algorithms.data.enums.GameResult;
import com.capgemini.chess.algorithms.data.enums.Level;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;
import com.capgemini.chess.exception.InvalidGameResultException;
import com.capgemini.chess.service.impl.PlayerStatisticsServiceImpl;

@SpringApplicationConfiguration(ChessApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PlayerStatisticsServiceTest {

	private PlayerStatisticsEntity playerOne;
	private PlayerStatisticsEntity playerTwo;
	private GameResult gameResultForPlayerOne;
	private GameResult gameResultForLoosingTwo;
	
	@Autowired
	private PlayerStatisticsServiceImpl playerStatisticsService;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void instantiatePlayerObjects() {
		playerOne = new PlayerStatisticsEntity();
		playerTwo = new PlayerStatisticsEntity();
		playerOne.setLevel(Level.ADVANCED);
		playerOne.setGamesPlayed(200);
		playerOne.setPoints(12000);
		playerOne.setGamesWon(110);
		playerOne.setGamesLost(50);
		playerTwo.setLevel(Level.WEAKLING);
		playerTwo.setPoints(400);
		playerTwo.setGamesPlayed(10);
		playerTwo.setGamesWon(2);
		playerTwo.setGamesLost(1);
	}
	
	@After
	public void resetAllInstances() {
		this.gameResultForPlayerOne = null;
		this.gameResultForLoosingTwo = null;
	}
	
	@Test
	public void shouldReturn1AsProfitForWinner() {
		//given
		PlayerStatisticsEntity winningPlayer = new PlayerStatisticsEntity();
		PlayerStatisticsEntity loosingPlayer = new PlayerStatisticsEntity();
		winningPlayer.setLevel(Level.ADVANCED);
		winningPlayer.setGamesPlayed(200);
		winningPlayer.setPoints(12000);
		winningPlayer.setGamesWon(110);
		loosingPlayer.setLevel(Level.WEAKLING);
		loosingPlayer.setPoints(400);
		loosingPlayer.setGamesPlayed(10);
		loosingPlayer.setGamesWon(2);
		int properResult = 1;

		//when
		int resultBonusForWinner = playerStatisticsService.calculateWinnerProfit(winningPlayer, loosingPlayer);
		
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
		PlayerStatisticsEntity winningPlayer = new PlayerStatisticsEntity();
		PlayerStatisticsEntity loosingPlayer = new PlayerStatisticsEntity();
		loosingPlayer.setLevel(Level.ADVANCED);
		loosingPlayer.setGamesPlayed(200);
		loosingPlayer.setPoints(12000);
		loosingPlayer.setGamesWon(110);
		winningPlayer.setLevel(Level.WEAKLING);
		winningPlayer.setPoints(400);
		winningPlayer.setGamesPlayed(10);
		winningPlayer.setGamesWon(2);
		int properResult = 1124;
		
		//when
		int resultBonusForWinner = playerStatisticsService.calculateWinnerProfit(winningPlayer, loosingPlayer);
		
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
		PlayerStatisticsEntity winningPlayer = new PlayerStatisticsEntity();
		PlayerStatisticsEntity loosingPlayer = new PlayerStatisticsEntity();
		winningPlayer.setLevel(Level.ADVANCED);
		winningPlayer.setGamesPlayed(200);
		winningPlayer.setPoints(12000);
		winningPlayer.setGamesWon(110);
		loosingPlayer.setLevel(Level.WEAKLING);
		loosingPlayer.setPoints(400);
		loosingPlayer.setGamesPlayed(10);
		loosingPlayer.setGamesWon(2);
		int properResult = 2;

		//when
		int resultBonusForLoser = playerStatisticsService.calculateLooserLoss(winningPlayer, loosingPlayer);
		
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
		PlayerStatisticsEntity winningPlayer = new PlayerStatisticsEntity();
		PlayerStatisticsEntity loosingPlayer = new PlayerStatisticsEntity();
		loosingPlayer.setLevel(Level.ADVANCED);
		loosingPlayer.setGamesPlayed(200);
		loosingPlayer.setPoints(12000);
		loosingPlayer.setGamesWon(110);
		winningPlayer.setLevel(Level.WEAKLING);
		winningPlayer.setPoints(400);
		winningPlayer.setGamesPlayed(10);
		winningPlayer.setGamesWon(2);
		int properResult = 844;
		
		//when
		int resultBonusForLoser = playerStatisticsService.calculateLooserLoss(winningPlayer, loosingPlayer);
		
		//then
		assertEquals(properResult, resultBonusForLoser);
	}
	
	@Test
	public void shouldReturn844AsPotentialLossForChallengingPlayer() {
		//given
		PlayerStatisticsEntity winningPlayer = new PlayerStatisticsEntity();
		PlayerStatisticsEntity loosingPlayer = new PlayerStatisticsEntity();
		winningPlayer.setLevel(Level.ADVANCED);
		winningPlayer.setGamesPlayed(200);
		winningPlayer.setPoints(12000);
		winningPlayer.setGamesWon(110);
		loosingPlayer.setLevel(Level.WEAKLING);
		loosingPlayer.setPoints(400);
		loosingPlayer.setGamesPlayed(10);
		loosingPlayer.setGamesWon(2);
		int properResult = 844;

		//when
		int resultBonusForLoser = playerStatisticsService.calculateChallengingPlayerPotentialLoss(winningPlayer, loosingPlayer);
		
		//then
		assertEquals(properResult, resultBonusForLoser);
	}
	
	@Test
	public void shouldThrowInvalidGameResultExceptionWhenProviding2GameResultsAsWon() {
		//given
		gameResultForPlayerOne = GameResult.WON;
		gameResultForLoosingTwo = GameResult.WON;
		
		//then
		thrown.expect(InvalidGameResultException.class);
		thrown.expectMessage("Invalid Game Result provided.");
		
		//when
		playerStatisticsService.updatePlayersStatistics(playerOne, playerTwo, gameResultForPlayerOne, gameResultForLoosingTwo);
	}
	
	@Test
	public void shouldThrowInvalidGameResultExceptionWhenProviding2GameResultsAsLost() {
		//given
		gameResultForPlayerOne = GameResult.LOST;
		gameResultForLoosingTwo = GameResult.LOST;
		
		//then
		thrown.expect(InvalidGameResultException.class);
		thrown.expectMessage("Invalid Game Result provided.");
		
		//when
		playerStatisticsService.updatePlayersStatistics(playerOne, playerTwo, gameResultForPlayerOne, gameResultForLoosingTwo);
	}
	
	@Test
	public void shouldThrowInvalidGameResultExceptionWhenProvidingGameResultsAsDrawAndWon() {
		//given
		gameResultForPlayerOne = GameResult.DRAW;
		gameResultForLoosingTwo = GameResult.WON;
		
		//then
		thrown.expect(InvalidGameResultException.class);
		thrown.expectMessage("Invalid Game Result provided.");
		
		//when
		playerStatisticsService.updatePlayersStatistics(playerOne, playerTwo, gameResultForPlayerOne, gameResultForLoosingTwo);
	}
	
	@Test
	public void shouldThrowInvalidGameResultExceptionWhenProvidingGameResultsAsDrawAndLost() {
		//given
		gameResultForPlayerOne = GameResult.LOST;
		gameResultForLoosingTwo = GameResult.DRAW;
		
		//then
		thrown.expect(InvalidGameResultException.class);
		thrown.expectMessage("Invalid Game Result provided.");
		
		//when
		playerStatisticsService.updatePlayersStatistics(playerOne, playerTwo, gameResultForPlayerOne, gameResultForLoosingTwo);
	}
	
	@Test
	public void shouldUpdateTotalPointsGamesPlayedAndWonForBothPlayersButLevelShouldStayTheSame() {
		//given
		playerTwo.setLevel(Level.ADVANCED);
		playerTwo.setGamesPlayed(200);
		playerTwo.setPoints(12000);
		playerTwo.setGamesWon(110);
		playerTwo.setGamesLost(50);
		gameResultForPlayerOne = GameResult.WON;
		gameResultForLoosingTwo = GameResult.LOST;
		
		//when
		playerStatisticsService.updatePlayersStatistics(playerOne, playerTwo, gameResultForPlayerOne, gameResultForLoosingTwo);
		
		//then
		assertEquals(201, playerOne.getGamesPlayed());
		assertEquals(111, playerOne.getGamesWon());
		assertEquals(50, playerOne.getGamesLost());
		assertEquals(Level.ADVANCED, playerOne.getLevel());
		assertEquals(201, playerTwo.getGamesPlayed());
		assertEquals(110, playerTwo.getGamesWon());
		assertEquals(51, playerTwo.getGamesLost());
		assertEquals(Level.ADVANCED, playerOne.getLevel());
	}
	
	@Test
	public void shouldUpdateTotalPointsGamesPlayedWonLostForBothPlayersAndLevelShouldGoUpForWinningPlayer() {
		//given
		playerOne.setLevel(Level.ADVANCED);
		playerOne.setGamesPlayed(244);
		playerOne.setPoints(19200);
		playerOne.setGamesWon(137);
		playerOne.setGamesLost(50);
		playerTwo.setLevel(Level.ADVANCED);
		playerTwo.setGamesPlayed(200);
		playerTwo.setPoints(12000);
		playerTwo.setGamesWon(110);
		playerTwo.setGamesLost(50);
		gameResultForPlayerOne = GameResult.WON;
		gameResultForLoosingTwo = GameResult.LOST;
		
		//when
		playerStatisticsService.updatePlayersStatistics(playerOne, playerTwo, gameResultForPlayerOne, gameResultForLoosingTwo);
		
		//then
		assertEquals(245, playerOne.getGamesPlayed());
		assertEquals(138, playerOne.getGamesWon());
		assertEquals(50, playerOne.getGamesLost());
		assertEquals(Level.PROFESSIONAL, playerOne.getLevel());
		assertEquals(201, playerTwo.getGamesPlayed());
		assertEquals(110, playerTwo.getGamesWon());
		assertEquals(51, playerTwo.getGamesLost());
		assertEquals(Level.ADVANCED, playerTwo.getLevel());
	}
	
	@Test
	public void shouldUpdateTotalPointsGamesPlayedWonLostForBothPlayersAndLevelShouldGoDownForLoosingPlayer() {
		//given
		playerOne.setLevel(Level.ADVANCED);
		playerOne.setGamesPlayed(244);
		playerOne.setPoints(9602);
		playerOne.setGamesWon(137);
		playerOne.setGamesLost(50);

		gameResultForPlayerOne = GameResult.LOST;
		gameResultForLoosingTwo = GameResult.WON;
		
		//when
		playerStatisticsService.updatePlayersStatistics(playerOne, playerTwo, gameResultForPlayerOne, gameResultForLoosingTwo);
		
		//then
		assertEquals(245, playerOne.getGamesPlayed());
		assertEquals(137, playerOne.getGamesWon());
		assertEquals(51, playerOne.getGamesLost());
		assertEquals(Level.EXPERIENCED_MIDDLEBORW, playerOne.getLevel());
		assertEquals(Level.WEAKLING, playerTwo.getLevel());
	}
	
}
