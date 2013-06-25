package main.java.edu.gatech.cs2340.risk.util;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.model.Player;

public class ArmyUtil {
	
	private static Logger log = Logger.getLogger(ArmyUtil.class);
	
	public static ArrayList<Player> addArmies(ArrayList<Player> players) {
		
		// determine number of armies each player should be initially assigned
		int armyCount = 20 - (players.size() * 5); //TODO change back to 50
		log.debug("Each player receives " + armyCount + " armies");
		
		for (Player player : players) {
			// assign the player's army count to the determined value
			player.setNumberOfArmies(armyCount);
		}
		// return the updated list of players
		return players;
	}

	public static int getArmiesToAssign(Player currentPlayer) {
		// TODO Auto-generated method stub
		return 0;
	}

}
