package main.java.edu.gatech.cs2340.risk.service;

import java.util.ArrayList; 

import main.java.edu.gatech.cs2340.risk.model.Player;

/**
 * @author Caroline Paulus
 *
 */
public interface PlayerService {
	/**
	 * Returns list of all players currently participating in the game
	 * 
	 * @return
	 */
	public ArrayList<Player> getPlayers(); 
	/**
	 * Returns the player with ID matching playerId
	 * 
	 * @param playerId
	 * @return
	 */
	public ArrayList<Player> getPlayer(int playerId); 
	/**
	 * Adds the player to the database of players and returns it
	 * 
	 * @param player
	 * @return
	 */
	public Player addPlayer(Player player);
	/**
	 * Deletes the player from the database of players and returns it
	 * 
	 * @param playerId
	 * @return
	 */
	public Player deletePlayer(int playerId);

}
