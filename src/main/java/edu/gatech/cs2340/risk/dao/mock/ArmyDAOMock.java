package main.java.edu.gatech.cs2340.risk.dao.mock;

import java.util.ArrayList ;

import org.apache.log4j.Logger;

import main.java.edu.gatech.cs2340.risk.dao.ArmyDAO;
import main.java.edu.gatech.cs2340.risk.model.Player;
/**
 * @author Caroline Paulus
 *
 */
public class ArmyDAOMock implements ArmyDAO {
	
	private static Logger log = Logger.getLogger(ArmyDAOMock.class);

	@Override
	public ArrayList<Player> addArmies(ArrayList<Player> players) {
		
		// determine number of armies each player should be initially assigned
		int armyCount = 50 - (players.size() * 5);
		log.debug("Each player receives " + armyCount + " armies");
		
		for (Player player : players) {
			// assign the player's army count to the determined value
			player.setNumberOfArmies(armyCount);
		}
		// return the updated list of players
		return players;
	}

}
