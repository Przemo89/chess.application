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
				+ "where ps.id != :id_player_statistics_challenging and player_level in :levels "
				+ "order by rand() ", PlayerStatisticsEntity.class);
		query.setParameter("id_player_statistics_challenging", idPlayerChallenging);
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
	

	/**Retrieves from DB players statistics entities of both challenging and challenged players.
	 * Also - if such challenge entity exists - retrieves challenge entity from DB, which already 
	 * exists for both players. If challenge exists, it will be only updated, if not - new one 
	 * will be created.
	 * @param idPlayerChallenging
	 * @param idPlayerChallenged
	 * @return list which should contain two filled entities. Service will verify it and in case when 
	 * some of the returned entities would turn out to be null, proper Exception will be thrown.
	 */
	@Override
	public List<PlayerStatisticsEntity> findBothPlayerStatisticsForChallengeCreation(long idPlayerChallenging,
			long idPlayerChallenged) {
		TypedQuery<PlayerStatisticsEntity> query = entityManager.createQuery( 
				"select p from PlayerStatisticsEntity p "
				+ "left join p.challengesSent c "
				+ "where p.id in (:id_player_statistics_challenging, :id_player_statistics_challenged) "
				+ "or (id_player_statistics_challenging = :id_player_statistics_challenging "
				+ "and id_player_statistics_challenged = :id_player_statistics_challenged)", PlayerStatisticsEntity.class);
		query.setParameter("id_player_statistics_challenging", idPlayerChallenging);
		query.setParameter("id_player_statistics_challenged", idPlayerChallenged);
		return query.getResultList();
	}
}
