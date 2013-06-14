package main.java.edu.gatech.cs2340.risk.dao.mock;

import java.sql.SQLException;
import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.dao.ArmyDAO;
import main.java.edu.gatech.cs2340.risk.model.Army;
import main.java.edu.gatech.cs2340.risk.model.Player;

public class ArmyDAOMock implements ArmyDAO {

	@Override
	public ArrayList<Player> addArmies(ArrayList<Player> players) {
		
		int armyCount = 50 - (players.size() * 5);
		int armyIdCount = 0;
		
		for (Player player : players) {
			for (int i = 0; i < armyCount; i++) {
				Army army = new Army(armyIdCount + i, player);
				player.addArmy(army);
			}
			armyIdCount = armyIdCount + armyCount;
		}
		return players;
	}

	@Override
	public Player addArmies(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

}
