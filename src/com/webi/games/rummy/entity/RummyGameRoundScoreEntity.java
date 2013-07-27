package com.webi.games.rummy.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table (name="RummyGameRoundScore_Tbl")
@IdClass (RummyGameRoundScorePK.class)
public class RummyGameRoundScoreEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2632986550559488827L;
	@Id
	private long gameId;
	@Id
	private String playerId;
	@Id
	private int roundId;
	private int score;

	public RummyGameRoundScoreEntity() { }
	
	public RummyGameRoundScoreEntity(long gameId, String playerId,
			int roundId, int score) {
		super();
		this.gameId = gameId;
		this.playerId = playerId;
		this.roundId = roundId;
		this.score = score;
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
	public int getRoundId() {
		return roundId;
	}
	public void setRoundId(int roundId) {
		this.roundId = roundId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
