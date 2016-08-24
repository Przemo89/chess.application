package com.capgemini.chess.dao.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.chess.dao.ChallengeDao;
import com.capgemini.chess.exception.PlayerNotExistException;
import com.capgemini.chess.service.testcollections.ChallengeTOTestList;
import com.capgemini.chess.service.testcollections.PlayerStatisticsTOTestList;
import com.capgemini.chess.service.to.ChallengeTO;

@Repository
public class ChallengeDaoImpl implements ChallengeDao {
	
	@Override
	public List<ChallengeTO> getPlayersSentChallenges(long idPlayer) {
		List<ChallengeTO> sentChallenges = new ArrayList<>();
		for (int i = 0; i < ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.size(); i++) {
			if (ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(i).getChallengingPlayer().getId() == idPlayer) {
				sentChallenges.add(ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(i));
			}
		}
		return sentChallenges;
	}

	@Override
	public List<ChallengeTO> getPlayersReceivedChallenges(long idPlayer) {
		List<ChallengeTO> receivedChallenges = new ArrayList<>();
		for (int i = 0; i < ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.size(); i++) {
			if (ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(i).getChallengedPlayer().getId() == idPlayer) {
				receivedChallenges.add(ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(i));
			}
		}
		return receivedChallenges;
	}

	/**Retrieves from DB full statistics of specific challenge, including 
	 * players' levels during creation of the challenge and 
	 * current participant players' levels (to compare by ChallengeService).
	 * @param idChallenge - identifies challenge.
	 * @return full challenge statistics.
	 */
	@Override
	public ChallengeTO getChallengeStatistics(long idChallenge) {
		ChallengeTO challengeReturned;
		for (int i = 0; i < ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.size(); i++) {
			if (ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(i).getIdChallenge() == idChallenge) {
				challengeReturned = ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(i);
				removeChallenge(challengeReturned.getIdChallenge());
				return challengeReturned;
			}
		}
		return null; //empty ChallengeTO
	}

	/**Removes provided challenge record from DB.
	 * @param idChallenge
	 */
	@Override
	public void removeChallenge(long idChallenge) {
		for (int i = 0; i < ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.size(); i++) {
			if (ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(i).getIdChallenge() == idChallenge) {
				ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST
				.remove(i);
			}
		}
	}

	/**Puts new challenge record in DB.
	 * @param manualChallengeToSet - challenge, which was validated first by service
	 * @throws PlayerNotExistException - in case some of the player's id was not found is DB.
	 */
	@Override
	public void setChallenge(ChallengeTO manualChallengeToSet) throws PlayerNotExistException {
		setLevelsInNewChallenge(manualChallengeToSet);
		for (int i = 0; i < ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.size(); i++) {
			if (ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(i).getIdChallenge() 
					== manualChallengeToSet.getIdChallenge()) {
				ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST
				.set(i, ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(i));
				return;
			}
		}
		ChallengeTOTestList.TEST_LIST_FOR_INSERTION_ONLY.add(manualChallengeToSet);
	}

	private void setLevelsInNewChallenge(ChallengeTO manualChallengeToSet)
			throws PlayerNotExistException {
		boolean isChallengingPlayerInDB = false;
		boolean isChallengedPlayerInDB = false;
		for (int i = 0; i < PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.size(); i++) {
			if (PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.get(i).getId() 
					== manualChallengeToSet.getIdOfChallengingPlayer()) {
				manualChallengeToSet.setLevelOfChallengingPlayer(PlayerStatisticsTOTestList
						.PLAYER_STATISTICS_TO_TEST_LIST.get(i).getLevel());
				isChallengingPlayerInDB = true;
			}
			if (PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.get(i).getId() 
					== manualChallengeToSet.getIdOfChallengedPlayer()) {
				manualChallengeToSet.setLevelOfChallengedPlayer(PlayerStatisticsTOTestList
						.PLAYER_STATISTICS_TO_TEST_LIST.get(i).getLevel());
				isChallengedPlayerInDB = true;
			}
		}
		if (isChallengedPlayerInDB == false || isChallengingPlayerInDB == false) {
			throw new PlayerNotExistException();
		}
	}
	
	/**Removes from DB all challenges, which are older than 7 seven days.
	 */
	@Override
	public void removeOutdatedChallenges() {
		LocalDate earliestProperCreationDate = LocalDate.now().minusDays(7);
		for (int i = ChallengeTOTestList.OUTDATED_CHALLENGES_TEST_LIST.size() - 1; i >= 0; i--) {
			if (ChallengeTOTestList.OUTDATED_CHALLENGES_TEST_LIST.get(i)
					.getDateOfChallengeCreation().isBefore(earliestProperCreationDate)) {
				ChallengeTOTestList.OUTDATED_CHALLENGES_TEST_LIST.remove(i);
			}
		}
	}
}
