package com.capgemini.chess.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.chess.dao.PlayerProfileDao;
import com.capgemini.chess.dao.PlayerStatisticsDao;
import com.capgemini.chess.dataaccess.entities.PlayerProfileEntity;
import com.capgemini.chess.service.PlayerProfileService;

@Service
@Transactional(readOnly = true)
public class PlayerProfileServiceImpl implements PlayerProfileService {

	@Autowired
	private PlayerProfileDao playerProfileDao;
	
	@Autowired
	private PlayerStatisticsDao playerStatisticsDao;
	
	@Override
	public PlayerProfileEntity readPlayerProfile(Long idPlayerProfile) {
		PlayerProfileEntity playerProfile = playerProfileDao.getPlayerProfileAndStatistics(idPlayerProfile);
		playerProfile.getPlayerStatistics().setRankingPosition(playerStatisticsDao
				.getPlayerRankingPosition(playerProfile.getPlayerStatistics().getPoints())+1);
		return playerProfile;
	}

	@Override
	public List<PlayerProfileEntity> getPlayersProfilesByLogin(String login) {
		return playerProfileDao.getPlayersProfilesByLogin(login);
	}
	
	
}
