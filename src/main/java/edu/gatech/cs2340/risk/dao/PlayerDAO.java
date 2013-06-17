package main.java.edu.gatech.cs2340.risk.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.exception.PackageNotFoundException;
import main.java.edu.gatech.cs2340.risk.model.Player;
/**
 * @author Caroline Paulus
 *
 */
public interface PlayerDAO {
	
	public ArrayList<Player> getPlayers() 
			throws SQLException, ClassNotFoundException; // returns all players
	
	public ArrayList<Player> getPlayer(int playerId); // returns player with id = playerId
	
	public Player addPlayer(Player player)
			throws SQLException, ClassNotFoundException;

	public Player deletePlayer(int playerId) 
			throws SQLException, ClassNotFoundException;
}
