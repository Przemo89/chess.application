package com.capgemini.chess.service;

import com.capgemini.chess.service.to.UserProfileTO;

public interface UserService {
	
	public UserProfileTO readUser(Long id);
	
}
