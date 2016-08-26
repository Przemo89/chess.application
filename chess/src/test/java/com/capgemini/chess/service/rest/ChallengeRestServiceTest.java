package com.capgemini.chess.service.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.capgemini.chess.dao.ChallengeDao;
import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.exception.ChallengeIsNoLongerValidException;
import com.capgemini.chess.exception.ChallengeNotExistException;
import com.capgemini.chess.exception.PlayerNotExistException;
import com.capgemini.chess.service.ChallengeService;
import com.capgemini.chess.service.mapper.ChallengeMapper;
import com.capgemini.chess.service.testcollections.ChallengeTOTestList;
import com.capgemini.chess.service.testcollections.PlayerStatisticsTOTestList;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.PlayerStatisticsTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:**/challenge-rest-service-test-configuration.xml", "classpath:**/database-configuration-rest-test.xml"})
@WebAppConfiguration
public class ChallengeRestServiceTest {
	
	private MockMvc mockMvc;

	@Autowired
	private ChallengeService challengeService;
	
	@Autowired
	private ChallengeDao challengeDao;
	
	@Before
	public void setup() {
		Mockito.reset(challengeService);

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");

		ChallengeRestService challengeRestService = new ChallengeRestService();
		mockMvc = MockMvcBuilders.standaloneSetup(challengeRestService).setViewResolvers(viewResolver).build();
		// Due to fact, that We are trying to construct real Bean - Challenge 
		// Rest Service, we have to use reflection to mock existing field challenge service
		ReflectionTestUtils.setField(challengeRestService, "challengeService", challengeService);
	}

	/**
	 * (Example)
	 * Sample method which convert's any object from Java to String
	 */
	private static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void shouldReturnOkStatusWhenCreatingManualChallenge() throws Exception {
		// given 
		long idOfChallengingPlayer = 354L;
		long idOfChallengedPlayer = 376L;
		
		// when
		ResultActions response = this.mockMvc.perform(post("/chess/challenge/manual/create/" + idOfChallengingPlayer
				+ "/" + idOfChallengedPlayer));

		// then
		response.andExpect(status().isOk());
	}
	
	@Test
	public void shouldReturnNotFoundStatusWhenCreateManualChallenge() throws Exception {
		// given 
		long idOfChallengingPlayer = 354L;
		long idOfChallengedPlayer = 376L;
		
		// when
		Mockito.doThrow(new PlayerNotExistException(idOfChallengingPlayer)).when(challengeService)
				.createChallenge(idOfChallengingPlayer, idOfChallengedPlayer);
		ResultActions response = this.mockMvc.perform(post("/chess/challenge/manual/create/" + idOfChallengingPlayer
				+ "/" + idOfChallengedPlayer));

		// then
		response.andExpect(status().isNotFound());
	}
	
	@Test
	public void testShouldReturn5MatchingPlayers() throws Exception {

		// given:
		PlayerStatisticsTO matchingPlayer1 = PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.get(0);
		PlayerStatisticsTO matchingPlayer2 = PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.get(1);
		PlayerStatisticsTO matchingPlayer3 = PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.get(2);
		PlayerStatisticsTO matchingPlayer4 = PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.get(3);
		PlayerStatisticsTO matchingPlayer5 = PlayerStatisticsTOTestList.PLAYER_STATISTICS_TO_TEST_LIST.get(4);
		List<PlayerStatisticsTO> mockResultList = new ArrayList<>();
		mockResultList.add(matchingPlayer1);
		mockResultList.add(matchingPlayer2);
		mockResultList.add(matchingPlayer3);
		mockResultList.add(matchingPlayer4);
		mockResultList.add(matchingPlayer5);

		// when
		Mockito.when(challengeService.getMatchingPlayers(matchingPlayer1.getId())).thenReturn(mockResultList);
		ResultActions response = this.mockMvc.perform(get("/chess/challenge/automatic/getMatchingPlayers/" + matchingPlayer1.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("5"));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("[0].id").value(new Integer((int)matchingPlayer1.getId())))
				.andExpect(jsonPath("[1].id").value(new Integer((int)matchingPlayer2.getId())))
				.andExpect(jsonPath("[2].id").value(new Integer((int)matchingPlayer3.getId())))
				.andExpect(jsonPath("[3].id").value(new Integer((int)matchingPlayer4.getId())))
				.andExpect(jsonPath("[4].id").value(new Integer((int)matchingPlayer5.getId())));
	}
	
	@Test
	public void testShouldReturnNoContentWhenGetMatchingPlayers() throws Exception {
		// given:
		List<PlayerStatisticsTO> mockResultList = new ArrayList<>();
		long idOfChallengingPlayer = 5;

		// when
		Mockito.when(challengeService.getMatchingPlayers(idOfChallengingPlayer)).thenReturn(mockResultList);
		ResultActions response = this.mockMvc.perform(get("/chess/challenge/automatic/getMatchingPlayers/" + idOfChallengingPlayer)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		// then
		response.andExpect(status().isNoContent());
	}
	
	@Test
	public void testShouldReturnOkStatusWhenDeclineChallenge() throws Exception {
		// given 
		long idChallenge = 10L;
		
		// when
		ResultActions response = this.mockMvc.perform(delete("/chess/challenge/decline/" + idChallenge));
		
		// then
		Mockito.verify(challengeService).declineChallenge(idChallenge);
		response.andExpect(status().isOk());
	}
	
	@Test
	public void testShouldReturnOkStatusWhenAcceptChallenge() throws Exception {
		// given 
		long idChallenge = 10L;
		
		// when
		ResultActions response = this.mockMvc.perform(post("/chess/challenge/accept/" + idChallenge));

		// then
		Mockito.verify(challengeService).acceptChallenge(idChallenge);
		response.andExpect(status().isOk());
	}
	
	@Test
	public void testShouldReturnNotFoundStatusWhenAcceptChallengeAndChallengeNotExist() throws Exception {
		// given 
		long idChallengeNotExisting = 355L;
		
		// when
		Mockito.doThrow(new ChallengeIsNoLongerValidException(idChallengeNotExisting)).when(challengeService)
				.acceptChallenge(idChallengeNotExisting);
		ResultActions response = this.mockMvc.perform(post("/chess/challenge/accept/" + idChallengeNotExisting));

		// then
		response.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldReturnNotFoundStatusWhenAcceptChallengeBecauseChallengeNotExist() throws Exception {
		// given 
		long idChallenge = 354L;
		
		// when
		Mockito.doThrow(new ChallengeNotExistException(idChallenge)).when(challengeService)
				.acceptChallenge(idChallenge);
		ResultActions response = this.mockMvc.perform(post("/chess/challenge/accept/" + idChallenge));

		// then
		response.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldReturnNotFoundStatusWhenAcceptChallengeBecauseChallengeIsNoLongerValid() throws Exception {
		// given 
		long idChallenge = 354L;
		
		// when
		Mockito.doThrow(new ChallengeIsNoLongerValidException(idChallenge)).when(challengeService)
				.acceptChallenge(idChallenge);
		ResultActions response = this.mockMvc.perform(post("/chess/challenge/accept/" + idChallenge));

		// then
		response.andExpect(status().isNotFound());
	}
	
	@Test
	public void testShouldReturn4SentChallenges() throws Exception {
		// given:
		final long idPlayerRequesting = 56669L;
		ChallengeTO challengeTO1 = ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(0);
		ChallengeTO challengeTO2 = ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(1);
		ChallengeTO challengeTO3 = ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(2);
		ChallengeTO challengeTO4 = ChallengeTOTestList.PROPER_CHALLENGES_WITH_PLAYER_STATISTICS_TEST_LIST.get(3);
		List<ChallengeTO> mockResultList = new ArrayList<>();
		mockResultList.add(challengeTO1);
		mockResultList.add(challengeTO2);
		mockResultList.add(challengeTO3);
		mockResultList.add(challengeTO4);
		
//		final long idChallengeExistingOne = 5L;
//		final long idChallengeExistingTwo = 6L;
//		final long idChallengeExistingThree = 7L;
//		long idPlayerRequesting = 56669L;
//		ChallengeEntity challengeExistingOne = challengeDao.findOne(idChallengeExistingOne);
//		ChallengeEntity challengeExistingTwo = challengeDao.findOne(idChallengeExistingTwo);
//		ChallengeEntity challengeExistingThree = challengeDao.findOne(idChallengeExistingThree);
//		List<ChallengeEntity> mockResultList = new ArrayList<>();
//		mockResultList.add(challengeExistingOne);
//		mockResultList.add(challengeExistingTwo);
//		mockResultList.add(challengeExistingThree);
//		List<ChallengeTO> mockResultListMappedToTOs = ChallengeMapper.map2TOs(mockResultList);

		// when
		Mockito.when(challengeService.getSentChallenges(idPlayerRequesting)).thenReturn(mockResultList);
		ResultActions response = this.mockMvc.perform(get("/chess/challenge/sentChallenges/" + idPlayerRequesting)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("4"));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("[0].idChallenge").value(new Integer((int) challengeTO1.getIdChallenge())))
				.andExpect(jsonPath("[1].idChallenge").value(new Integer((int) challengeTO2.getIdChallenge())))
				.andExpect(jsonPath("[2].idChallenge").value(new Integer((int) challengeTO3.getIdChallenge())))
				.andExpect(jsonPath("[3].idChallenge").value(new Integer((int) challengeTO4.getIdChallenge())));
	}
	
	@Test
	public void testShouldReturnNoContentWhenGetSentChallenges() throws Exception {
		// given:
		long idRequestingPlayer = 56669L;
		List<ChallengeEntity> mockResultList = new ArrayList<>();

		// when
		Mockito.when(challengeService.getSentChallenges(idRequestingPlayer)).thenReturn(mockResultList);
		ResultActions response = this.mockMvc.perform(get("/chess/challenge/sentChallenges/" + idRequestingPlayer));

		response.andExpect(status().isNoContent());
	}
	
	@Test
	public void testShouldReturn3ReceivedChallenges() throws Exception {
		// given:
		final long idChallengeExistingOne = 5L;
		final long idChallengeExistingTwo = 6L;
		final long idChallengeExistingThree = 7L;
		long idPlayerRequesting = 56669L;
		ChallengeEntity challengeExistingOne = challengeDao.findOne(idChallengeExistingOne);
		ChallengeEntity challengeExistingTwo = challengeDao.findOne(idChallengeExistingTwo);
		ChallengeEntity challengeExistingThree = challengeDao.findOne(idChallengeExistingThree);
		List<ChallengeEntity> mockResultList = new ArrayList<>();
		mockResultList.add(challengeExistingOne);
		mockResultList.add(challengeExistingTwo);
		mockResultList.add(challengeExistingThree);
		List<ChallengeTO> mockResultListMappedToTOs = ChallengeMapper.map2TOs(mockResultList);

		// when
		Mockito.when(challengeService.getReceivedChallenges(idPlayerRequesting)).thenReturn(mockResultList);
		ResultActions response = this.mockMvc.perform(get("/chess/challenge/receivedChallenges/" + idPlayerRequesting)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("3"));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("[0].idChallenge").value(new Integer((int) mockResultListMappedToTOs.get(0).getIdChallenge())))
				.andExpect(jsonPath("[1].idChallenge").value(new Integer((int) mockResultListMappedToTOs.get(1).getIdChallenge())))
				.andExpect(jsonPath("[2].idChallenge").value(new Integer((int) mockResultListMappedToTOs.get(2).getIdChallenge())));
	}
	
	@Test
	public void testShouldReturnNoContentWhenGetReceivedChallenges() throws Exception {
		// given:
		long idRequestingPlayer = 56669L;
		List<ChallengeEntity> mockResultList = new ArrayList<>();

		// when
		Mockito.when(challengeService.getReceivedChallenges(idRequestingPlayer)).thenReturn(mockResultList);
		ResultActions response = this.mockMvc.perform(get("/chess/challenge/receivedChallenges/" + idRequestingPlayer));

		response.andExpect(status().isNoContent());
	}
	
	@Test
	public void testShouldReturnOkStatusWhenRemoveOutdatedChallenges() throws Exception {
		// given when
		ResultActions response = this.mockMvc.perform(post("/chess/challenge/removeOutdated"));

		// then
		Mockito.verify(challengeService).removeOutdatedChallenges();
		response.andExpect(status().isOk());
	}
}
