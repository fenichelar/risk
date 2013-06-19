package main.java.edu.gatech.cs2340.risk.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import main.java.edu.gatech.cs2340.risk.model.Player;

public interface ArmyDAO {
	
	public ArrayList<Player> addArmies(ArrayList<Player> players) 
			throws SQLException, ClassNotFoundException;
	
	public Player addArmies(Player player);

}
