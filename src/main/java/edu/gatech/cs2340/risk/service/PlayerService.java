package main.java.edu.gatech.cs2340.risk.service;

import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.exception.PackageNotFoundException;
import main.java.edu.gatech.cs2340.risk.model.Player;

/**
 * @author Caroline Paulus
 *
 */
public interface PlayerService {
	
	public ArrayList<Player> getPlayers() throws PackageNotFoundException; // returns all players
	
	public ArrayList<Player> getPlayer(int playerId); // returns player with id = playerId
	
	public Player addPlayer(Player player) throws PackageNotFoundException;
	
	public Player deletePlayer(int playerId);

}
