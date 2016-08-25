package com.capgemini.chess.domain.statistics;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.capgemini.chess.algorithms.data.enums.GameResult;
import com.capgemini.chess.algorithms.data.enums.Level;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;
import com.capgemini.chess.exception.InvalidGameResultException;

/**TestCase 1. Attempt to set game result for both players as WON should throw @InvalidGameResultException.
 * TestCase 2. Attempt to set game result for both players as LOST should throw @InvalidGameResultException.
 * TestCase 3. Attempt to set game result for one of the players as draw 
 * and for second one some other game result, should throw @InvalidGameResultException.
 * TestCase 4. In case of draw after which players should not change their levels, 
 * only games played and games draw should be incremented for both players.
 * TestCase 5. In case of draw after which players should change their levels, 
 * games played, games draw should be incremented for both players and their levels updated.
 * TestCase 6. In case of game, which ended with Check Mate, Points, Games played, games won/lost 
 * (Winner/Looser accordingly) and levels for both players should be updated.
 */
public class PlayersStatisticsUpdateTest {

	private PlayerStatisticsEntity playerOne;
	private PlayerStatisticsEntity playerTwo;
	private GameResult gameResultForPlayerOne;
	private GameResult gameResultForLoosingTwo;
	private PlayersStatisticsUpdate test;
	
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
		this.test = null;
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
		test = new PlayersStatisticsUpdate(playerOne, gameResultForPlayerOne, playerTwo, gameResultForLoosingTwo);
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
		test = new PlayersStatisticsUpdate(playerOne, gameResultForPlayerOne, playerTwo, gameResultForLoosingTwo);
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
		test = new PlayersStatisticsUpdate(playerOne, gameResultForPlayerOne, playerTwo, gameResultForLoosingTwo);
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
		test = new PlayersStatisticsUpdate(playerOne, gameResultForPlayerOne, playerTwo, gameResultForLoosingTwo);
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
		test = new PlayersStatisticsUpdate(playerOne, gameResultForPlayerOne, playerTwo, gameResultForLoosingTwo);
		
		//when
		test.updatePlayersStatistics();
		
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
		test = new PlayersStatisticsUpdate(playerOne, gameResultForPlayerOne, playerTwo, gameResultForLoosingTwo);
		
		//when
		test.updatePlayersStatistics();
		
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
		test = new PlayersStatisticsUpdate(playerOne, gameResultForPlayerOne, playerTwo, gameResultForLoosingTwo);
		
		//when
		test.updatePlayersStatistics();
		
		//then
		assertEquals(245, playerOne.getGamesPlayed());
		assertEquals(137, playerOne.getGamesWon());
		assertEquals(51, playerOne.getGamesLost());
		assertEquals(Level.EXPERIENCED_MIDDLEBORW, playerOne.getLevel());
		assertEquals(Level.WEAKLING, playerTwo.getLevel());
	}
	
}
