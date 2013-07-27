package com.webi.games.rummy.game;

public class Player implements IPlayer {
	
	private static final long serialVersionUID = -5441837409226471276L;
	
	private String emailId;
	private String name;
	private int roundScore;

	public Player() {}
	public Player(String emailId) {
		this.emailId = emailId;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getEmailId() {
		return emailId;
	}

	@Override
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public int getRoundScore() {
		return roundScore;
	}

	@Override
	public void setRoundScore(int score) {
		this.roundScore = score;
	}

}
