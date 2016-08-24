package com.capgemini.chess.dao;

import java.util.List;

import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;

public interface ChallengeDao extends Dao<ChallengeEntity, Long> {

	/**Retrieves from DB players statistics entities of both challenging and challenged players.
	 * Also - if such challenge entity exists - retrieves challenge entity from DB, which already 
	 * exists for both players. If challenge exists, it will be only updated, if not - new one 
	 * will be created.
	 * @param idPlayerChallenging
	 * @param idPlayerChallenged
	 * @return list which should contain two filled entities. Service will verify it and in case when 
	 * some of the returned entities would turn out to be null, proper Exception will be thrown.
	 */
	List<PlayerStatisticsEntity> findBothPlayerStatisticsForChallengeCreation(long idPlayerChallenging, long idPlayerChallenged);
	
	List<ChallengeEntity> getPlayersSentChallenges(long idPlayer);
	List<ChallengeEntity> getPlayersReceivedChallenges(long idPlayer);
	
	/**Removes provided challenge record from DB.
	 * @param idChallenge
	 */
	void deleteChallenge(ChallengeEntity challenge);
	
//	/**Puts new challenge record in DB.
//	 * @param manualChallengeToSet - challenge, which was validated first by service
//	 * @throws PlayerNotExistException - in case some of the player's id was not found is DB.
//	 */
//	void setChallenge(ChallengeEntity manualChallengeToSet) throws PlayerNotExistException;
	
	/**Removes from DB all challenges, which are older than 7 seven days.
	 */
	void removeOutdatedChallenges();
	
//	int updateChallengeDatesCreationAndLastModification(long idChallenge);
}
