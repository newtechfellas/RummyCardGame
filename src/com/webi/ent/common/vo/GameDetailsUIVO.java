package com.webi.ent.common.vo;

public class GameDetailsUIVO {

	private long gameId;
	
	private String gameName;
	
	// Comma separated list of associated player names that accepted the game
	private String acceptedPlayerNames;
	
	private boolean canGameStart;
	
	private boolean isGameStarted;
	
	// Comma separated list of associated player names
	private String associatedPlayerNames;
	
	// indicator to render deal position link
	private boolean requiresDealPosition;
	
	
	public GameDetailsUIVO() {}
	
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
	public boolean isRequiresDealPosition() {
		return requiresDealPosition;
	}
	public void setRequiresDealPosition(boolean requiresDealPosition) {
		this.requiresDealPosition = requiresDealPosition;
	}

	public String getAcceptedPlayerNames() {
		return acceptedPlayerNames;
	}

	public void setAcceptedPlayerNames(String acceptedPlayerNames) {
		this.acceptedPlayerNames = acceptedPlayerNames;
	}

	public boolean isCanGameStart() {
		return canGameStart;
	}

	public void setCanGameStart(boolean canGameStart) {
		this.canGameStart = canGameStart;
	}

	public boolean isGameStarted() {
		return isGameStarted;
	}

	public void setGameStarted(boolean isGameStarted) {
		this.isGameStarted = isGameStarted;
	}

	public String getAssociatedPlayerNames() {
		return associatedPlayerNames;
	}

	public void setAssociatedPlayerNames(String associatedPlayerNames) {
		this.associatedPlayerNames = associatedPlayerNames;
	}
}
