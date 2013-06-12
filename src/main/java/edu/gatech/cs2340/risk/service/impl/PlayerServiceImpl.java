package main.java.edu.gatech.cs2340.risk.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import main.java.edu.gatech.cs2340.risk.dao.impl.PlayerDAOImpl;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.service.PlayerService;

/**
 * @author Caroline Paulus
 *
 */
public class PlayerServiceImpl implements PlayerService {
	
	private PlayerDAOImpl playerDAO = new PlayerDAOImpl();
	

	@Override
	public ArrayList<Player> getPlayers() {
		try {
			return playerDAO.getPlayers();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO do something here
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<Player> getPlayer(int playerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player addPlayer(Player player) {
		try {
			return playerDAO.addPlayer(player);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO handle exceptions
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Player deletePlayer(int playerId) {
		try {
			return playerDAO.deletePlayer(playerId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static TreeMap<Integer, Player> assignArmiesToPlayers(TreeMap<Integer, Player> players) {
		
		int armyCount = (50 - (players.size() * 5));
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setArmyCount(armyCount);
		}
		return players;
	}

}
