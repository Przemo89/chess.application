package com.capgemini.chess.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.capgemini.chess.dao.PlayerStatisticsDao;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;
import com.capgemini.chess.exception.PlayerNotExistException;

@Repository
public class PlayerStatisticsDaoImpl extends AbstractDao<PlayerStatisticsEntity, Long> implements PlayerStatisticsDao {

	@Override
	public List<PlayerStatisticsEntity> getMatchingPlayersList(long idOfChallengingPlayer)
			throws PlayerNotExistException {
		// TODO Auto-generated method stub
		return null;
	}

	
}
