package com.capgemini.chess.dataaccess.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.capgemini.domain.PlayerProfileListener;

@Entity
@Table(name = "player_profile")
@EntityListeners(value = PlayerProfileListener.class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PlayerProfileEntity extends VersionControl implements Serializable {

	private static final long serialVersionUID = -3489573491L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "login", nullable = false, length = 45, unique = true)
	private String login;
	
	@Column(name = "password", nullable = false, length = 25)
	private String password;
	
	@Column(name = "first_name", nullable = true, length = 45)
	private String firstName;
	
	@Column(name = "last_name", nullable = true, length = 25)
	private String lastName;
	
	@Column(name = "email", nullable = false, length = 45, unique = true)
	private String email;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_player_statistics", nullable = false, unique = true)
	private PlayerStatisticsEntity playerStatistics;
	
	@Lob
	private String aboutMe;
	
	@Lob
	private String lifeMotto;
	
	public PlayerProfileEntity() {
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getLifeMotto() {
		return lifeMotto;
	}

	public void setLifeMotto(String lifeMotto) {
		this.lifeMotto = lifeMotto;
	}
	
	public PlayerStatisticsEntity getPlayerStatistics() {
		return playerStatistics;
	}

	public void setPlayerStatistics(PlayerStatisticsEntity playerStatistics) {
		this.playerStatistics = playerStatistics;
	}
}
