package com.capgemini.chess.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.capgemini.chess.algorithms.data.enums.Level;
import com.capgemini.chess.dao.PlayersStatisticsDao;
import com.capgemini.chess.exception.PlayerNotExistException;
import com.capgemini.chess.service.testcollections.PlayerStatisticsTOTestList;
import com.capgemini.chess.service.to.PlayerStatisticsTO;

@Repository
public class PlayersStatisticsDaoImpl implements PlayersStatisticsDao {

	/**Retrieves at first PlayerStatisticsTO of challenging player.
	 * Then looks through the proper test list and checks possible rivals'
	 * levels. If level is in range of +2...-2 in comparison to 
	 * challenging player level, such player is added to the list.
	 * Next, whole challenging player TO is added at the end of result list - 
	 * it's necessary to count potential loss/benefit.
	 */
	@Override
	public List<PlayerStatisticsTO> getMatchingPlayersList(long idOfChallengingPlayer) throws PlayerNotExistException {
		List<PlayerStatisticsTO> allPotentialRivalsAndChallengingPlayer = new ArrayList<>();
		PlayerStatisticsTO challengingPlayer = getChallengingPlayer(idOfChallengingPlayer);
		isChallengingPlayerFound(challengingPlayer);
		for (PlayerStatisticsTO player : PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST) {
			if (Math.abs(challengingPlayer.getLevel().getValue() - player.getLevel().getValue()) <= 2 
					&& player.getId() != idOfChallengingPlayer) {
				allPotentialRivalsAndChallengingPlayer.add(player);
			}
		}
		allPotentialRivalsAndChallengingPlayer.add(challengingPlayer);
		return allPotentialRivalsAndChallengingPlayer;
	}

	private void isChallengingPlayerFound(PlayerStatisticsTO challengingPlayer) {
		if (challengingPlayer == null) {
			throw new PlayerNotExistException();
		}
	}
	
	private PlayerStatisticsTO getChallengingPlayer(long idChallengingPlayer) {
		for (int i = 0; i < PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.size(); i++) {
			if (PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.get(i).getId() == idChallengingPlayer) {
				PlayerStatisticsTO challengingPlayer = PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.get(i);
				return challengingPlayer;
			}
		}
		return null;
	}

	
}
