package com.webi.games.rummy.entity;

import java.io.Serializable;

public class RummyGameRoundScorePK implements Serializable {

	private static final long serialVersionUID = -1146872933335607675L;
	private long gameId;
	private String playerId;
	private int roundId;
	
	public RummyGameRoundScorePK() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RummyGameRoundScorePK(long gameId, String playerId, int roundId) {
		super();
		this.gameId = gameId;
		this.playerId = playerId;
		this.roundId = roundId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (gameId ^ (gameId >>> 32));
		result = prime * result
				+ ((playerId == null) ? 0 : playerId.hashCode());
		result = prime * result + roundId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RummyGameRoundScorePK other = (RummyGameRoundScorePK) obj;
		if (gameId != other.gameId)
			return false;
		if (playerId == null) {
			if (other.playerId != null)
				return false;
		} else if (!playerId.equals(other.playerId))
			return false;
		if (roundId != other.roundId)
			return false;
		return true;
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
	
}
