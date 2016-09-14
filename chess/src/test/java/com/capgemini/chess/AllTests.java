package com.capgemini.chess;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.capgemini.chess.service.ServiceTestSuite;
import com.capgemini.chess.service.rest.RestServiceTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ServiceTestSuite.class, RestServiceTestSuite.class})
public class AllTests {

}