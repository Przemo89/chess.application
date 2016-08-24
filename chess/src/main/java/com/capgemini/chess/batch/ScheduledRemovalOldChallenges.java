package com.capgemini.chess.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.capgemini.chess.service.ChallengeService;

@Component
public class ScheduledRemovalOldChallenges {

	@Autowired
	ChallengeService challengeService;

	/**Removes challenges which are older than 7 days.
	 * This batch is scheduled for everyday, at 00:01
	 */
	@Scheduled(cron = "0 1 0 * * *")
	public void removeOldChallenges() {
		challengeService.removeOutdatedChallenges();
	}
}
