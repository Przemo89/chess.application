package com.capgemini.chess.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.chess.ChessApplication;
import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.exception.PlayerNotExistException;
import com.capgemini.chess.service.testcollections.ChallengeTOTestList;
import com.capgemini.chess.service.testcollections.PlayerStatisticsTOTestList;
import com.capgemini.chess.service.to.PlayerStatisticsTO;


/**TestCase 1. Creation of manual challenge - if challenge already exists in
 * @ChallengeTOTestList.PROPER_CHALLENGES_TO_TEST_LIST, then it should be only updated 
 * with players' current levels and date.
 * TestCase 2. Creation of manual challenge - if challenge does not exist in
 * @ChallengeTOTestList.PROPER_CHALLENGES_TO_TEST_LIST, then this ChallengeTO should be added 
 * to @ChallengeTOTestList.TEST_LIST_FOR_INSERTION_ONLY.
 * TestCase 3. Creation of manual challenge - any of Players does not exist in
 * @PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST, then should be thrown 
 * @PlayerNotExistException. 
 * to @ChallengeTOTestList.TEST_LIST_FOR_INSERTION_ONLY.
 * TestCase 4. Finding matching players for automatic challenge for specific requesting 
 * player should return list with maximum 5 entries, with players which have 
 * +2/+1/+0/-1/-2 levels in regard to requesting player.
 * TestCase 5. Finding matching players for automatic challenge for specific requesting 
 * player should return list without challenging player.
 * TestCase 6. Found matching players for automatic challenge for specific requesting 
 * player should have set up potential benefit/loss for challenging player.
 * Tests of calculation of benefit/profit has been created in @PointsCalculatorTest.
 * TestCase 7. If during finding matching players procedure challenging player won't be found
 * in DB, @PlayerNotExistException should be thrown.
 * TestCase 8. Accepting challenge, when both players' levels didn't change and they still exist in DB,
 * should lead to usage of @GameService.startMatch() method. Also, the challenge should 
 * be removed from DB.
 * TestCase 9. Accepting challenge, which has been deleted earlier from DB, 
 * should lead to throwing @ChallengeNotExistException.
 * TestCase 10. Accepting challenge, when some of the involved player does not exist anymore, 
 * should lead to throwing @PlayerNotExistException.
 * TestCase 11. Accepting challenge, when some of the involved players' level has changed, 
 * should lead to throwing @ChallengeIsNoLongerValidException.
 * TestCase 12. Retrieving list of challenges, which were sent by specific player,
 * should always return some list with ChallengesTO. If there are no such challenges,
 * the list should be empty.
 * TestCase 13. Retrieving list of challenges, which were received by specific player,
 * should always return some list with ChallengesTO. If there are no such challenges,
 * the list should be empty.
 * TestCase 14. Removing outdated challenges should cause deletion only of challenges,
 * which are older than 7 days.
 */

@SpringApplicationConfiguration(ChessApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
public class ChallengeServiceTest {
	
	@Autowired
	private ChallengeService challengeService;
	
	@InjectMocks
	private GameService gameService;
	
//	@Configuration
//	static class ChallengeServiceTestConfiguration {
//		@Bean
//		public GameService gameService() {
//			gameService = Mockito.mock(GameServiceImpl.class);
//			return gameService;
//		}
//		
//		@Bean
//		public ChallengeDao challengesDao() {
//			return new ChallengeDaoImpl();
//		}
//		
//		@Bean
//		public PlayerStatisticsDao playersStatisticsDao() {
//			return new PlayerStatisticsDaoImpl();
//		}
//		
//		@Bean
//		public ChallengeService challengeService() {
//			return new ChallengeServiceImpl();
//		}
//		
////		@Bean
////		public EntityManager entityManager() {
////			return new EntityMana;
////		}
//	}
	
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
//	@Before
//	public void instantiateTestListANew() {
//		ChallengeTOTestList.setInitialValuesOfProperChallengesWithPlayerStatisticsToTestList();
//		ChallengeTOTestList.setInitialValuesOfProperChallengesWithoutPlayerStatisticsToTestList();
//		ChallengeTOTestList.setInitialValuesOfInproperChallengesToTestList();
//		ChallengeTOTestList.setInitialValuesOfTestListForInsertionOnly();
//		ChallengeTOTestList.setInitialValuesOfOutdatedChallengesToTestList();
//		
//		PlayerStatisticsTOTestList.setInitialValuesOfPlayerStatisticsToTestList();
//	}
//	
//	/**TestCase 1. Creation of manual challenge - if challenge already exists in
//	 * @ChallengeTOTestList.PROPER_CHALLENGES_TO_TEST_LIST, then it should be only updated 
//	 * with players' current levels and date.
//	 */
//	@Test
//	public void shouldUpdateChallengeInProperChallengesToTestList() {
//		//given
//		ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(0).setLevelOfChallengingPlayer(Level.MASTER);
//		//when
//		challengeServiceTest.createManualChallenge(345, 3445);
//		boolean isTestListEmpty = ChallengeTOTestList.TEST_LIST_FOR_INSERTION_ONLY.isEmpty();
//
//		//then
//		Assert.assertTrue(isTestListEmpty);
//		Assert.assertEquals(Level.NEWBIE, 
//				ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(0).getChallengedPlayer().getLevel());
//	}
	
	/**TestCase 2. Creation of manual challenge - if challenge does not exist in
	 * PROPER_CHALLENGES_TO_TEST_LIST, then this ChallengeTO should be added 
	 * to @ChallengeTOTestList.TEST_LIST_FOR_INSERTION_ONLY.
	 */
	@Test
	public void shouldAddNewChallenge() throws Exception {
		//given
		final long idChallengingPlayer = 15L;
		final long idChallengedPlayer = 16L;
		
		//when
		ChallengeEntity challengeSaved = challengeService.createManualChallenge(idChallengingPlayer, idChallengedPlayer);

		//then
//		Assert.assertFalse(isTestListEmpty);
//		Assert.assertEquals(properIdChallenge, ChallengeTOTestList.TEST_LIST_FOR_INSERTION_ONLY.get(0).getIdChallenge());
//		Assert.assertEquals(challengingPlayer.getLevel(), ChallengeTOTestList.TEST_LIST_FOR_INSERTION_ONLY
//				.get(0).getLevelOfChallengingPlayer());
//		Assert.assertEquals(challengedPlayer.getLevel(), ChallengeTOTestList.TEST_LIST_FOR_INSERTION_ONLY
//				.get(0).getLevelOfChallengedPlayer());
	}
	
	/**TestCase 3. Creation of manual challenge - any of Players does not exist in
	 * @PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST, then should be thrown 
	 * @PlayerInThisChallengeNotExistAnymoreException. 
	 */
	@Ignore
	public void shouldThrowPlayerInThisChallengeNotExistAnymoreException() {
		//given
		PlayerStatisticsTO challengingPlayer = PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.get(12);
		PlayerStatisticsTO challengedPlayer = new PlayerStatisticsTO();
		challengedPlayer.setId(2L);
		//then
		thrown.expect(PlayerNotExistException.class);
		thrown.expectMessage("Player does not exist anymore.");
		//when
//		challengeServiceTest.createManualChallenge(challengingPlayer.getId(), challengedPlayer.getId());
	}
	
//	@Test
//	public void shouldThrowPlayerInThisChallengeNotExistAnymoreExceptionAgain() {
//		//given
//		PlayerStatisticsTO challengedPlayer = PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.get(12);
//		PlayerStatisticsTO challengingPlayer = new PlayerStatisticsTO();
//		challengedPlayer.setId(2L);
//		//then
//		thrown.expect(PlayerNotExistException.class);
//		thrown.expectMessage("Player does not exist anymore.");
//		//when
//		challengeServiceTest.createManualChallenge(challengingPlayer.getId(), challengedPlayer.getId());
//	}
//	
//	/**TestCase 4. Finding matching players for automatic challenge for specific requesting 
//	 * player should return list with maximum 5 entries, with players which have 
//	 * +2/+1/+0/-1/-2 levels in regard to requesting player.
//	 */
//	@Test
//	public void shouldReturnListOfMatchingPlayersWith5Entries() {
//		//given
//		PlayerStatisticsTO challengingPlayer = PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.get(4);
//		
//		//when
//		List<PlayerStatisticsTO> resultList = challengeServiceTest.getMatchingPlayers(challengingPlayer.getId());
//		int properSize = 5;
//		boolean isPlayersWithOnlyProperLevels = true;
//		for (PlayerStatisticsTO player : resultList) {
//			if (Math.abs(player.getLevel().getValue() - challengingPlayer.getLevel().getValue()) > 2) {
//				isPlayersWithOnlyProperLevels = false;
//			}
//		}
//		//then
//		Assert.assertEquals(properSize, resultList.size());
//		Assert.assertTrue(isPlayersWithOnlyProperLevels);
//	}
//	
//	/**TestCase 5. Finding matching players for automatic challenge for specific requesting 
//	 * player should return list without challenging player.
//	 */
//	@Test
//	public void shouldReturnListWithoutChallengingPlayer() {
//		//given
//		PlayerStatisticsTO challengingPlayer = PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.get(4);
//		
//		//when
//		List<PlayerStatisticsTO> resultList = challengeServiceTest.getMatchingPlayers(challengingPlayer.getId());
//		boolean isListContainingChallengingPlayer = false;
//		for (PlayerStatisticsTO player : resultList) {
//			if (player.getId() == challengingPlayer.getId()) {
//				isListContainingChallengingPlayer = true;
//			}
//		}
//		//then
//		Assert.assertFalse(isListContainingChallengingPlayer);
//	}
//	
//	/**TestCase 6. Found matching players for automatic challenge for specific requesting 
//	 * player should have set up potential benefit/loss for challenging player.
//	 * Tests of calculation of benefit/profit has been created in @PointsCalculatorTest.
//	 */
//	@Test
//	public void shouldReturnListOfMatchingPlayersWithFilledBenefitLoss() {
//		//given
//		PlayerStatisticsTO challengingPlayer = PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.get(4);
//		
//		//when
//		List<PlayerStatisticsTO> resultList = challengeServiceTest.getMatchingPlayers(challengingPlayer.getId());
//		
//		//then
//		for (PlayerStatisticsTO player : resultList) {
//			Assert.assertTrue(player.getPotentialBenefitForChallengingPlayer() > 0);
//			Assert.assertTrue(player.getPotentialLossForChallengingPlayer() > 0);
//		}
//	}
//	
//	/**TestCase 7. If during finding matching players procedure challenging player won't be found
//	 * in DB, @PlayerNotExistException should be thrown.
//	 */
//	@Test
//	public void shouldThrowExceptionWhenGettingMatchingPlayers() {
//		//given
//		PlayerStatisticsTO challengingPlayer = new PlayerStatisticsTO();
//		challengingPlayer.setId(36L);
//		//then
//		thrown.expect(PlayerNotExistException.class);
//		thrown.expectMessage("Player does not exist anymore.");
//		//when
//		challengeServiceTest.getMatchingPlayers(challengingPlayer.getId());
//	}
//	
//	/**TestCase 8. Accepting challenge, when both players' levels didn't change and they still exist in DB,
//	 * should lead to usage of @GameService.startMatch() method. Also, the challenge should be removed from DB.
//	 */
//	@Test
//	public void shouldUseStartMatchMethod() throws PlayerNotExistException, ChallengeNotExistException, ChallengeIsNoLongerValidException {
//		//given
//		ChallengeTO properChallenge = ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(0);
//		
//		challengeServiceTest.acceptChallenge(properChallenge.getIdChallenge());
//		
//		//then
//		Assert.assertFalse(ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.contains(properChallenge));
//		Mockito.verify(gameService, Mockito.atLeastOnce()).startMatch(Mockito.any(ChallengeTO.class));
//	}
//	
//	/**TestCase 9. Accepting challenge, which has been deleted from DB, 
//	 * should lead to throwing @ChallengeNotExistException.
//	 */
//	@Test
//	public void shouldThrowExceptionBecauseChallengeNotExist() throws PlayerNotExistException, 
//	ChallengeNotExistException, ChallengeIsNoLongerValidException {
//		//given
//		long idOfRemovedChallenge = ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(0).getIdChallenge();
//		ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.remove(0);
//		
//		//when
//		thrown.expect(ChallengeNotExistException.class);
//		thrown.expectMessage("Challenge with id: ");
//
//		//then
//		challengeServiceTest.acceptChallenge(idOfRemovedChallenge);
//	}
//	
//	/**TestCase 10. Accepting challenge, when some of the involved player does not exist anymore, 
//	 * should lead to throwing @PlayerNotExistException.
//	 */
//	@Test
//	public void shouldThrowExceptionBecauseChallengingPlayerNotExist() throws PlayerNotExistException, 
//	ChallengeNotExistException, ChallengeIsNoLongerValidException {
//		//given
//		ChallengeTO challengeTOWithoutPlayer = ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(0);
//		challengeTOWithoutPlayer.setChallengingPlayer(new PlayerStatisticsTO());
//		
//		//when
//		thrown.expect(PlayerNotExistException.class);
//		thrown.expectMessage("Player does not exist anymore.");
//
//		//then
//		challengeServiceTest.acceptChallenge(challengeTOWithoutPlayer.getIdChallenge());
//	}
//	
//	/**TestCase 11. Accepting challenge, when some of the involved players' level has changed, 
//	 * should lead to throwing @ChallengeIsNoLongerValidException. 
//	 */
//	@Test
//	public void shouldThrowExceptionBecausePlayersLevelChanged() throws PlayerNotExistException, 
//	ChallengeNotExistException, ChallengeIsNoLongerValidException {
//		//given
//		ChallengeTO challengeTOWithoutPlayer = ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(0);
//		challengeTOWithoutPlayer.getChallengedPlayer().setLevel(Level.PROFESSIONAL);
//		
//		//when
//		thrown.expect(ChallengeIsNoLongerValidException.class);
//		thrown.expectMessage("Challenge with id: ");
//
//		//then
//		challengeServiceTest.acceptChallenge(challengeTOWithoutPlayer.getIdChallenge());
//	}
//	
//	/**TestCase 12. Retrieving list of challenges, which were sent by specific player,
//	 * should always return some list with ChallengesTO. If there are no such challenges,
//	 * the list should be empty.
//	 */
//	@Test
//	public void shouldReturnListWith3EntriesOfSentChallenges() {
//		//given
//		PlayerStatisticsTO requestingPlayer = ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST
//				.get(0).getChallengingPlayer();
//		int properSize = 3;
//		
//		//when
//		List<ChallengeTO> resultList = challengeServiceTest.getSentChallenges(requestingPlayer.getId());
//		boolean isListContainingOnlyChallengesSentByRequestingPlayer = true;
//		for (ChallengeTO challenge : resultList) {
//			if (challenge.getIdOfChallengingPlayer() != requestingPlayer.getId()) {
//				isListContainingOnlyChallengesSentByRequestingPlayer = false;
//			}
//		}
//		
//		//then
//		Assert.assertTrue(isListContainingOnlyChallengesSentByRequestingPlayer);
//		Assert.assertEquals(properSize, resultList.size());
//	}
//	
//	@Test
//	public void shouldReturnListWithNoEntriesOfSentChallenges() {
//		//given
//		long idPlayerWhichNotHaveSentChallenges = 34405598L;
//		int properSize = 0;
//		
//		//when
//		List<ChallengeTO> resultList = challengeServiceTest.getSentChallenges(idPlayerWhichNotHaveSentChallenges);
//		boolean isListEmpty = resultList.isEmpty();
//		
//		//then
//		Assert.assertTrue(isListEmpty);
//		Assert.assertEquals(properSize, resultList.size());
//	}
//	
//	/**TestCase 13. Retrieving list of challenges, which were received by specific player,
//	 * should always return some list with ChallengesTO. If there are no such challenges,
//	 * the list should be empty.
//	 */
//	@Test
//	public void shouldReturnListWith2EntriesOfReceivedChallenges() {
//		//given
//		PlayerStatisticsTO requestingPlayer = ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST
//				.get(4).getChallengedPlayer();
//		int properSize = 2;
//		
//		//when
//		List<ChallengeTO> resultList = challengeServiceTest.getReceivedChallenges(requestingPlayer.getId());
//		boolean isListContainingOnlyChallengesReceivedByRequestingPlayer = true;
//		for (ChallengeTO challenge : resultList) {
//			if (challenge.getIdOfChallengedPlayer() != requestingPlayer.getId()) {
//				isListContainingOnlyChallengesReceivedByRequestingPlayer = false;
//			}
//		}
//		
//		//then
//		Assert.assertTrue(isListContainingOnlyChallengesReceivedByRequestingPlayer);
//		Assert.assertEquals(properSize, resultList.size());
//	}
//
//	@Test
//	public void shouldReturnEmptyListOfReceivedChallenges() {
//		//given
//		PlayerStatisticsTO requestingPlayer = ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST
//				.get(3).getChallengingPlayer();
//		
//		//when
//		List<ChallengeTO> resultList = challengeServiceTest.getReceivedChallenges(requestingPlayer.getId());
//		boolean isListEmpty = resultList.isEmpty();
//		
//		//then
//		Assert.assertTrue(isListEmpty);
//	}
//	
//	/**TestCase 14. Removing outdated challenges should cause deletion only of challenges,
//	 * which are older than 7 days.
//	 */
//	@Test
//	public void shouldReturnListWithChallengesWhichAreNotOutdated() {
//		//given
//		LocalDate earliestProperDayOfCreationChallenge = LocalDate.now().minusDays(7);
//		//when
//		challengeServiceTest.removeOutdatedChallenges();
//		
//		//then
//		for (ChallengeTO challenge : ChallengeTOTestList.OUTDATED_CHALLENGES_TEST_LIST) {
//			Assert.assertFalse(challenge.getDateOfChallengeCreation().isBefore(earliestProperDayOfCreationChallenge));
//		}
//	}

	
}
