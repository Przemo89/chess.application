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
import com.capgemini.chess.dao.PlayerStatisticsDao;
import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.dataaccess.entities.PlayerStatisticsEntity;
import com.capgemini.chess.exception.ChallengeCreationException;
import com.capgemini.chess.exception.ChallengeDeclineException;
import com.capgemini.chess.exception.ChallengeIsNoLongerValidException;
import com.capgemini.chess.exception.ChallengeNotExistException;
import com.capgemini.chess.exception.PlayerNotExistException;
import com.capgemini.chess.service.ChallengeService;
import com.capgemini.chess.service.mapper.ChallengeMapper;
import com.capgemini.chess.service.mapper.PlayerMatchingMapper;
import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.PlayerMatchingTO;
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
	
	@Autowired
	private PlayerStatisticsDao playerStatisticsDao;
	
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
	public void shouldReturnOkStatusWhenCreatingChallenge() throws Exception {
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
	public void testShouldReturnNotFoundStatusWhenCreateManualChallenge() throws Exception {
		// given 
		long idPlayerChallengingNotExisting = 354L;
		long idPlayerChallengedNotExisting = 376L;
		
		// when
		Mockito.doThrow(new ChallengeCreationException()).when(challengeService)
				.createChallenge(idPlayerChallengingNotExisting, idPlayerChallengedNotExisting);
		ResultActions response = this.mockMvc.perform(post("/chess/challenge/manual/create/" + idPlayerChallengingNotExisting
				+ "/" + idPlayerChallengedNotExisting));

		// then
		response.andExpect(status().isNotFound());
	}
	
	@Test
	public void testShouldReturnMatchingPlayers() throws Exception {
		// given
		final long idPlayerExistingOne = 10L;
		final long idPlayerExistingTwo = 11L;
		final long idPlayerExistingThree = 12L;
		final long idPlayerExistingFour = 13L;
		final long idPlayerExistingFive = 14L;
		PlayerStatisticsEntity matchingPlayerOne = playerStatisticsDao.findOne(idPlayerExistingOne);
		PlayerStatisticsEntity matchingPlayerTwo = playerStatisticsDao.findOne(idPlayerExistingTwo);
		PlayerStatisticsEntity matchingPlayerThree = playerStatisticsDao.findOne(idPlayerExistingThree);
		PlayerStatisticsEntity matchingPlayerFour = playerStatisticsDao.findOne(idPlayerExistingFour);
		PlayerStatisticsEntity matchingPlayerFive = playerStatisticsDao.findOne(idPlayerExistingFive);
		List<PlayerStatisticsEntity> mockResultList = new ArrayList<>();
		mockResultList.add(matchingPlayerOne);
		mockResultList.add(matchingPlayerTwo);
		mockResultList.add(matchingPlayerThree);
		mockResultList.add(matchingPlayerFour);
		mockResultList.add(matchingPlayerFive);
		List<PlayerMatchingTO> mockResultListMappedToTOs = PlayerMatchingMapper.map2TOs(mockResultList);

		// when
		Mockito.when(challengeService.getMatchingPlayers(matchingPlayerOne.getId())).thenReturn(mockResultList);
		ResultActions response = this.mockMvc.perform(get("/chess/challenge/automatic/getMatchingPlayers/" + matchingPlayerOne.getId())
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("5"));

		response.andExpect(status().isOk())
				.andExpect(jsonPath("[0].id").value(new Integer((int) mockResultListMappedToTOs.get(0).getId())))
				.andExpect(jsonPath("[1].id").value(new Integer((int) mockResultListMappedToTOs.get(1).getId())))
				.andExpect(jsonPath("[2].id").value(new Integer((int) mockResultListMappedToTOs.get(2).getId())))
				.andExpect(jsonPath("[3].id").value(new Integer((int) mockResultListMappedToTOs.get(3).getId())))
				.andExpect(jsonPath("[4].id").value(new Integer((int) mockResultListMappedToTOs.get(4).getId())));
	}
	
	@Test
	public void testShouldReturnNoContentWhenGetMatchingPlayers() throws Exception {
		// given
		List<PlayerStatisticsEntity> mockResultList = new ArrayList<>();
		long idPlayerChallenging = 5;

		// when
		Mockito.when(challengeService.getMatchingPlayers(idPlayerChallenging)).thenReturn(mockResultList);
		ResultActions response = this.mockMvc.perform(get("/chess/challenge/automatic/getMatchingPlayers/" + idPlayerChallenging)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON));

		// then
		response.andExpect(status().isNoContent());
	}
	
	@Test
	public void testShouldReturnNotFoundStatusWhenGetMatchingPlayersForNotExistingPlayer() throws Exception {
		// given
		long idPlayerChallengingNotExisting = 545L;

		// when
		Mockito.doThrow(new PlayerNotExistException(idPlayerChallengingNotExisting))
				.when(challengeService).getMatchingPlayers(idPlayerChallengingNotExisting);
		ResultActions response = this.mockMvc.perform(get("/chess/challenge/automatic/getMatchingPlayers/" + idPlayerChallengingNotExisting));

		// then
		response.andExpect(status().isNotFound());
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
	public void testShouldNotFoundStatusWhenDeclineChallengeThatNotExists() throws Exception {
		// given 
		final long idChallengeNotExisting = 1078L;
		
		// when
		Mockito.doThrow(new ChallengeNotExistException(idChallengeNotExisting))
				.when(challengeService).declineChallenge(idChallengeNotExisting);
		ResultActions response = this.mockMvc.perform(delete("/chess/challenge/decline/" + idChallengeNotExisting));
		
		// then
		Mockito.verify(challengeService).declineChallenge(idChallengeNotExisting);
		response.andExpect(status().isNotFound());
	}
	
	@Test
	public void testShouldNotFoundStatusWhenDeclineChallengeByPlayerWhoNotReceivedIt() throws Exception {
		// given 
		final long idChallengeNotExisting = 107356L;
		
		// when
		Mockito.doThrow(new ChallengeDeclineException())
				.when(challengeService).declineChallenge(idChallengeNotExisting);
		ResultActions response = this.mockMvc.perform(delete("/chess/challenge/decline/" + idChallengeNotExisting));
		
		// then
		Mockito.verify(challengeService).declineChallenge(idChallengeNotExisting);
		response.andExpect(status().isNotFound());
	}
	
	@Test
	public void testShouldReturnOkStatusWhenAcceptChallenge() throws Exception {
		// given 
		final long idChallenge = 10L;
		
		// when
		ResultActions response = this.mockMvc.perform(post("/chess/challenge/accept/" + idChallenge));

		// then
		Mockito.verify(challengeService).acceptChallenge(idChallenge);
		response.andExpect(status().isOk());
	}
	
	@Test
	public void testShouldReturnNotFoundStatusWhenAcceptChallengeThatIsNoLongerValid() throws Exception {
		// given 
		final long idChallengeNotExisting = 355L;
		
		// when
		Mockito.doThrow(new ChallengeIsNoLongerValidException(idChallengeNotExisting)).when(challengeService)
				.acceptChallenge(idChallengeNotExisting);
		ResultActions response = this.mockMvc.perform(post("/chess/challenge/accept/" + idChallengeNotExisting));

		// then
		response.andExpect(status().isNotFound());
	}
	
	@Test
	public void testShouldReturnNotFoundStatusWhenAcceptChallengeThatNotExist() throws Exception {
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
	public void testShouldReturnSentChallengesWhenGettingSentChallenges() throws Exception {
		// given
		final long idChallengeExistingOne = 5L;
		final long idChallengeExistingTwo = 6L;
		final long idChallengeExistingThree = 7L;
		final long idChallengeExistingFour = 8L;
		final long idPlayerRequesting = 56669L;
		ChallengeEntity challengeExistingOne = challengeDao.findOne(idChallengeExistingOne);
		ChallengeEntity challengeExistingTwo = challengeDao.findOne(idChallengeExistingTwo);
		ChallengeEntity challengeExistingThree = challengeDao.findOne(idChallengeExistingThree);
		ChallengeEntity challengeExistingFour = challengeDao.findOne(idChallengeExistingFour);
		List<ChallengeEntity> mockResultList = new ArrayList<>();
		mockResultList.add(challengeExistingOne);
		mockResultList.add(challengeExistingTwo);
		mockResultList.add(challengeExistingThree);
		mockResultList.add(challengeExistingFour);
		List<ChallengeTO> mockResultListMappedToTOs = ChallengeMapper.map2TOs(mockResultList);

		// when
		Mockito.when(challengeService.getSentChallenges(idPlayerRequesting)).thenReturn(mockResultList);
		ResultActions response = this.mockMvc.perform(get("/chess/challenge/sentChallenges/" + idPlayerRequesting)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content("4"));

		response.andExpect(status().isOk())
			.andExpect(jsonPath("[0].idChallenge").value(new Integer((int) mockResultListMappedToTOs.get(0).getIdChallenge())))
			.andExpect(jsonPath("[1].idChallenge").value(new Integer((int) mockResultListMappedToTOs.get(1).getIdChallenge())))
			.andExpect(jsonPath("[2].idChallenge").value(new Integer((int) mockResultListMappedToTOs.get(2).getIdChallenge())))
			.andExpect(jsonPath("[3].idChallenge").value(new Integer((int) mockResultListMappedToTOs.get(3).getIdChallenge())));
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
