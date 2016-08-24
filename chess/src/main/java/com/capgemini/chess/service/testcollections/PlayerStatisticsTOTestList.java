package com.capgemini.chess.service.testcollections;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.data.enums.Level;
import com.capgemini.chess.service.to.PlayerStatisticsTO;

public class PlayerStatisticsTOTestList {

	public static List<PlayerStatisticsTO> PLAYER_STATISTICS_TO_TEST_LIST = new ArrayList<>();
	
	
	static {
		setInitialValuesOfPlayerStatisticsToTestList();
	}
	/**Restarts initial value of @PLAYER_STATISTICS_TO_TEST_LIST.
	 */
	public static void setInitialValuesOfPlayerStatisticsToTestList() {
		PLAYER_STATISTICS_TO_TEST_LIST.clear();
		
		PLAYER_STATISTICS_TO_TEST_LIST.add(new PlayerStatisticsTO(345L, Level.NEWBIE, 0, 2, 1, 1, 0, 0, 0, 0, null));
		PLAYER_STATISTICS_TO_TEST_LIST.add(new PlayerStatisticsTO(3445L, Level.NEWBIE, 10, 5, 2, 2, 1, 0, 0, 0, null));
		PLAYER_STATISTICS_TO_TEST_LIST.add(new PlayerStatisticsTO(3508745L, Level.WEAKLING, 400, 10, 5, 2, 5, 0, 0, 0, null));
		PLAYER_STATISTICS_TO_TEST_LIST.add(new PlayerStatisticsTO(23345L, Level.WEAKLING, 500, 12, 6, 3, 5, 0, 0, 0, null));
		PLAYER_STATISTICS_TO_TEST_LIST.add(new PlayerStatisticsTO(1345L, Level.BEGINNER, 700, 35, 18, 8, 5, 0, 0, 0, null));
		PLAYER_STATISTICS_TO_TEST_LIST.add(new PlayerStatisticsTO(34905L, Level.BEGINNER, 800, 40, 20, 10, 10, 0, 0, 0, null));
		PLAYER_STATISTICS_TO_TEST_LIST.add(new PlayerStatisticsTO(34345L, Level.EXPERIENCED_BEGINNER, 1500, 70, 35, 20, 15, 0, 0, 0, null));
		PLAYER_STATISTICS_TO_TEST_LIST.add(new PlayerStatisticsTO(590345L, Level.MIDDLEBROW, 2609, 100, 50, 25, 25, 0, 0, 0, null));
		PLAYER_STATISTICS_TO_TEST_LIST.add(new PlayerStatisticsTO(5873345L, Level.EXPERIENCED_MIDDLEBORW, 7000, 160, 100, 10, 50, 0, 0, 0, null));
		PLAYER_STATISTICS_TO_TEST_LIST.add(new PlayerStatisticsTO(980345L, Level.ADVANCED, 12000, 200, 100, 10, 70, 0, 0, 0, null));
		PLAYER_STATISTICS_TO_TEST_LIST.add(new PlayerStatisticsTO(34405598L, Level.ADVANCED, 16800, 200, 100, 10, 70, 0, 0, 0, null));
		PLAYER_STATISTICS_TO_TEST_LIST.add(new PlayerStatisticsTO(3674905L, Level.PROFESSIONAL, 27000, 300, 200, 50, 50, 0, 0, 0, null));
		PLAYER_STATISTICS_TO_TEST_LIST.add(new PlayerStatisticsTO(345561224234L, Level.MASTER, 60000, 390, 300, 45, 45, 0, 0, 0, null));
		PLAYER_STATISTICS_TO_TEST_LIST.add(new PlayerStatisticsTO(125345445L, Level.CHUCK_NORRIS_OF_CHESS, 80000, 450, 390, 30, 30, 0, 0, 0, null));
	}
	
}
