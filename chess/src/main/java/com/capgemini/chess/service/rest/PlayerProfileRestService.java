package com.capgemini.chess.service.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.chess.service.PlayerProfileService;
import com.capgemini.chess.service.mapper.PlayerProfileAndStatisticsMapper;
import com.capgemini.chess.service.to.PlayerProfileAndStatisticsTO;

@RequestMapping("/chess/player/profile")
@RestController
public class PlayerProfileRestService {

	@Autowired
	private PlayerProfileService playerProfileService;
	
	@RequestMapping(value = "/statistics/{idPlayerProfile}", method = RequestMethod.GET)
	public ResponseEntity<PlayerProfileAndStatisticsTO> getPlayerProfileAndStatisticsByIdPlayerProfile(
			@PathVariable(value = "idPlayerProfile") final long idPlayerProfile) {
		PlayerProfileAndStatisticsTO playerProfileAndStatistics = PlayerProfileAndStatisticsMapper
				.map(playerProfileService.readPlayerProfile(idPlayerProfile));
		return new ResponseEntity<PlayerProfileAndStatisticsTO>(playerProfileAndStatistics, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/statistics/getByLogin/{login}", method = RequestMethod.GET)
	public ResponseEntity<List<PlayerProfileAndStatisticsTO>> getPlayerProfileAndStatisticsByLogin(
			@PathVariable(value = "login") final String login) {
		List<PlayerProfileAndStatisticsTO> playerProfilesAndStatistics = PlayerProfileAndStatisticsMapper
				.map2TOs(playerProfileService.getPlayersProfilesByLogin(login));
		return new ResponseEntity<List<PlayerProfileAndStatisticsTO>>(playerProfilesAndStatistics, HttpStatus.OK);
	}
	
}
