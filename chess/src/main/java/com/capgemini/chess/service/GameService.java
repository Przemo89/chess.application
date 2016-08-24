package com.capgemini.chess.service;

import com.capgemini.chess.service.to.ChallengeTO;
import com.capgemini.chess.service.to.GameTO;

public interface GameService {

	GameTO startMatch(ChallengeTO validatedAcceptedChallenge);
}
