package com.capgemini.chess.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ChallengeServiceTest.class, UserServiceTest.class, PlayerStatisticsServiceTest.class})
public class ServiceTestSuite {

}