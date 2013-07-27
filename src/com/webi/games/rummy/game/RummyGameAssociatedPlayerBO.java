package com.webi.games.rummy.game;

import com.webi.games.rummy.entity.RummyGameAssociatedPlayersEntity;

public class RummyGameAssociatedPlayerBO {

	//Unique Game Identifier
	private long gameId;
	
	//Associated Player
	private String playerId;
	
	// Magically matches the player name.
	private String playerName;
	
	//Was the game accepted?
	private GameAcceptStatus gameAcceptStatus;
	
	//What is the deal order of the game for this player
	private int handPositionSequenceNumber;
	
	public RummyGameAssociatedPlayerBO() {
		super();
	}
	
	public RummyGameAssociatedPlayerBO populateFromEntityObject ( RummyGameAssociatedPlayersEntity entity) {
		this.gameId = entity.getGameId();
		this.gameAcceptStatus = entity.getGameAcceptStatus();
		this.playerId = entity.getPlayerId();
		this.handPositionSequenceNumber = entity.getHandPositionSequenceNumber();
		return this;
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

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}