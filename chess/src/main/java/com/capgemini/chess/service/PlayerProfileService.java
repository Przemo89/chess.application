package com.capgemini.chess.service;

import java.util.List;

import com.capgemini.chess.dataaccess.entities.PlayerProfileEntity;

public interface PlayerProfileService {
	
	PlayerProfileEntity readPlayerProfile(Long idPlayerProfile);

	List<PlayerProfileEntity> getPlayersProfilesByLogin(String login);
}
