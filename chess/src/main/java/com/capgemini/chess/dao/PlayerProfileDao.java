package com.capgemini.chess.dao;

import java.util.List;

import com.capgemini.chess.dataaccess.entities.PlayerProfileEntity;

public interface PlayerProfileDao extends Dao<PlayerProfileEntity, Long> {

	PlayerProfileEntity getPlayerProfileAndStatistics(long idPlayer);
	
	List<PlayerProfileEntity> getPlayersProfilesByLogin(String login);
}
