package com.capgemini.chess.dataaccess.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.capgemini.chess.algorithms.data.enums.Level;

@Entity
@Table(name = "challenges")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ChallengeEntity extends VersionControl implements Serializable {

	private static final long serialVersionUID = 16754467L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_player_challenging", nullable = false)
	private PlayerStatisticsEntity playerChallenging;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_player_challenged", nullable = false)
	private PlayerStatisticsEntity playerChallenged;
	
	@Column(name = "level_player_challenging", nullable = false)
	@Enumerated(EnumType.STRING)
	private Level levelPlayerChallenging;
	
	@Column(name = "level_player_challenged", nullable = false)
	@Enumerated(EnumType.STRING)
	private Level levelPlayerChallenged;
	
	
	public ChallengeEntity() {
		
	}
	
	/**If some player will sent again challenge to some player and it will turn out, that
	 * such challenge already exists, then this challenge is going to be updated with 
	 * actual informations (both players' levels) and creation date is set a new.
	 */
	@PreUpdate
	public void setDateCreateANewBeforeUpdate() {
		this.setDateCreated(new Date());
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PlayerStatisticsEntity getPlayerChallenging() {
		return playerChallenging;
	}

	public void setPlayerChallenging(PlayerStatisticsEntity playerChallenging) {
		this.playerChallenging = playerChallenging;
	}

	public PlayerStatisticsEntity getPlayerChallenged() {
		return playerChallenged;
	}

	public void setPlayerChallenged(PlayerStatisticsEntity playerChallenged) {
		this.playerChallenged = playerChallenged;
	}

	public Level getLevelPlayerChallenging() {
		return levelPlayerChallenging;
	}

	public void setLevelPlayerChallenging(Level levelOfChallengingPlayer) {
		this.levelPlayerChallenging = levelOfChallengingPlayer;
	}

	public Level getLevelPlayerChallenged() {
		return levelPlayerChallenged;
	}

	public void setLevelPlayerChallenged(Level levelOfChallengedPlayer) {
		this.levelPlayerChallenged = levelOfChallengedPlayer;
	}
}
