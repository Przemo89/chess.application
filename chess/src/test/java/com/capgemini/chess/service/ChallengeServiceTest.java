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
import com.capgemini.chess.exception.ChallengeCreationException;
import com.capgemini.chess.exception.ChallengeDeclineException;
import com.capgemini.chess.exception.ChallengeIsNoLongerValidException;
import com.capgemini.chess.exception.ChallengeNotExistException;
import com.capgemini.chess.exception.PlayerNotExistException;

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
		
	@Transactional
	@Test
	public void testShouldUpdateDatesCreatedAndLastModificatedWhenUpdatingChallenge() throws Exception {
		//given
		final long idChallengeThatExist = 1L;
		ChallengeEntity challengeBeforeUpdate = challengeRepository.findOne(idChallengeThatExist);
		entityManager.detach(challengeBeforeUpdate);
		final long idChallengingPlayer = challengeBeforeUpdate.getPlayerChallenging().getId();
		final long idChallengedPlayer = challengeBeforeUpdate.getPlayerChallenged().getId();
		List<ChallengeEntity> challengesListBeforeChallengeCreation = challengeRepository.findAll();
		final int sizeBefore = challengesListBeforeChallengeCreation.size();
		
		//when
		TimeUnit.SECONDS.sleep(1);
		// createOrUpdateChallenge() method updates challenge, if challenge with challenging and challenged players 
		// already exists in DB. Otherwise, new challenge is created. 
		ChallengeEntity challengeAfterUpdate = challengeService.createOrUpdateChallenge(idChallengingPlayer, idChallengedPlayer);
		
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
		ChallengeEntity challengeSaved = challengeService.createOrUpdateChallenge(idChallengingPlayer, idChallengedPlayer);
		
		//then
		List<ChallengeEntity> challengesListAfterChallengeCreation = challengeRepository.findAll();
		assertEquals(sizeBefore + 1, challengesListAfterChallengeCreation.size());
		assertEquals(idChallengingPlayer, challengeSaved.getPlayerChallenging().getId());
		assertEquals(idChallengedPlayer, challengeSaved.getPlayerChallenged().getId());
	}
	
	@Test
	public void testShouldThrowExceptionWhenCreatingChallengeAndPlayerIsChallengingHimself() throws Exception {
		//given
		final long idPlayerChallenging = 5L;

		//then
		thrown.expect(ChallengeCreationException.class);
		thrown.expectMessage("Player cannot sent challenge to himself!");
		
		//when
		challengeService.createOrUpdateChallenge(idPlayerChallenging, idPlayerChallenging);
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
	public void shouldReturnListWithPlayersRankingPositionWhenGettingMatchingPlayers() throws Exception {
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
			Assert.assertTrue(player.getPotentialBenefitForOtherPlayer() > 0);
			Assert.assertTrue(player.getPotentialLossForOtherPlayer() > 0);
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
		thrown.expect(ChallengeNotExistException.class);
		thrown.expectMessage(" does not exist.");

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
		thrown.expect(ChallengeIsNoLongerValidException.class);
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
		thrown.expect(ChallengeIsNoLongerValidException.class);
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
		thrown.expect(ChallengeIsNoLongerValidException.class);
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
	public void testShouldDeleteChallengeWhenDeclineChallenge() throws Exception {
		//given
		final long idChallengeExistingWhereIdReceiverIs10 = 24L;
		List<ChallengeEntity> challengesAllBeforeDecline = challengeRepository.findAll();
		final int challengesNumberBeforeDecline = challengesAllBeforeDecline.size();
		
		//when
		challengeService.declineChallenge(idChallengeExistingWhereIdReceiverIs10);
		List<ChallengeEntity> challengesAllAfterDecline = challengeRepository.findAll();
		
		//then
		Assert.assertEquals(challengesNumberBeforeDecline - 1, challengesAllAfterDecline.size());
		Assert.assertNull(challengeRepository.findOne(idChallengeExistingWhereIdReceiverIs10));
	}
	
	@Test
	public void testShouldThrowExceptionWhenDeclineChallengeByPlayerWhoDidNotReceiveIt() throws Exception {
		//given
		final long idChallengeExistingWhereIdReceiverIsNot10 = 39L;
		
		//then
		thrown.expect(ChallengeDeclineException.class);
		
		//when
		challengeService.declineChallenge(idChallengeExistingWhereIdReceiverIsNot10);
	}
	
	@Test
	public void testShouldThrowExceptionWhenDeclineChallengeWhichNotExists() throws Exception {
		//given
		final long idChallengeNotExisting = 393456L;
		
		//then
		thrown.expect(ChallengeNotExistException.class);
		
		//when
		challengeService.declineChallenge(idChallengeNotExisting);
	}

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
	
	@Transactional
	@Test
	public void testShouldUpdateVersionNumber() throws Exception {
		//given
		final long idChallengeExisting = 27L;
		final Integer versionBeforeUpdate = 1;
		final Integer versionAfterUpdate = 2;
		ChallengeEntity challengeBeforeUpdate = challengeRepository.findOne(idChallengeExisting);
		entityManager.detach(challengeBeforeUpdate);
		challengeBeforeUpdate.setDateCreated(new Date());
		
		//when
		ChallengeEntity challengeAfterUpdate = challengeRepository.update(challengeBeforeUpdate);
		entityManager.flush();

		//then
		assertEquals(versionBeforeUpdate, challengeBeforeUpdate.getVersion());
		assertEquals(versionAfterUpdate, challengeAfterUpdate.getVersion());
	}
	
	@Transactional
	@Test
	public void testShouldSetCreationDateAndLastModificationDateWhenCreatingChallenge() throws Exception {
		//given
		final long idPlayerOneNotHavingAnyChallenges = 16L;
		final long idPlayerOneTwoHavingAnyChallenges = 17L;

		//when
		TimeUnit.SECONDS.sleep(1);
		ChallengeEntity challengeCreated = challengeService.createOrUpdateChallenge(idPlayerOneNotHavingAnyChallenges, 
				idPlayerOneTwoHavingAnyChallenges);
		
		//then
		Assert.assertNotNull(challengeCreated.getDateCreated());
		Assert.assertNotNull(challengeCreated.getDateLastModified());
	}
}
