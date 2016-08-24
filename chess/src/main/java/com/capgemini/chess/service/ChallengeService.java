package com.capgemini.chess.service;

import java.util.List;

import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.exception.ChallengeDataIntegrityViolationException;
import com.capgemini.chess.exception.ChallengeIsNoLongerValidException;
import com.capgemini.chess.exception.ChallengeNotExistException;
import com.capgemini.chess.exception.PlayerNotExistException;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.PlayerStatisticsTO;

public interface ChallengeService {

	/**Creates manual challenge. If challenge already exists in DB, it will
	 * be updated with current Date. Check, if both players exist, will be performed
	 * during accepting of challenge.
	 * @param idOfChallengingPlayer
	 * @param idOfChallengedPlayer
	 * @return challenge entity which was saved or updated.
	 */
	ChallengeEntity createManualChallenge(long idChallengingPlayer, long idChallengedPlayer) 
			throws ChallengeDataIntegrityViolationException;
	
	/**Finds matching players during creation of challenge list (automatic)
	 * @param idOfChallengingPlayer
	 * @return list with at most 5 potential matching players
	 */
	List<PlayerStatisticsTO> getMatchingPlayers(long idOfChallengingPlayer);
	
	/**Removes record containing specific challenge from DB through ChallengeDAO.
	 * @param idChallenge - challenge's id, which is to be declined
	 */
	void declineChallenge(long idChallenge);
	
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
	void acceptChallenge(long idChallenge) throws ChallengeNotExistException, 
	ChallengeIsNoLongerValidException, PlayerNotExistException;
	
	/**Retrieves from DB (through ChallengesDAO) all challenges sent by specific player.
	 * @param idPlayer - identifies the player
	 * @return full list of all challenges, which were sent by the player.
	 * If there are no such challenges, returns empty list.
	 */
	List<ChallengeTO> getSentChallenges(long idPlayer);
	
	/**Retrieves from DB (through ChallengesDAO) all challenges, which were received by specific player.
	 * @param idPlayer - identifies the player
	 * @return full list of all challenges, which were received by the player.
	 * If there are no such challenges, returns empty list.
	 */
	List<ChallengeTO> getReceivedChallenges(long idPlayer);
	
	/**Removes from DB all challenges, which are older than 7 seven days.
	 */
	void removeOutdatedChallenges();
}
