package com.capgemini.chess.service.testcollections;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.capgemini.chess.algorithms.data.enums.Level;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.PlayerStatisticsTO;

public class ChallengeTOTestList {

	/**Contains only ChallengesTO which are fully proper and contain @PlayersStatisticsTO 
	 * (these challenges will be returned by @ChallengeDAO)
	 */
	public static List<ChallengeTO> PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST = new ArrayList<>();
	
	/**Contains only ChallengesTO which are fully proper and does not contain 
	 * @PlayersStatisticsTO (these challenges will be pushed from by @ChallengeService to @ChallengeDao)
	 */
	public static List<ChallengeTO> PROPER_CHALLENGES_TO_WITHOUT_PLAYER_STATISTICS_TO_TEST_LIST = new ArrayList<>();
	
	/**Contains invalid ChallengesTO, for example with "empty" related Players etc.
	 */
	public static List<ChallengeTO> INPROPER_CHALLENGES_TO_TEST_LIST = new ArrayList<>();
	
	/** To this list will be inserted only new challenges during testing of @ChallengeServiceImpl.
	 */
	public static List<ChallengeTO> TEST_LIST_FOR_INSERTION_ONLY = new ArrayList<>();
	
	/**Contains few out dated and proper challenges.
	 */
	public static List<ChallengeTO> OUTDATED_CHALLENGES_TEST_LIST = new ArrayList<>();

	/**Restarts initial value of @PROPER_CHALLENGES_TO_WITH_PLAYER_STATISTICS_TO_TEST_LIST.
	 */
	
	static {
		setInitialValuesOfInproperChallengesToTestList();
		setInitialValuesOfOutdatedChallengesToTestList();
		setInitialValuesOfProperChallengesWithoutPlayerStatisticsToTestList();
		setInitialValuesOfProperChallengesWithPlayerStatisticsToTestList();
		setInitialValuesOfTestListForInsertionOnly();
	}
	
	public static void setInitialValuesOfProperChallengesWithPlayerStatisticsToTestList() {
		PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.clear();
		
		PlayerStatisticsTO playerOne = new PlayerStatisticsTO(345, Level.NEWBIE, 0, 2, 1, 1, 0, 0, 0, 0, null);
		PlayerStatisticsTO playerTwo =new PlayerStatisticsTO(3445, Level.NEWBIE, 10, 5, 2, 2, 1, 0, 0, 0, null);
		PlayerStatisticsTO playerThree = new PlayerStatisticsTO(3508745, Level.WEAKLING, 400, 10, 5, 2, 5, 0, 0, 0, null);
		PlayerStatisticsTO playerFour = new PlayerStatisticsTO(23345, Level.WEAKLING, 500, 12, 6, 3, 5, 0, 0, 0, null);
		PlayerStatisticsTO playerFive = new PlayerStatisticsTO(1345, Level.BEGINNER, 700, 35, 18, 8, 5, 0, 0, 0, null);
		PlayerStatisticsTO playerSix = new PlayerStatisticsTO(34905, Level.BEGINNER, 800, 40, 20, 10, 10, 0, 0, 0, null);
		PlayerStatisticsTO playerSeven = new PlayerStatisticsTO(590345, Level.MIDDLEBROW, 2609, 100, 50, 25, 25, 0, 0, 0, null);
		PlayerStatisticsTO playerEight = new PlayerStatisticsTO(34405598, Level.ADVANCED, 16800, 200, 100, 10, 70, 0, 0, 0, null);
		PlayerStatisticsTO playerNine = new PlayerStatisticsTO(125345445, Level.CHUCK_NORRIS_OF_CHESS, 80000, 450, 390, 30, 30, 0, 0, 0, null);

		PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.add(new ChallengeTO(4139, 345, 3445, 
				Level.NEWBIE, Level.NEWBIE, LocalDate.now(), playerOne, playerTwo));
		PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.add(new ChallengeTO(3509439, 345, 3508745, 
				Level.NEWBIE, Level.WEAKLING, LocalDate.now(), playerOne, playerThree));
		PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.add(new ChallengeTO(2039, 345, 1345, 
				Level.NEWBIE, Level.BEGINNER, LocalDate.now(), playerOne, playerFive));
		PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.add(new ChallengeTO(34452292, 23345, 34405598, 
				Level.WEAKLING, Level.ADVANCED, LocalDate.now(), playerFour, playerEight));
		PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.add(new ChallengeTO(1215599, 590345, 34905, 
				Level.MIDDLEBROW, Level.BEGINNER, LocalDate.now(), playerSeven, playerSix));
		PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.add(new ChallengeTO(1215599, 125345445, 34905, 
				Level.CHUCK_NORRIS_OF_CHESS, Level.BEGINNER, LocalDate.now(), playerNine, 
				playerSix));
	}
	
	/**Restarts initial value of @PROPER_CHALLENGES_TO_WITHOUT_PLAYER_STATISTICS_TO_TEST_LIST.
	 */
	public static void setInitialValuesOfProperChallengesWithoutPlayerStatisticsToTestList() {
		PROPER_CHALLENGES_TO_WITHOUT_PLAYER_STATISTICS_TO_TEST_LIST.clear();
		PROPER_CHALLENGES_TO_WITHOUT_PLAYER_STATISTICS_TO_TEST_LIST.add(new ChallengeTO(4139, 345, 3445, 
				Level.NEWBIE, Level.NEWBIE, LocalDate.now(), null, null));
		PROPER_CHALLENGES_TO_WITHOUT_PLAYER_STATISTICS_TO_TEST_LIST.add(new ChallengeTO(3509439, 345, 3508745, 
				Level.NEWBIE, Level.WEAKLING, LocalDate.now(), null, null));
		PROPER_CHALLENGES_TO_WITHOUT_PLAYER_STATISTICS_TO_TEST_LIST.add(new ChallengeTO(2039, 345, 1345, 
				Level.NEWBIE, Level.BEGINNER, LocalDate.now(), null, null));
		PROPER_CHALLENGES_TO_WITHOUT_PLAYER_STATISTICS_TO_TEST_LIST.add(new ChallengeTO(34452292, 23345, 34405598, 
				Level.WEAKLING, Level.ADVANCED, LocalDate.now(), null, null));
		PROPER_CHALLENGES_TO_WITHOUT_PLAYER_STATISTICS_TO_TEST_LIST.add(new ChallengeTO(1215599, 590345, 34905, 
				Level.MIDDLEBROW, Level.BEGINNER, LocalDate.now(), null, null));
		PROPER_CHALLENGES_TO_WITHOUT_PLAYER_STATISTICS_TO_TEST_LIST.add(new ChallengeTO(1215599, 125345445, 34905, 
				Level.CHUCK_NORRIS_OF_CHESS, Level.BEGINNER, LocalDate.now(), null, null));
	}
	
	/**Restarts initial value of @INPROPER_CHALLENGES_TO_TEST_LIST.
	 */
	public static void setInitialValuesOfInproperChallengesToTestList() {
		INPROPER_CHALLENGES_TO_TEST_LIST.clear();
		
		PlayerStatisticsTO playerOne = new PlayerStatisticsTO(345, Level.NEWBIE, 0, 2, 1, 1, 0, 0, 0, 0, null);
		PlayerStatisticsTO playerTwo = new PlayerStatisticsTO(23345, Level.WEAKLING, 500, 12, 6, 3, 5, 0, 0, 0, null);
		PlayerStatisticsTO playerThree = new PlayerStatisticsTO(1345, Level.BEGINNER, 700, 35, 18, 8, 5, 0, 0, 0, null);
		PlayerStatisticsTO playerFour = new PlayerStatisticsTO(34405598, Level.ADVANCED, 16800, 200, 100, 10, 70, 0, 0, 0, null);
		
		INPROPER_CHALLENGES_TO_TEST_LIST.add(new ChallengeTO(4139, 345, 3445, 
				Level.NEWBIE, Level.NEWBIE, LocalDate.now(), new PlayerStatisticsTO(), new PlayerStatisticsTO()));
		INPROPER_CHALLENGES_TO_TEST_LIST.add(new ChallengeTO(3509439, 345, 3508745, 
				Level.NEWBIE, Level.WEAKLING, LocalDate.now(), playerOne, new PlayerStatisticsTO()));
		INPROPER_CHALLENGES_TO_TEST_LIST.add(new ChallengeTO(2039, 345, 1345, 
				Level.NEWBIE, Level.BEGINNER, LocalDate.now(), playerOne, playerThree));
		INPROPER_CHALLENGES_TO_TEST_LIST.add(new ChallengeTO(34452292, 23345, 34405598, 
				Level.WEAKLING, Level.ADVANCED, LocalDate.now(), playerTwo, playerFour));
	}
	
	/**Clears completely @TEST_LIST_FOR_INSERTION_ONLY.
	 */
	public static void setInitialValuesOfTestListForInsertionOnly() {
		TEST_LIST_FOR_INSERTION_ONLY.clear();
	}
	
	/**Restarts initial value of @OUTDATED_CHALLENGES_TEST_LIST
	 */
	public static void setInitialValuesOfOutdatedChallengesToTestList() {
		OUTDATED_CHALLENGES_TEST_LIST.clear();
		
		PlayerStatisticsTO playerOne = new PlayerStatisticsTO(345, Level.NEWBIE, 0, 2, 1, 1, 0, 0, 0, 0, null);
		PlayerStatisticsTO playerTwo =new PlayerStatisticsTO(3445, Level.NEWBIE, 10, 5, 2, 2, 1, 0, 0, 0, null);
		PlayerStatisticsTO playerThree = new PlayerStatisticsTO(3508745, Level.WEAKLING, 400, 10, 5, 2, 5, 0, 0, 0, null);
		PlayerStatisticsTO playerFour = new PlayerStatisticsTO(1345, Level.BEGINNER, 700, 35, 18, 8, 5, 0, 0, 0, null);

		/**Addition of outdated challenges.
		 */
		OUTDATED_CHALLENGES_TEST_LIST.add(new ChallengeTO(4139, 345, 3445, 
				Level.NEWBIE, Level.NEWBIE, LocalDate.of(2016, Month.JULY, 14), playerOne, playerTwo));
		OUTDATED_CHALLENGES_TEST_LIST.add(new ChallengeTO(3509439, 345, 3508745, 
				Level.NEWBIE, Level.WEAKLING, LocalDate.of(2016, Month.JULY, 14), playerOne, playerThree));
		OUTDATED_CHALLENGES_TEST_LIST.add(new ChallengeTO(2039, 345, 1345, 
				Level.NEWBIE, Level.BEGINNER, LocalDate.of(2016, Month.JULY, 14), playerOne, playerFour));
		
		/**Addition of proper challenges.
		 */
		OUTDATED_CHALLENGES_TEST_LIST.add(new ChallengeTO(4139, 345, 3445, 
				Level.NEWBIE, Level.NEWBIE, LocalDate.now(), playerOne, playerTwo));
		OUTDATED_CHALLENGES_TEST_LIST.add(new ChallengeTO(3509439, 345, 3508745, 
				Level.NEWBIE, Level.WEAKLING, LocalDate.now(), playerOne, playerThree));
		OUTDATED_CHALLENGES_TEST_LIST.add(new ChallengeTO(2039, 345, 1345, 
				Level.NEWBIE, Level.BEGINNER, LocalDate.now(), playerOne, playerFour));
		
	}

}
