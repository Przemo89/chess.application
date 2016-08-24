package com.capgemini.chess.service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.chess.ChessApplication;
import com.capgemini.chess.algorithms.data.enums.Level;
import com.capgemini.chess.dao.ChallengeDao;
import com.capgemini.chess.dao.PlayerStatisticsDao;
import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;
import com.capgemini.chess.exception.ChallengeDataIntegrityViolationException;


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
	
	@Autowired
	private ChallengeDao challengeRepository;
	
	@Autowired
	private PlayerStatisticsDao playerStatisticsRepository;
	
	@Autowired
	private EntityManager entityManager;
	
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
	/**TestCase 1. Creation of manual challenge - if challenge already exists in
	 * @ChallengeTOTestList.PROPER_CHALLENGES_TO_TEST_LIST, then it should be only updated 
	 * with players' current levels and date.
	 */
	@Transactional
	@Test
	public void testShouldUpdateChallengeWhenCreateManualChallenge() throws Exception {
		//given
		final long idChallengeThatExist = 1L;
		ChallengeEntity challengeBeforeUpdate = challengeRepository.findOne(idChallengeThatExist);
		entityManager.detach(challengeBeforeUpdate);
		final long idChallengingPlayer = challengeBeforeUpdate.getPlayerChallenging().getId();
		final long idChallengedPlayer = challengeBeforeUpdate.getPlayerChallenged().getId();
		List<ChallengeEntity> challengesListBeforeChallengeCreation = challengeRepository.findAll();
		final int sizeBefore = challengesListBeforeChallengeCreation.size();
		
		//when
		TimeUnit.SECONDS.sleep(2);
		ChallengeEntity challengeAfterUpdate = challengeService.createManualChallenge(idChallengingPlayer, idChallengedPlayer);
		//then
		List<ChallengeEntity> challengesListAfterChallengeUpdate = challengeRepository.findAll();

		assertEquals(sizeBefore, challengesListAfterChallengeUpdate.size());
		assertEquals(idChallengingPlayer, challengeAfterUpdate.getPlayerChallenging().getId());
		assertEquals(idChallengedPlayer, challengeAfterUpdate.getPlayerChallenged().getId());
		Assert.assertNotEquals(challengeBeforeUpdate.getDateCreated(), challengeAfterUpdate.getDateCreated());
		Assert.assertNotEquals(challengeBeforeUpdate.getDateLastModified(), challengeAfterUpdate.getDateLastModified());
	}
	
	@Transactional
	@Test
	public void testShouldAddNewChallengeWhenCreateManualChallenge() throws Exception {
		//given
		final long idChallengingPlayer = 15L;
		final long idChallengedPlayer = 16L;
		List<ChallengeEntity> challengesListBeforeChallengeCreation = challengeRepository.findAll();
		final int sizeBefore = challengesListBeforeChallengeCreation.size();
		
		//when
		ChallengeEntity challengeSaved = challengeService.createManualChallenge(idChallengingPlayer, idChallengedPlayer);
		
		//then
		List<ChallengeEntity> challengesListAfterChallengeCreation = challengeRepository.findAll();
		assertEquals(sizeBefore + 1, challengesListAfterChallengeCreation.size());
		assertEquals(idChallengingPlayer, challengeSaved.getPlayerChallenging().getId());
		assertEquals(idChallengedPlayer, challengeSaved.getPlayerChallenged().getId());
	}
	
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

	@Test
	public void shouldThrowExceptionBecauseChallengeNotExist() throws Exception {
		//given
		long idChallengeNotExisting = 146L;
		
		//when
		thrown.expect(ChallengeDataIntegrityViolationException.class);
		thrown.expectMessage(" does not exist anymore!");

		//then
		challengeService.acceptChallenge(idChallengeNotExisting);
	}

	/**TestCase 11. Accepting challenge, when some of the involved players' level has changed, 
	 * should lead to throwing @ChallengeIsNoLongerValidException. 
	 */
	@Transactional
	@Test
	public void shouldThrowExceptionBecauseChallengingPlayerLevelChanged() throws Exception {
		//given
		final long idChallengeExisting = 27L;
		ChallengeEntity challenge = challengeRepository.findOne(idChallengeExisting);
		entityManager.detach(challenge);
		PlayerStatisticsEntity playerChallenging = challenge.getPlayerChallenging();
		playerChallenging.setLevel(Level.PROFESSIONAL);
		playerStatisticsRepository.update(playerChallenging);
		
		//when
		thrown.expect(ChallengeDataIntegrityViolationException.class);
		thrown.expectMessage(" is no longer valid, because players' level changed.");

		//then
		challengeService.acceptChallenge(idChallengeExisting);
	}
	
	@Transactional
	@Test
	public void shouldThrowExceptionBecauseChallengedPlayerLevelChanged() throws Exception {
		//given
		final long idChallengeExisting = 27L;
		ChallengeEntity challenge = challengeRepository.findOne(idChallengeExisting);
		entityManager.detach(challenge);
		PlayerStatisticsEntity playerChallenged = challenge.getPlayerChallenged();
		playerChallenged.setLevel(Level.MASTER);
		playerStatisticsRepository.update(playerChallenged);
		
		//when
		thrown.expect(ChallengeDataIntegrityViolationException.class);
		thrown.expectMessage(" is no longer valid, because players' level changed.");

		//then
		challengeService.acceptChallenge(idChallengeExisting);
	}
	
	@Transactional
	@Test
	public void shouldThrowExceptionBecauseBothPlayersLevelsChanged() throws Exception {
		//given
		final long idChallengeExisting = 27L;
		ChallengeEntity challenge = challengeRepository.findOne(idChallengeExisting);
		entityManager.detach(challenge);
		PlayerStatisticsEntity playerChallenging = challenge.getPlayerChallenging();
		PlayerStatisticsEntity playerChallenged = challenge.getPlayerChallenged();
		playerChallenging.setLevel(Level.PROFESSIONAL);
		playerStatisticsRepository.update(playerChallenging);
		playerChallenged.setLevel(Level.MASTER);
		playerStatisticsRepository.update(playerChallenged);
		
		//when
		thrown.expect(ChallengeDataIntegrityViolationException.class);
		thrown.expectMessage(" is no longer valid, because players' level changed.");

		//then
		challengeService.acceptChallenge(idChallengeExisting);
	}
//	
	/**TestCase 12. Retrieving list of challenges, which were sent by specific player,
	 * should always return some list with ChallengesTO. If there are no such challenges,
	 * the list should be empty.
	 */
	@Test
	public void testShouldReturnListWithSentChallenges() {
		//given
		final long idPlayer = 10L;
		int properSize = 5;
		
		//when
		List<ChallengeEntity> resultList = challengeService.getSentChallenges(idPlayer);
		boolean isListContainingOnlyChallengesSentByRequestingPlayer = true;
		
		for (ChallengeEntity challenge : resultList) {
			if (challenge.getPlayerChallenging().getId() != idPlayer) {
				isListContainingOnlyChallengesSentByRequestingPlayer = false;
			}
		}
		
		//then
		Assert.assertTrue(isListContainingOnlyChallengesSentByRequestingPlayer);
		Assert.assertEquals(properSize, resultList.size());
	}
	
	@Test
	public void testShouldReturnEmptyListWithSentChallenges() {
		//given
		final long idPlayer = 17L;
		
		//when
		List<ChallengeEntity> resultList = challengeService.getSentChallenges(idPlayer);
		boolean isListEmpty = resultList.isEmpty();
		
		//then
		Assert.assertTrue(isListEmpty);
	}

	@Test
	public void testShouldReturnListWithReceivedChallenges() {
		//given
		final long idPlayer = 10L;
		int properSize = 3;
		
		//when
		List<ChallengeEntity> resultList = challengeService.getReceivedChallenges(idPlayer);
		boolean isListContainingOnlyChallengesReceived = true;
		
		for (ChallengeEntity challenge : resultList) {
			if (challenge.getPlayerChallenged().getId() != idPlayer) {
				isListContainingOnlyChallengesReceived = false;
			}
		}
		
		//then
		Assert.assertTrue(isListContainingOnlyChallengesReceived);
		assertEquals(properSize, resultList.size());
	}
	
	@Test
	public void testShouldReturnEmptyListWithReceivedChallenges() {
		//given
		final long idPlayer = 16L;
		
		//when
		List<ChallengeEntity> resultList = challengeService.getReceivedChallenges(idPlayer);
		boolean isListEmpty = resultList.isEmpty();
		
		//then
		Assert.assertTrue(isListEmpty);
	}

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
