package com.capgemini.chess.dao;

import java.util.List;

import com.capgemini.chess.exception.PlayerNotExistException;
import com.capgemini.chess.service.to.PlayerStatisticsTO;

public interface PlayersStatisticsDao {
	
	/**Retrieves from DB full list of players, who could be selected 
	 * for specific player during automatic challenges suggestion.
	 * @param idOfChallengingPlayer - identifies the player
	 * @throws PlayerNotExistException - when challenging player 
	 * has not been found in DB.
	 * @return full list of potential rivals.
	 */
	List<PlayerStatisticsTO> getMatchingPlayersList(long idOfChallengingPlayer) throws PlayerNotExistException;
}
