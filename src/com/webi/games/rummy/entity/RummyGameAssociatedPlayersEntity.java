package com.webi.games.rummy.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.webi.games.rummy.game.GameAcceptStatus;

@Entity
@Table ( name="RummyGameAssociatedPlayers_Tbl")
@IdClass (RummyGameAssociatedPlayersPK.class)
public class RummyGameAssociatedPlayersEntity implements Serializable {
	
	private static final long serialVersionUID = 5790763952184435685L;
	
	@Id
	private long gameId;
	@Id
	private String playerId;
	
	@Enumerated (EnumType.STRING)
	@Column(name="GAME_ACCEPT_STATUS")
	private GameAcceptStatus gameAcceptStatus;
	
	//represents the hand position sequence number of 
	// the associated player of this game
	@Column(name="HAND_POSITION_SEQ")
	private int handPositionSequenceNumber;
	
	public RummyGameAssociatedPlayersEntity() {
		super();
	}
	
	public RummyGameAssociatedPlayersEntity(long gameId, String playerId) {
		super();
		this.gameId = gameId;
		this.playerId = playerId;
		gameAcceptStatus = GameAcceptStatus.NO_RESPONSE;
	}
	
	public long getGameId() {
		return gameId;
	}
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public GameAcceptStatus getGameAcceptStatus() {
		return gameAcceptStatus;
	}

	public void setGameAcceptStatus(GameAcceptStatus gameAcceptStatus) {
		this.gameAcceptStatus = gameAcceptStatus;
	}

	public int getHandPositionSequenceNumber() {
		return handPositionSequenceNumber;
	}

	public void setHandPositionSequenceNumber(int handPositionSequenceNumber) {
		this.handPositionSequenceNumber = handPositionSequenceNumber;
	}
	
}