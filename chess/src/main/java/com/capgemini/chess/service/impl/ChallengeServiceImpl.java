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

		int playerChallengingIndexInList = setPlayerChallengingIndexInList(playersStatistics.get(0), idPlayerChallenging);
		int playerChallengedIndexInList = setPlayerChallengedIndexInList(playerChallengingIndexInList);
		if (playersStatistics.get(playerChallengingIndexInList).getChallengesSent().isEmpty() == false 
				&& playersStatistics.get(playerChallengingIndexInList).getChallengesSent().get(0) != null) {
			// Version has to be changed, otherwise Listeners won't be fired up and no changes will be made.
//			playersStatistics.get(playerChallengingIndexInList).getChallengesSent().get(0).setVersion(
//					playersStatistics.get(playerChallengingIndexInList).getChallengesSent().get(0).getVersion() + 1); 
			playersStatistics.get(playerChallengingIndexInList).getChallengesSent().get(0).setDateCreated(null);
			return challengeDao.update(playersStatistics.get(playerChallengingIndexInList).getChallengesSent().get(0));
		}
		ChallengeEntity challenge = new ChallengeEntity();
		setChallengeEntityForChallengeCreation(challenge, 
				playersStatistics.get(playerChallengingIndexInList), playersStatistics.get(playerChallengedIndexInList));
		return challengeDao.save(challenge);
	}

	private void setChallengeEntityForChallengeCreation(ChallengeEntity challenge,
			PlayerStatisticsEntity playerChallenging, PlayerStatisticsEntity playerChallenged) {
		challenge.setPlayerChallenging(playerChallenging);
		challenge.setPlayerChallenged(playerChallenged);
		challenge.setLevelPlayerChallenging(playerChallenging.getLevel());
		challenge.setLevelPlayerChallenged(playerChallenged.getLevel());
	}

	private int setPlayerChallengingIndexInList(PlayerStatisticsEntity playerStatistics,
			long idPlayerChallenging) {
		if (playerStatistics.getId() == idPlayerChallenging) {
			return 0;
		}
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
	public List<PlayerStatisticsEntity> getMatchingPlayers(long idOfChallengingPlayer) {
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
	public void acceptChallenge(long idChallenge) throws ChallengeDataIntegrityViolationException {
		ChallengeEntity challengeToAccept = challengeDao.findOne(idChallenge);
		isChallengedReturned(idChallenge, challengeToAccept);
		isPlayersLevelsChanged(challengeToAccept);
		gameService.startMatch(challengeToAccept);
	}

	private void isPlayersLevelsChanged(ChallengeEntity challengeToAccept)
			throws ChallengeDataIntegrityViolationException {
		if (challengeToAccept.getPlayerChallenging().getLevel() != challengeToAccept.getLevelPlayerChallenging()
				|| challengeToAccept.getPlayerChallenged().getLevel() != challengeToAccept.getLevelPlayerChallenged()) {
			throw new ChallengeDataIntegrityViolationException("Challenge with id: " + challengeToAccept.getId() + 
					" is no longer valid, because players' level changed.");
		}
	}

	private void isChallengedReturned(long idChallenge, ChallengeEntity challengeToAccept)
			throws ChallengeDataIntegrityViolationException {
		if (challengeToAccept == null) {
			throw new ChallengeDataIntegrityViolationException("Specified challenge with id = " + idChallenge
					+ " does not exist anymore!");
		}
	}

	/**Retrieves from DB (through ChallengesDAO) all challenges sent by specific player.
	 * @param idPlayer - identifies the player
	 * @return full list of all challenges, which were sent by the player.
	 * If there are no such challenges, returns empty list.
	 */
	@Override
	public List<ChallengeEntity> getSentChallenges(long idPlayer) {
		List<ChallengeEntity> sentChallenges = challengeDao.getPlayersSentChallenges(idPlayer);
		return sentChallenges;
	}

	/**Retrieves from DB (through ChallengesDAO) all challenges, which were received by specific player.
	 * @param idPlayer - identifies the player
	 * @return full list of all challenges, which were received by the player.
	 * If there are no such challenges, returns empty list.
	 */
	@Override
	public List<ChallengeEntity> getReceivedChallenges(long idPlayer) {
		List<ChallengeEntity> receivedChallenges = challengeDao.getPlayersReceivedChallenges(idPlayer);
		return receivedChallenges;
	}
	
	/**Removes from DB all challenges, which are older than 7 seven days.
	 */
	@Override
	public void removeOutdatedChallenges() {
		challengeDao.removeOutdatedChallenges();
	}

}
