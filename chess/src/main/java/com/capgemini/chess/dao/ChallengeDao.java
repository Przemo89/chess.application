package com.capgemini.chess.dao;

import java.util.List;

import com.capgemini.chess.exception.PlayerNotExistException;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.PlayerStatisticsTO;

public interface ChallengeDao {

	List<ChallengeTO> getPlayersSentChallenges(long idPlayer);
	List<ChallengeTO> getPlayersReceivedChallenges(long idPlayer);
	
	/**Retrieves from DB full statistics of specific challenge, including 
	 * players' levels during creation of the challenge and 
	 * current participant players' levels (to compare by ChallengeService).
	 * @param idChallenge - identifies challenge.
	 * @return full challenge statistics.
	 */
	ChallengeTO getChallengeStatistics(long idChallenge);
	
	/**Removes provided challenge record from DB.
	 * @param idChallenge
	 */
	void removeChallenge(long idChallenge);
	
	/**Puts new challenge record in DB.
	 * @param manualChallengeToSet - challenge, which was validated first by service
	 * @throws PlayerNotExistException - in case some of the player's id was not found is DB.
	 */
	void setChallenge(ChallengeTO manualChallengeToSet) throws PlayerNotExistException;
	
	/**Removes from DB all challenges, which are older than 7 seven days.
	 */
	void removeOutdatedChallenges();
}
