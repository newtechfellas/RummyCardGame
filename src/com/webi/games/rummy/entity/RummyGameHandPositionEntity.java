package com.webi.games.rummy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table ( name="GAME_HANDPOSITION_TBL")

public class RummyGameHandPositionEntity {
	@Id
	long gameId;
	
	@Column(name="USER_ID_LIST")
	String handPositionUserIdListStr;

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public String getHandPositionUserIdListStr() {
		return handPositionUserIdListStr;
	}

	public void setHandPositionUserIdListStr(String handPositionUserIdListStr) {
		this.handPositionUserIdListStr = handPositionUserIdListStr;
	}
}
