package com.capgemini.chess.dao;

import java.util.List;

import com.capgemini.chess.dataaccess.entities.ChallengeEntity;

public interface ChallengeDao extends Dao<ChallengeEntity, Long> {

	List<ChallengeEntity> getPlayersSentChallenges(long idPlayer);
	List<ChallengeEntity> getPlayersReceivedChallenges(long idPlayer);
	
	/**Removes from DB all challenges, which are older than 7 seven days.
	 * @return number of deleted challenges
	 */
	int removeOutdatedChallenges();
}
