package com.capgemini.chess.service.impl;

import org.springframework.stereotype.Service;

import com.capgemini.chess.service.UserService;
import com.capgemini.chess.service.to.UserProfileTO;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public UserProfileTO readUser(Long id) {

		return new UserProfileTO();
	}
}
