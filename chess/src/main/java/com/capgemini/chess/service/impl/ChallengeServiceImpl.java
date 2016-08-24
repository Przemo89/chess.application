package com.capgemini.chess.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.chess.dao.ChallengeDao;
import com.capgemini.chess.dao.PlayerStatisticsDao;
import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;
import com.capgemini.chess.domain.statistics.PointsCalculator;
import com.capgemini.chess.exception.ChallengeDataIntegrityViolationException;
import com.capgemini.chess.exception.ChallengeIsNoLongerValidException;
import com.capgemini.chess.exception.ChallengeNotExistException;
import com.capgemini.chess.exception.PlayerNotExistException;
import com.capgemini.chess.service.ChallengeService;
import com.capgemini.chess.service.GameService;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.PlayerStatisticsTO;

@Service
@Transactional(readOnly = true)
public class ChallengeServiceImpl implements ChallengeService {

	@Autowired
	private ChallengeDao challengeDao;
	
	@Autowired
	private PlayerStatisticsDao playerStatisticsDao;
	
	@Autowired
	private GameService gameService;

	/**Creates manual challenge. If challenge already exists in DB, it will
	 * be updated with current Date. Check, if both players exist, will be performed
	 * during accepting of challenge.
	 * @param idOfChallengingPlayer
	 * @param idOfChallengedPlayer
	 * @return challenge entity which was saved or updated.
	 */
	@Override
	@Transactional(readOnly = false)
	public ChallengeEntity createManualChallenge(long idPlayerChallenging, long idPlayerChallenged) 
			throws ChallengeDataIntegrityViolationException {
		List<PlayerStatisticsEntity> playersStatistics = challengeDao
				.findBothPlayerStatisticsForChallengeCreation(idPlayerChallenging, idPlayerChallenged); 
		if (playersStatistics.get(0) == null || playersStatistics.get(1) == null) {
			throw new ChallengeDataIntegrityViolationException("Cannot create challenge, "
					+ "because some Player requested in this challenge does not exist anymore!");
		}
		int playerChallengingIndexInList = setPlayerChallengingIndexInList(playersStatistics.get(0), idPlayerChallenging);
		int playerChallengedIndexInList = setPlayerChallengedIndexInList(playerChallengingIndexInList);
		
		ChallengeEntity challenge = new ChallengeEntity();
		setChallengeEntityForChallengeCreation(challenge, 
				playersStatistics.get(playerChallengingIndexInList), playersStatistics.get(playerChallengedIndexInList));
		
		if (playersStatistics.get(playerChallengingIndexInList).getChallengesSent().isEmpty() == false 
				&& playersStatistics.get(playerChallengingIndexInList).getChallengesSent().get(0) != null) {
			return challengeDao.update(challenge);
		}
		return challengeDao.save(challenge);
	}

	private void setChallengeEntityForChallengeCreation(ChallengeEntity challenge,
			PlayerStatisticsEntity playerChallenging, PlayerStatisticsEntity playerChallenged) {
		challenge.setPlayerChallenging(playerChallenging);
		challenge.setPlayerChallenged(playerChallenged);
		challenge.setLevelOfChallengingPlayer(playerChallenging.getLevel());
		challenge.setLevelOfChallengedPlayer(playerChallenged.getLevel());
	}

	private int setPlayerChallengingIndexInList(PlayerStatisticsEntity playerStatistics,
			long idPlayerChallenging) {
		if (playerStatistics.getId() == idPlayerChallenging) {
//			playerChallengingIndexInList = new Integer(0);
//			playerChallengedIndexInList = new Integer(1);
			return 0;
		}
//		playerChallengingIndexInList = 1;
//		playerChallengedIndexInList = 0;
		return 1;
	}
	
	private int setPlayerChallengedIndexInList(int playerChallengingIndexInList) {
		if (playerChallengingIndexInList == 0) {
			return 1;
		}
		return 0;
	}

	/**Finds matching players during creation of challenge list (automatic)
	 * @param idOfChallengingPlayer
	 * @return list with at most 5 potential matching players
	 */
	@Override
	public List<PlayerStatisticsTO> getMatchingPlayers(long idOfChallengingPlayer) {
//		List<PlayerStatisticsTO> potentialRivalPlayers = 
//				playerStatisticsDao.getMatchingPlayersList(idOfChallengingPlayer);
//		PlayerStatisticsTO challengingPlayer = potentialRivalPlayers.get(potentialRivalPlayers.size()-1);
//		potentialRivalPlayers.remove(potentialRivalPlayers.size() - 1);
//		if (potentialRivalPlayers.size() < 5) {
//			setPotentialBenefitAndLoss(potentialRivalPlayers, challengingPlayer);
//			return potentialRivalPlayers;
//
//		}
//		Collections.shuffle(potentialRivalPlayers);
//		List<PlayerStatisticsTO> finalListOfPlayers = new ArrayList<>();
//		finalListOfPlayers.addAll(potentialRivalPlayers.subList(0, 5));
//		setPotentialBenefitAndLoss(finalListOfPlayers, challengingPlayer);
//		return finalListOfPlayers;
		return null;
	}

	private void setPotentialBenefitAndLoss(List<PlayerStatisticsTO> potentialRivalPlayers,
			PlayerStatisticsTO challengingPlayer) {
		for (PlayerStatisticsTO player : potentialRivalPlayers) {
			PointsCalculator pointCalculator = new PointsCalculator(challengingPlayer, player);
			player.setPotentialBenefitForChallengingPlayer(pointCalculator.calculateWinnerProfit());
			player.setPotentialLossForChallengingPlayer(pointCalculator.calculateChallengingPlayerPotentialLoss());
		}
	}

	/**Removes record containing specific challenge from DB through ChallengeDAO.
	 * @param idChallenge - challenge's id, which is to be declined
	 */
	@Override
	public void declineChallenge(long idChallenge) {
//		challengeDao.deleteChallenge(idChallenge);
	}

	/**Checks if challenge exist, then compares level of players from:
	 * 1. Moment when challenge has been created (from ChallengeEntity)
	 * 2. Present time
	 * If there is any difference detected, challenge is automatically declined.
	 * If no difference is detected, game starts.
	 * @param idChallenge - identifies specific challenge
	 * @throws ChallengeNotExistException - challenge not exists
	 * @throws ChallengeIsNoLongerValidException - change of players' levels detected
	 * @throws PlayerNotExistException - if any of the players does not exist in DB anymore
	 */
	@Override
	public void acceptChallenge(long idChallenge) throws ChallengeNotExistException, 
	ChallengeIsNoLongerValidException, PlayerNotExistException {
//		ChallengeTO challengeToAccept = challengeDao.getChallengeStatistics(idChallenge);
//		isChallengedReturned(idChallenge, challengeToAccept);
//		isPlayersExistInChallenge(challengeToAccept);
//		isPlayersLevelsChanged(idChallenge, challengeToAccept);
//		gameService.startMatch(challengeToAccept);
	}

	private void isPlayersLevelsChanged(long idChallenge, ChallengeTO challengeToAccept)
			throws ChallengeIsNoLongerValidException {
		if (challengeToAccept.getChallengedPlayer().getLevel() != challengeToAccept.getLevelOfChallengedPlayer()
				|| challengeToAccept.getChallengingPlayer().getLevel() != challengeToAccept.getLevelOfChallengingPlayer()) {
			throw new ChallengeIsNoLongerValidException(idChallenge);
		}
	}

	private void isChallengedReturned(long idChallenge, ChallengeTO challengeToAccept)
			throws ChallengeNotExistException {
		if (challengeToAccept == null) {
			throw new ChallengeNotExistException(idChallenge);
		}
	}

	private void isPlayersExistInChallenge(ChallengeTO challengeToAccept) {
		if (challengeToAccept.getChallengingPlayer() == null 
				|| challengeToAccept.getChallengedPlayer() == null) {
			throw new PlayerNotExistException();
		}
		if (challengeToAccept.getChallengingPlayer().getId() == 0 
				|| challengeToAccept.getChallengedPlayer().getId() == 0) {
			throw new PlayerNotExistException();
		}
	}

	/**Retrieves from DB (through ChallengesDAO) all challenges sent by specific player.
	 * @param idPlayer - identifies the player
	 * @return full list of all challenges, which were sent by the player.
	 * If there are no such challenges, returns empty list.
	 */
	@Override
	public List<ChallengeTO> getSentChallenges(long idPlayer) {
//		List<ChallengeTO> sentChallenges = challengeDao.getPlayersSentChallenges(idPlayer);
//		return sentChallenges;
		return null;
	}

	/**Retrieves from DB (through ChallengesDAO) all challenges, which were received by specific player.
	 * @param idPlayer - identifies the player
	 * @return full list of all challenges, which were received by the player.
	 * If there are no such challenges, returns empty list.
	 */
	@Override
	public List<ChallengeTO> getReceivedChallenges(long idPlayer) {
//		List<ChallengeTO> receivedChallenges = challengeDao.getPlayersReceivedChallenges(idPlayer);
//		return receivedChallenges;
		return null;
	}
	
//	private long generateChallengeId(long idOfChallengingPlayer, long idOfChallengedPlayer) {
//		final long prime = 2;
//		long result = 1;
//		result = prime * result + idOfChallengingPlayer;
//		result = prime * result + idOfChallengedPlayer;
//		return result;
//	}
	
	/**Removes from DB all challenges, which are older than 7 seven days.
	 */
	@Override
	public void removeOutdatedChallenges() {
		challengeDao.removeOutdatedChallenges();
	}

}
