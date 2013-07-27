package com.webi.games.rummy.entity;

import java.io.Serializable;

public class RummyGameAssociatedPlayersPK implements Serializable {

	private static final long serialVersionUID = 2978318415854712371L;
	private long gameId;
	private String playerId;
	
	
	public RummyGameAssociatedPlayersPK() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public RummyGameAssociatedPlayersPK(long gameId, String playerId) {
		super();
		this.gameId = gameId;
		this.playerId = playerId;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (gameId ^ (gameId >>> 32));
		result = prime * result
				+ ((playerId == null) ? 0 : playerId.hashCode());
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
		RummyGameAssociatedPlayersPK other = (RummyGameAssociatedPlayersPK) obj;
		if (gameId != other.gameId)
			return false;
		if (playerId == null) {
			if (other.playerId != null)
				return false;
		} else if (!playerId.equals(other.playerId))
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
	
	
}
