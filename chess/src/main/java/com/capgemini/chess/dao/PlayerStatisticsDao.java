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
	List<PlayerStatisticsEntity> getMatchingPlayersList(long idOfChallengingPlayer) throws PlayerNotExistException;
}
