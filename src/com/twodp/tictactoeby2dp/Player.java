package com.twodp.tictactoeby2dp;

public class Player {

	private int Wins;

	public Player(){
		this.Wins = 0;
	}
	
	public void addWin(){
		this.Wins++;
	}
	
	public int wins(){
		return this.Wins;
	}
}
