package com.capgemini.chess.service.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.chess.exception.ChallengeCreationException;
import com.capgemini.chess.exception.ChallengeDeclineException;
import com.capgemini.chess.exception.ChallengeIsNoLongerValidException;
import com.capgemini.chess.exception.ChallengeNotExistException;
import com.capgemini.chess.exception.PlayerNotExistException;
import com.capgemini.chess.service.ChallengeService;
import com.capgemini.chess.service.mapper.ChallengeReceivedMapper;
import com.capgemini.chess.service.mapper.ChallengeSentMapper;
import com.capgemini.chess.service.mapper.PlayerMatchingMapper;
import com.capgemini.chess.service.to.ChallengeReceivedTO;
import com.capgemini.chess.service.to.ChallengeSentTO;
import com.capgemini.chess.service.to.PlayerMatchingTO;

@RequestMapping("/chess/challenge")
@RestController
public class ChallengeRestService {
	
	@Autowired
	private ChallengeService challengeService;
	
	@RequestMapping(value = "/manual/create/{idChallenger}/{idChallenged}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void createManualChallenge(@PathVariable(value = "idChallenger") final long idOfChallengingPlayer, 
			@PathVariable(value = "idChallenged") final long idOfChallengedPlayer) throws ChallengeCreationException {
		challengeService.createOrUpdateChallenge(idOfChallengingPlayer, idOfChallengedPlayer);
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Player does not exist.")
	@ExceptionHandler(ChallengeCreationException.class)
	private void challengeCreationExceptionHandler() {
		
	}
	
	@RequestMapping(value = "/automatic/getMatchingPlayers/{idRequestingPlayer}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PlayerMatchingTO>> getMatchingPlayers(@PathVariable
			(value = "idRequestingPlayer") final long idOfChallengingPlayer) throws PlayerNotExistException {
		List<PlayerMatchingTO> matchingPlayers = PlayerMatchingMapper.map2TOs(challengeService.getMatchingPlayers(idOfChallengingPlayer));
		if (matchingPlayers.isEmpty()) {
			return new ResponseEntity<List<PlayerMatchingTO>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<PlayerMatchingTO>>(matchingPlayers, HttpStatus.OK);
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Player does not exist.")
	@ExceptionHandler(PlayerNotExistException.class)
	private void playerNotExistExceptionHandler() {
		
	}
	
	/**Declines challenge - in this case such challenge is simply removed from DB,
	 * hence RequestMethod.DELETE .
	 * @param idChallenge 
	 * @return
	 */
	@RequestMapping(value = "/decline", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void declineChallenge(@RequestBody ChallengeReceivedTO challenge) 
					throws ChallengeNotExistException, ChallengeDeclineException {
		challengeService.declineChallenge(challenge.getIdChallenge());
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Challenge can be declined only by Player, who received it.")
	@ExceptionHandler(ChallengeDeclineException.class)
	private void challengeDeclineExceptionHandler() {
		
	}
	
	@RequestMapping(value = "/accept", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void acceptChallenge(@RequestBody ChallengeReceivedTO challenge) throws ChallengeIsNoLongerValidException, ChallengeNotExistException {
		challengeService.acceptChallenge(challenge.getIdChallenge());
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Challenge does not exist anymore.")
	@ExceptionHandler(ChallengeNotExistException.class)
	private void challengeNotExistExceptionHandler() {
		
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Players' levels changed, hence challenge is no longer valid.")
	@ExceptionHandler(ChallengeIsNoLongerValidException.class)
	private void challengeIsNoLongerValidExceptionHandler() {
		
	}
	
	@RequestMapping(value = "/sentChallenges/{idRequestingPlayer}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ChallengeSentTO>> getSentChallenges(@PathVariable(value = "idRequestingPlayer") 
			final long idRequestingPlayer) {
		List<ChallengeSentTO> sentChallenges = ChallengeSentMapper.map2TOs(challengeService.getSentChallenges(idRequestingPlayer));
		if (sentChallenges.isEmpty()) {
			return new ResponseEntity<List<ChallengeSentTO>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ChallengeSentTO>>(sentChallenges, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/receivedChallenges/{idRequestingPlayer}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ChallengeReceivedTO>> getReceivedChallenges(@PathVariable(value = "idRequestingPlayer") 
			final long idRequestingPlayer) {
		List<ChallengeReceivedTO> receivedChallenges = ChallengeReceivedMapper.map2TOs(challengeService.getReceivedChallenges(idRequestingPlayer));
		if (receivedChallenges.isEmpty()) {
			return new ResponseEntity<List<ChallengeReceivedTO>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ChallengeReceivedTO>>(receivedChallenges, HttpStatus.OK);
	}
	
	/**This method will be possible to perform only by Admin.
	 */
	@RequestMapping(value = "/removeOutdated", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void removeOutdatedChallenges() {
		challengeService.removeOutdatedChallenges();
	}
}
