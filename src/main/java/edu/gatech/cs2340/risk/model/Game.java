package main.java.edu.gatech.cs2340.risk.model;

import java.util.ArrayList;

public class Game {
	
	private ArrayList<Player> players;

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public void addPlayer(Player player) {
		if (players == null) {
			players = new ArrayList<Player>();
		}
		players.add(player);
	}

}
