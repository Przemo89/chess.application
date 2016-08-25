package com.capgemini.chess.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.chess.dao.PlayerStatisticsDao;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;

@Repository
public class PlayerStatisticsDaoImpl extends AbstractDao<PlayerStatisticsEntity, Long> implements PlayerStatisticsDao {

	@Override
	public List<PlayerStatisticsEntity> getMatchingPlayersList(long idPlayerChallenging, List<String> levelsString) {
		TypedQuery<PlayerStatisticsEntity> query = entityManager.createQuery(
				"select ps from PlayerStatisticsEntity ps join fetch ps.playerProfile pp "
				+ "where ps.id != :id_player_challenging and player_level in :levels "
				+ "order by rand() ", PlayerStatisticsEntity.class);
		query.setParameter("id_player_challenging", idPlayerChallenging);
		query.setParameter("levels", levelsString);
		query.setMaxResults(5);
		return query.getResultList();
	}

	@Override
	public Long getPlayerRankingPosition(int playerPoints) {
		TypedQuery<Long> query = entityManager.createQuery(
				"select count(p) from PlayerStatisticsEntity p "
				+ "where points_total > :points_total", Long.class);
		query.setParameter("points_total", playerPoints);
		return query.getSingleResult();
	}
}
