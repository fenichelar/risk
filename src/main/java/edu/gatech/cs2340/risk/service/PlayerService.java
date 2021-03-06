package main.java.edu.gatech.cs2340.risk.service;

import java.sql.SQLException;
import java.util.ArrayList; 

import main.java.edu.gatech.cs2340.risk.model.Player;

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
	public Player getPlayer(int playerId); 
	/**
	 * Adds the player to the database of players and returns it
	 * 
	 * @param player
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Player addPlayer(Player player) throws ClassNotFoundException, SQLException;
	/**
	 * Deletes the player from the database of players and returns it
	 * 
	 * @param playerId
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public void deletePlayer(int playerId) throws ClassNotFoundException, SQLException;

}
