package com.capgemini.chess.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.chess.dao.ChallengeDao;
import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;

@Repository
public class ChallengeDaoImpl extends AbstractDao<ChallengeEntity, Long> implements ChallengeDao {
	
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
				+ "where p.id in (:id_player_challenging, :id_player_challenged) "
				+ "or (id_player_challenging = :id_player_challenging "
				+ "and id_player_challenged = :id_player_challenged)", PlayerStatisticsEntity.class);
		query.setParameter("id_player_challenging", idPlayerChallenging);
		query.setParameter("id_player_challenged", idPlayerChallenged);
		// TODO Auto-generated method stub
		return query.getResultList();
	}

	@Override
	public List<ChallengeEntity> getPlayersSentChallenges(long idPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChallengeEntity> getPlayersReceivedChallenges(long idPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteChallenge(ChallengeEntity challenge) {
		challenge = entityManager.merge(challenge);
		entityManager.remove(challenge);
		// TODO Auto-generated method stub
	}

//	@Override
//	public void setChallenge(ChallengeEntity manualChallengeToSet) throws PlayerNotExistException {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public void removeOutdatedChallenges() {
		// TODO Auto-generated method stub
		
	}

}
