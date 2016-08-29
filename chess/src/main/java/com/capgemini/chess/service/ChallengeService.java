package com.capgemini.chess.service;

import java.util.List;

import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;
import com.capgemini.chess.exception.ChallengeCreationException;
import com.capgemini.chess.exception.ChallengeDeclineException;
import com.capgemini.chess.exception.ChallengeIsNoLongerValidException;
import com.capgemini.chess.exception.ChallengeNotExistException;
import com.capgemini.chess.exception.PlayerNotExistException;

public interface ChallengeService {

	/**Creates manual challenge. If challenge already exists in DB, it will
	 * be updated with current Date. Check, if both players exist, will be performed
	 * during accepting of challenge.
	 * @param idOfChallengingPlayer
	 * @param idOfChallengedPlayer
	 * @return challenge entity which was saved or updated.
	 */
	ChallengeEntity createOrUpdateChallenge(long idChallengingPlayer, long idChallengedPlayer) 
			throws ChallengeCreationException;
	
	/**Finds matching players during creation of challenge list (automatic)
	 * @param idPlayerChallenging
	 * @return list with at most 5 potential matching players
	 */
	List<PlayerStatisticsEntity> getMatchingPlayers(long idPlayerChallenging) throws PlayerNotExistException;
	
	/**Removes record containing specific challenge from DB through ChallengeDAO.
	 * @param challenge - challenge, which is to be declined
	 */
	void declineChallenge(long idChallenge) throws ChallengeDeclineException, ChallengeNotExistException;
	
	/**Checks if challenge exist, then compares level of players from:
	 * 1. Moment when challenge has been created (from ChallengeEntity)
	 * 2. Present time
	 * If there is any difference detected, challenge is automatically declined.
	 * If no difference is detected, game starts.
	 * @param idChallenge - identifies specific challenge
	 * @throws ChallengeDataIntegrityViolationException - if challenge not exists or 
	 * change of players' levels detected
	 */
	void acceptChallenge(long idChallenge) throws ChallengeNotExistException, ChallengeIsNoLongerValidException;
	
	/**Retrieves from DB (through ChallengesDAO) all challenges sent by specific player.
	 * @param idPlayer - identifies the player
	 * @return full list of all challenges, which were sent by the player.
	 * If there are no such challenges, returns empty list.
	 */
	List<ChallengeEntity> getSentChallenges(long idPlayer);
	
	/**Retrieves from DB (through ChallengesDAO) all challenges, which were received by specific player.
	 * @param idPlayer - identifies the player
	 * @return full list of all challenges, which were received by the player.
	 * If there are no such challenges, returns empty list.
	 */
	List<ChallengeEntity> getReceivedChallenges(long idPlayer);
	
	/**Removes from DB all challenges, which are older than 7 seven days.
	 * @return number of deleted challenges
	 */
	int removeOutdatedChallenges();
	
}
