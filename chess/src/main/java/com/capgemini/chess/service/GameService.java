package com.capgemini.chess.service;

import com.capgemini.chess.dataaccess.entities.ChallengeEntity;
import com.capgemini.chess.service.to.GameTO;

public interface GameService {

	GameTO startMatch(ChallengeEntity validatedAcceptedChallenge);
}
