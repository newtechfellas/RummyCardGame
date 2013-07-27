package com.webi.games.rummy.game;

import java.util.Date;

import com.webi.games.rummy.entity.RummyGameEntity;

/**
 * Represents the Game Business Object with all relevant fields of the game associated to the player of the Game
 * @author Suman Jakkula
 *
 */
public class RummyGameBO implements IRummyGame {

	private long gameId;
	
	//Optional Game Name
	private String gameName; 
	
	//Player to which this game is associated
	private String associatedPlayerId;

	private Player originatorPlayer;
	
	// Is game played till the end and completed
	private boolean isCompleted;
	
	// Is game terminated before its completion
	private boolean isTerminated;
	
	private Date creationTime;
	
	private Date lastUpdatedTime;
	
	// Is game terminated before its completion
	private boolean isActive;
	
	private boolean handPositionDetermined;
	
	//Game Accept Status
	private GameAcceptStatus gameAcceptanceStatus;
	
	public RummyGameBO populateFromEntity(RummyGameEntity rummyGameEntity) {
		this.creationTime = rummyGameEntity.getCreationTime();
		this.lastUpdatedTime = rummyGameEntity.getLastUpdatedTime();
		this.gameId = rummyGameEntity.getGameId();
		this.gameName = rummyGameEntity.getGameName();
		this.isActive = rummyGameEntity.isActive();
		this.isCompleted = rummyGameEntity.isCompleted();
		this.isTerminated = rummyGameEntity.isTerminated();
		if ( originatorPlayer == null ) {
			originatorPlayer = new Player();
		}
		this.originatorPlayer.setEmailId(rummyGameEntity.getOriginatorPlayerID());
		
		return this;
	}
	
	@Override
	public Player getWinnerPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setWinnerPlayer(Player player) {
		// TODO Auto-generated method stub
	}
	

	@Override
	public boolean isHandPositionDetermined() {
		return handPositionDetermined;
	}

	@Override
	public Player getOriginatorPlayer() {
		return originatorPlayer;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public boolean isTerminated() {
		return isTerminated;
	}

	public void setTerminated(boolean isTerminated) {
		this.isTerminated = isTerminated;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setHandPositionDetermined(boolean isHandPositionDetermined) {
		this.handPositionDetermined = isHandPositionDetermined;
	}

	public void setOriginatorPlayer(Player originatorPlayer) {
		this.originatorPlayer = originatorPlayer;
	}

	public GameAcceptStatus getGameAcceptanceStatus() {
		return gameAcceptanceStatus;
	}

	public void setGameAcceptanceStatus(GameAcceptStatus gameAcceptanceStatus) {
		this.gameAcceptanceStatus = gameAcceptanceStatus;
	}

	public String getAssociatedPlayerId() {
		return associatedPlayerId;
	}

	public void setAssociatedPlayerId(String associatedPlayerId) {
		this.associatedPlayerId = associatedPlayerId;
	}
}
