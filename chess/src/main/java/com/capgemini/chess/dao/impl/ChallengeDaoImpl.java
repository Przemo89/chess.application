package com.capgemini.chess.dao.impl;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.chess.dao.ChallengeDao;
import com.capgemini.chess.dataaccess.entities.ChallengeEntity;

@Repository
public class ChallengeDaoImpl extends AbstractDao<ChallengeEntity, Long> implements ChallengeDao {

	@Override
	public List<ChallengeEntity> getPlayersSentChallenges(long idPlayer) {
		TypedQuery<ChallengeEntity> query = entityManager.createQuery(
				"select c from ChallengeEntity c "
				+ "where id_player_statistics_challenging = :id_player_statistics_challenging", ChallengeEntity.class);
		query.setParameter("id_player_statistics_challenging", idPlayer);
		return query.getResultList();
	}

	@Override
	public List<ChallengeEntity> getPlayersReceivedChallenges(long idPlayer) {
		TypedQuery<ChallengeEntity> query = entityManager.createQuery(
				"select c from ChallengeEntity c "
				+ "where id_player_statistics_challenged = :id_player_statistics_challenged", ChallengeEntity.class);
		query.setParameter("id_player_statistics_challenged", idPlayer);
		return query.getResultList();
	}

	@Override
	public int removeOutdatedChallenges() {
		Query query = entityManager.createQuery(
				"delete from ChallengeEntity challenges where "
				+ "timestampdiff(sql_tsi_day, date_creation, curdate()) > 7");
		return query.executeUpdate();
	}
}
