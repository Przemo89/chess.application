package com.capgemini.chess.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.chess.dao.PlayerProfileDao;
import com.capgemini.chess.dataaccess.entities.PlayerProfileEntity;

@Repository
public class PlayerProfileDaoImpl extends AbstractDao<PlayerProfileEntity, Long> implements PlayerProfileDao{

	@Override
	public PlayerProfileEntity getPlayerProfileAndStatistics(long idPlayer) {
		TypedQuery<PlayerProfileEntity> query = entityManager.createQuery(
				"select p from PlayerProfileEntity p "
				+ "join fetch p.playerStatistics ps "
				+ "where p.id = :id_player_profile", PlayerProfileEntity.class);
		query.setParameter("id_player_profile", idPlayer);
		return query.getSingleResult();
	}

	@Override
	public List<PlayerProfileEntity> getPlayersProfilesByLogin(String login) {
		TypedQuery<PlayerProfileEntity> query = entityManager.createQuery(
				"select p from PlayerProfileEntity p "
				+ "join fetch p.playerStatistics ps "
				+ "where login like concat('%', :login, '%')", PlayerProfileEntity.class);
		query.setParameter("login", login);
		query.setMaxResults(10);
		return query.getResultList();
	}

	
	
}
