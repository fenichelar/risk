package main.java.edu.gatech.cs2340.risk.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * @author Caroline Paulus
 *
 */
public class Player {
	
	private String playerName;
	private int playerId;
	private ArrayList<Territory> territories;
	private TreeMap<Territory, ArrayList<Army>> armies;
	private Color playerColor;
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public ArrayList<Territory> getTerritories() {
		return territories;
	}

	public void setTerritories(ArrayList<Territory> territories) {
		this.territories = territories;
	}
	
	public void addTerritory(Territory territory) {
		if (territories == null) {
			territories = new ArrayList<Territory>();
		}
		territories.add(territory);
	}

}
