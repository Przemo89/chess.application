package com.capgemini.chess.dao;

import java.util.List;

import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;
import com.capgemini.chess.exception.PlayerNotExistException;

public interface PlayerStatisticsDao extends Dao<PlayerStatisticsEntity, Long> {
	
	/**Retrieves from DB full list of players, who could be selected 
	 * for specific player during automatic challenges suggestion.
	 * @param idOfChallengingPlayer - identifies the player
	 * @throws PlayerNotExistException - when challenging player 
	 * has not been found in DB.
	 * @return full list of potential rivals.
	 */
	List<PlayerStatisticsEntity> getMatchingPlayersList(long idPlayerChallenging, List<String> levelsString);
	
	Long getPlayerRankingPosition(int playerPoints);
	
	/**Retrieves from DB players statistics entities of both challenging and challenged players.
	 * Also - if such challenge entity exists - retrieves challenge entity from DB, which already 
	 * exists for both players. If challenge exists, it will be only updated, if not - new one 
	 * will be created.
	 * @param idPlayerChallenging
	 * @param idPlayerChallenged
	 * @return list which should contain two filled entities. Service will verify it and in case when 
	 * some of the returned entities would turn out to be null, proper Exception will be thrown.
	 */
	PlayerStatisticsEntity getPlayerStatisticsWithChallengesSent(long idPlayerChallenging);
}
