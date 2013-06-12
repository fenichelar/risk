package main.java.edu.gatech.cs2340.risk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import main.java.edu.gatech.cs2340.risk.dao.impl.ArmyDAOImpl;
import main.java.edu.gatech.cs2340.risk.model.Army;
import main.java.edu.gatech.cs2340.risk.model.Player;
import main.java.edu.gatech.cs2340.risk.service.ArmyService;

public class ArmyServiceImpl implements ArmyService {
	
	private ArmyDAOImpl armyDAO = new ArmyDAOImpl();

	@Override
	public ArrayList<Player> addArmies(ArrayList<Player> players) {
		return armyDAO.addArmies(players);
	}

	@Override
	public Player addArmies(Player player) {
		return armyDAO.addArmies(player);
	}

}
