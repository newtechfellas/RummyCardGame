package com.webi.games.rummy.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table  ( name="Rummy_Game_Tbl")
public class RummyGameEntity implements Serializable {

	private static final long serialVersionUID = -6059906319059232767L;

	@Id
	@GeneratedValue
	private long gameId;
	
	//Optional Game Name
	private String gameName; 
	
	//Player That created the Game
	private String originatorPlayerID;
	
	// Is game played till the end and completed
	private boolean isCompleted;
	
	// Is game terminated before its completion
	private boolean isTerminated;
	
	private Date creationTime;
	
	private Date lastUpdatedTime;
	
	// Is game terminated before its completion
	private boolean isActive;

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

	public String getOriginatorPlayerID() {
		return originatorPlayerID;
	}

	public void setOriginatorPlayerID(String originatorPlayerID) {
		this.originatorPlayerID = originatorPlayerID;
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
}
