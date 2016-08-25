package com.capgemini.chess.service;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.capgemini.chess.exception.PlayerStatisticsDataIntegrityViolationException;
import com.capgemini.chess.exception.PlayerNotExistException;


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
public class ChallengeServiceTest {
	
	@Autowired
	private ChallengeService challengeService;
	
	@Autowired
	private ChallengeDao challengeRepository;
	
	@Autowired
	private PlayerStatisticsDao playerStatisticsRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
		
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
		ChallengeEntity challengeAfterUpdate = challengeService.createChallenge(idChallengingPlayer, idChallengedPlayer);
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
		ChallengeEntity challengeSaved = challengeService.createChallenge(idChallengingPlayer, idChallengedPlayer);
		
		//then
		List<ChallengeEntity> challengesListAfterChallengeCreation = challengeRepository.findAll();
		assertEquals(sizeBefore + 1, challengesListAfterChallengeCreation.size());
		assertEquals(idChallengingPlayer, challengeSaved.getPlayerChallenging().getId());
		assertEquals(idChallengedPlayer, challengeSaved.getPlayerChallenged().getId());
	}
	
	@Test
	public void shouldReturnListWithPlayersProperLevelsWhenGettingMatchingPlayers() throws Exception {
		//given
		final long idPlayerChallengingExisting = 12L;
		PlayerStatisticsEntity playerChallenging = playerStatisticsRepository.findOne(idPlayerChallengingExisting);
		int levelValuePlayerChallenging = playerChallenging.getLevel().getValue();
		
		//when
		List<PlayerStatisticsEntity> resultList = challengeService.getMatchingPlayers(idPlayerChallengingExisting);
		final int properSize = 5;
		boolean isPlayersWithOnlyProperLevels = true;
		for (PlayerStatisticsEntity player : resultList) {
			if (Math.abs(player.getLevel().getValue() - levelValuePlayerChallenging) > 2) {
				isPlayersWithOnlyProperLevels = false;
			}
		}
		
		//then
		Assert.assertEquals(properSize, resultList.size());
		Assert.assertTrue(isPlayersWithOnlyProperLevels);
	}
	
	@Test
	public void shouldReturnListWithPlayersNullifiedPasswordsWhenGettingMatchingPlayers() throws Exception {
		//given
		final long idPlayerChallengingExisting = 12L;
		final long rankingPositionChuckNorris = 1L;
		final long rankingPositionMaster = 2L;
		
		//when
		List<PlayerStatisticsEntity> resultList = challengeService.getMatchingPlayers(idPlayerChallengingExisting);
		final int properSize = 5;

		//then
		Assert.assertEquals(properSize, resultList.size());
		for (PlayerStatisticsEntity player : resultList) {
			if (player.getLevel() == Level.MASTER) {
				Assert.assertEquals(rankingPositionMaster, player.getRankingPosition());
			}
			if (player.getLevel() == Level.CHUCK_NORRIS_OF_CHESS) {
				Assert.assertEquals(rankingPositionChuckNorris, player.getRankingPosition());
			}
		}
	}
	
	@Test
	public void testShouldReturnListWithoutChallengingPlayerWhenGettingMatchingPlayers() throws Exception {
		//given
		final long idPlayerChallengingExistingLevelChuckNorris = 14L;
		final int properSize = 2;
		
		//when
		List<PlayerStatisticsEntity> resultList = challengeService.getMatchingPlayers(idPlayerChallengingExistingLevelChuckNorris);
		boolean isListContainingChallengingPlayer = false;
		for (PlayerStatisticsEntity player : resultList) {
			if (player.getId() == idPlayerChallengingExistingLevelChuckNorris) {
				isListContainingChallengingPlayer = true;
			}
		}
		
		//then
		Assert.assertFalse(isListContainingChallengingPlayer);
		Assert.assertEquals(properSize, resultList.size());
	}
	
	@Test
	public void testShouldReturnListOfMatchingPlayersWithFilledBenefitLoss() throws Exception {
		//given
		final long idPlayerChallengingExisting = 12L;
		
		//when
		List<PlayerStatisticsEntity> resultList = challengeService.getMatchingPlayers(idPlayerChallengingExisting);
		final int properSize = 5;
		
		//then
		for (PlayerStatisticsEntity player : resultList) {
			Assert.assertTrue(player.getPotentialBenefitForChallengingPlayer() > 0);
			Assert.assertTrue(player.getPotentialLossForChallengingPlayer() > 0);
		}
		Assert.assertEquals(properSize, resultList.size());
	}
	
	@Transactional
	@Test
	public void testShouldThrowExceptionWhenGettingMatchingPlayers() throws Exception {
		//given
		final long idPlayerChallengingNotExisting = 5743234L;

		//then
		thrown.expect(PlayerNotExistException.class);
		thrown.expectMessage(" does not exist anymore.");
		
		//when
		challengeService.getMatchingPlayers(idPlayerChallengingNotExisting);
	}

	@Test
	public void testShouldThrowExceptionBecauseChallengeNotExist() throws Exception {
		//given
		long idChallengeNotExisting = 146L;
		
		//when
		thrown.expect(ChallengeDataIntegrityViolationException.class);
		thrown.expectMessage(" does not exist anymore!");

		//then
		challengeService.acceptChallenge(idChallengeNotExisting);
	}

	@Transactional
	@Test
	public void testShouldThrowExceptionBecauseChallengingPlayerLevelChanged() throws Exception {
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
	public void testShouldThrowExceptionBecauseChallengedPlayerLevelChanged() throws Exception {
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
	public void testShouldThrowExceptionBecauseBothPlayersLevelsChanged() throws Exception {
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
	
	@Transactional
	@Test
	public void testShouldDeleteChallengeWhenDeclineChallenge() {
		//given
		final long idChallengeExisting = 39L;
		ChallengeEntity challengeToDelete = challengeRepository.findOne(idChallengeExisting);
		List<ChallengeEntity> challengesAllBeforeDecline = challengeRepository.findAll();
		final int challengesNumberBeforeDecline = challengesAllBeforeDecline.size();
		
		//when
		challengeService.declineChallenge(challengeToDelete);
		List<ChallengeEntity> challengesAllAfterDecline = challengeRepository.findAll();
		
		//then
		Assert.assertEquals(challengesNumberBeforeDecline - 1, challengesAllAfterDecline.size());
		Assert.assertNull(challengeRepository.findOne(idChallengeExisting));
	}

	
	/**TestCase 14. Removing outdated challenges should cause deletion only of challenges,
	 * which are older than 7 days.
	 */
	@Transactional
	@Test
	public void testShouldDeleteOutdatedChallenges() throws Exception {
		//given
		final int properDeletedChallengesNumber = 3;
		final long idPlayerWithNoChallengesOne = 15L;
		final long idPlayerWithNoChallengesTwo = 16L;
		final long idPlayerWithNoChallengesThree = 17L;
		PlayerStatisticsEntity playerWithNoChallengesOne = playerStatisticsRepository.findOne(idPlayerWithNoChallengesOne);
		PlayerStatisticsEntity playerWithNoChallengesTwo = playerStatisticsRepository.findOne(idPlayerWithNoChallengesTwo);
		PlayerStatisticsEntity playerWithNoChallengesThree = playerStatisticsRepository.findOne(idPlayerWithNoChallengesThree);
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		Date dateCreatedOld = (Date) formatter.parse("2016-08-10"); 
		
		ChallengeEntity challengeOutdatedOne = new ChallengeEntity();
		challengeOutdatedOne.setDateCreated(dateCreatedOld);
		challengeOutdatedOne.setPlayerChallenging(playerWithNoChallengesOne);
		challengeOutdatedOne.setLevelPlayerChallenging(playerWithNoChallengesOne.getLevel());
		challengeOutdatedOne.setPlayerChallenged(playerWithNoChallengesTwo);
		challengeOutdatedOne.setLevelPlayerChallenged(playerWithNoChallengesTwo.getLevel());
		
		ChallengeEntity challengeOutdatedTwo = new ChallengeEntity();
		challengeOutdatedTwo.setDateCreated(dateCreatedOld);
		challengeOutdatedTwo.setPlayerChallenging(playerWithNoChallengesThree);
		challengeOutdatedTwo.setLevelPlayerChallenging(playerWithNoChallengesThree.getLevel());
		challengeOutdatedTwo.setPlayerChallenged(playerWithNoChallengesTwo);
		challengeOutdatedTwo.setLevelPlayerChallenged(playerWithNoChallengesTwo.getLevel());
		
		ChallengeEntity challengeOutdatedThree = new ChallengeEntity();
		challengeOutdatedThree.setDateCreated(dateCreatedOld);
		challengeOutdatedThree.setPlayerChallenging(playerWithNoChallengesTwo);
		challengeOutdatedThree.setLevelPlayerChallenging(playerWithNoChallengesTwo.getLevel());
		challengeOutdatedThree.setPlayerChallenged(playerWithNoChallengesOne);
		challengeOutdatedThree.setLevelPlayerChallenged(playerWithNoChallengesOne.getLevel());
		
		ChallengeEntity challengeOutdatedCreatedOne = challengeRepository.save(challengeOutdatedOne);
		ChallengeEntity challengeOutdatedCreatedTwo = challengeRepository.save(challengeOutdatedTwo);
		ChallengeEntity challengeOutdatedCreatedThree = challengeRepository.save(challengeOutdatedThree);
		
		//when
		final int resultDeletedChallengesNumber = challengeService.removeOutdatedChallenges();
		
		//then
		Assert.assertEquals(properDeletedChallengesNumber, resultDeletedChallengesNumber);
		Assert.assertEquals(challengeOutdatedCreatedOne.getDateCreated(), dateCreatedOld);
		Assert.assertEquals(challengeOutdatedCreatedTwo.getDateCreated(), dateCreatedOld);
		Assert.assertEquals(challengeOutdatedCreatedThree.getDateCreated(), dateCreatedOld);
	}
}
