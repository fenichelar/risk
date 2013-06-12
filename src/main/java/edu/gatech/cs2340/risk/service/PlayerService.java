package main.java.edu.gatech.cs2340.risk.service;

import java.util.TreeMap;

import main.java.edu.gatech.cs2340.risk.model.Player;

public class PlayerService {
	
	public static TreeMap<Integer, Player> assignArmiesToPlayers(TreeMap<Integer, Player> players) {
		
		int armyCount = (50 - (players.size() * 5));
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setArmyCount(armyCount);
		}
		return players;
	}

}
