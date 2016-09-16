package com.capgemini.chess.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.capgemini.chess.ChessApplication;
import com.capgemini.chess.dataaccess.entities.PlayerProfileEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ChessApplication.class)
public class PlayerProfileServiceTest {

	@Autowired
	PlayerProfileService service;

	@Test
	public void testReadUserSuccessful() throws Exception {

		// when
		PlayerProfileEntity playerProfileEntity = service.readPlayerProfile(1L);
		assertNotNull(playerProfileEntity);
	}

}
