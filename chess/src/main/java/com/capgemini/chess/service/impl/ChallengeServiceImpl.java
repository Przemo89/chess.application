package com.capgemini.chess.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.chess.algorithms.data.enums.Level;
import com.capgemini.chess.dao.ChallengeDao;
import com.capgemini.chess.dao.PlayerStatisticsDao;
import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;
import com.capgemini.chess.exception.ChallengeCreationException;
import com.capgemini.chess.exception.ChallengeIsNoLongerValidException;
import com.capgemini.chess.exception.ChallengeNotExistException;
import com.capgemini.chess.exception.PlayerNotExistException;
import com.capgemini.chess.service.ChallengeService;
import com.capgemini.chess.service.GameService;
import com.capgemini.chess.service.PlayerStatisticsService;

@Service
@Transactional(readOnly = true)
public class ChallengeServiceImpl implements ChallengeService {

	@Autowired
	private ChallengeDao challengeDao;
	
	@Autowired
	private PlayerStatisticsDao playerStatisticsDao;
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private PlayerStatisticsService playersStatisticsService;

	/**Creates manual challenge. If challenge already exists in DB, it will
	 * be updated with current Date. Check, if both players exist, will be performed
	 * during accepting of challenge.
	 * @param idOfChallengingPlayer
	 * @param idOfChallengedPlayer
	 * @return challenge entity which was saved or updated.
	 */
	@Override
	@Transactional(readOnly = false)
	public ChallengeEntity createOrUpdateChallenge(long idPlayerChallenging, long idPlayerChallenged) 
			throws ChallengeCreationException {
		isPlayerChallengingHimself(idPlayerChallenging, idPlayerChallenged);
		PlayerStatisticsEntity playerChallenging = playerStatisticsDao.getPlayerStatisticsWithChallengesSent(idPlayerChallenging);
		if (playerChallenging.getChallengesSent().isEmpty() == false) {
			for (ChallengeEntity challenge: playerChallenging.getChallengesSent()) {
				if (challenge.getPlayerChallenging().getId() == idPlayerChallenging && challenge.getPlayerChallenged().getId() == idPlayerChallenged) {
					challenge.setDateCreated(null);
					return challengeDao.update(challenge);
				}
			}
		}
		PlayerStatisticsEntity playerChallenged = playerStatisticsDao.findOne(idPlayerChallenged);
		ChallengeEntity challenge = new ChallengeEntity();
		setChallengeEntityForChallengeCreation(challenge, 
				playerChallenging, playerChallenged);
		return challengeDao.save(challenge);
	}

	private void isPlayerChallengingHimself(long idPlayerChallenging, long idPlayerChallenged)
			throws ChallengeCreationException {
		if (idPlayerChallenging == idPlayerChallenged) {
			throw new ChallengeCreationException();
		}
	}

	private void setChallengeEntityForChallengeCreation(ChallengeEntity challenge,
			PlayerStatisticsEntity playerChallenging, PlayerStatisticsEntity playerChallenged) {
		challenge.setPlayerChallenging(playerChallenging);
		challenge.setPlayerChallenged(playerChallenged);
		challenge.setLevelPlayerChallenging(playerChallenging.getLevel());
		challenge.setLevelPlayerChallenged(playerChallenged.getLevel());
	}

	/**Finds matching players during creation of challenge list (automatic)
	 * @param idPlayerChallenging
	 * @return list with at most 5 potential matching players
	 */
	@Override
	public List<PlayerStatisticsEntity> getMatchingPlayers(long idPlayerChallenging) 
			throws PlayerNotExistException {
		PlayerStatisticsEntity playerChallenging = playerStatisticsDao.findOne(idPlayerChallenging);
		isPlayerStatisticsEntityExists(playerChallenging, idPlayerChallenging);
		List<String> levelsString = new ArrayList<>();
		setLevelsMatchingPlayersList(playerChallenging, levelsString);
		List<PlayerStatisticsEntity> potentialRivalPlayers = 
				playerStatisticsDao.getMatchingPlayersList(idPlayerChallenging, levelsString);
		List<PlayerStatisticsEntity> finalListOfPlayers = createMatchingPlayersFinalList(playerChallenging,
				potentialRivalPlayers);
		setPlayersRankingPositions(finalListOfPlayers);
		return finalListOfPlayers;
	}

	private void setPlayersRankingPositions(List<PlayerStatisticsEntity> finalListOfPlayers) {
		for (PlayerStatisticsEntity playerStats : finalListOfPlayers) {
			playerStats.setRankingPosition(playerStatisticsDao.getPlayerRankingPosition(playerStats.getPoints())+1);
		}
	}

	private List<PlayerStatisticsEntity> createMatchingPlayersFinalList(PlayerStatisticsEntity playerChallenging,
			List<PlayerStatisticsEntity> potentialRivalPlayers) {
		setPotentialBenefitLoss(potentialRivalPlayers, playerChallenging);
		return potentialRivalPlayers;
	}

	private void setLevelsMatchingPlayersList(PlayerStatisticsEntity playerChallenging, List<String> levels) {
		int valueLevelPlayerChallenging = playerChallenging.getLevel().getValue();
		for (int i = -2; i <= 2; i++) {
			int levelValue = i + valueLevelPlayerChallenging;
			if (levelValue > 0 && levelValue < 11) {
				levels.add(Level.getLevelByValue(levelValue).toString());
			}
		}
	}

	private void isPlayerStatisticsEntityExists(PlayerStatisticsEntity playerChallenging, long idPlayerChallenging)
			throws PlayerNotExistException {
		if (playerChallenging == null) {
			throw new PlayerNotExistException(idPlayerChallenging);
		}
	}

	private void setPotentialBenefitLoss(List<PlayerStatisticsEntity> potentialRivalPlayers,
			PlayerStatisticsEntity playerChallenging) {
		for (PlayerStatisticsEntity player : potentialRivalPlayers) {
			player.setPotentialBenefitForOtherPlayer(playersStatisticsService.calculateWinnerProfit(playerChallenging, player));
			player.setPotentialLossForOtherPlayer(playersStatisticsService
					.calculateChallengingPlayerPotentialLoss(playerChallenging, player));
		}
	}

	/**Removes record containing specific challenge from DB through ChallengeDAO.
	 * @param idChallenge - challenge's id, which is to be declined
	 */
	@Override
	@Transactional(readOnly = false)
	public void declineChallenge(long idChallenge) 
			throws ChallengeNotExistException {
		ChallengeEntity challengeToDelete = challengeDao.findOne(idChallenge);
		isChallengeExists(challengeToDelete, idChallenge);
		challengeDao.delete(challengeToDelete);
	}

	private void isChallengeExists(ChallengeEntity challengeToDelete, long idChallenge)
			throws ChallengeNotExistException {
		if (challengeToDelete == null) {
			throw new ChallengeNotExistException(idChallenge);
		}
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
	@Transactional(readOnly = false)
	public void acceptChallenge(long idChallenge) throws ChallengeNotExistException, ChallengeIsNoLongerValidException {
		ChallengeEntity challengeToAccept = challengeDao.findOne(idChallenge);
		isChallengeExists(challengeToAccept, idChallenge);
		isPlayersLevelsChanged(challengeToAccept);
		challengeDao.delete(challengeToAccept);
		gameService.startMatch(challengeToAccept);
	}

	private void isPlayersLevelsChanged(ChallengeEntity challengeToAccept)
			throws ChallengeIsNoLongerValidException {
		if (challengeToAccept.getPlayerChallenging().getLevel() != challengeToAccept.getLevelPlayerChallenging()
				|| challengeToAccept.getPlayerChallenged().getLevel() != challengeToAccept.getLevelPlayerChallenged()) {
			throw new ChallengeIsNoLongerValidException(challengeToAccept.getId());
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
		PlayerStatisticsEntity playerChallenging = playerStatisticsDao.getOne(idPlayer);
		List<PlayerStatisticsEntity> playersChallenged = new ArrayList<>();
		for (ChallengeEntity challenge: sentChallenges) {
			playersChallenged.add(challenge.getPlayerChallenged());
		}
		setPotentialBenefitLoss(playersChallenged, playerChallenging);
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
		PlayerStatisticsEntity playerChallenged = playerStatisticsDao.getOne(idPlayer);
		List<PlayerStatisticsEntity> playersChallenging = new ArrayList<>();
		for (ChallengeEntity challenge: receivedChallenges) {
			playersChallenging.add(challenge.getPlayerChallenging());
		}
		setPotentialBenefitLoss(playersChallenging, playerChallenged);
		return receivedChallenges;
	}
	
	/**Removes from DB all challenges, which are older than 7 seven days.
	 * @return number of deleted challenges
	 */
	@Override
	@Transactional(readOnly = false)
	public int removeOutdatedChallenges() {
		return challengeDao.removeOutdatedChallenges();
	}

}
