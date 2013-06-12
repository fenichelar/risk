package main.java.edu.gatech.cs2340.risk.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import main.java.edu.gatech.cs2340.risk.dao.PlayerDAO;
import main.java.edu.gatech.cs2340.risk.model.Player;

/**
 * @author Caroline Paulus
 *
 */
public class PlayerDAOImpl implements PlayerDAO {
	
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String DB_NAME = "Risk_Database";
	private static final String DB_URL = "jdbc:mysql://localhost/";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "mypass";
	

	@Override
	public ArrayList<Player> getPlayers() throws SQLException, ClassNotFoundException {
		
		ArrayList<Player> players = new ArrayList<Player>();
		Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
		Class.forName(JDBC_DRIVER);
		
		Statement s = conn.createStatement();
		String sql = "SELECT * FROM Players;";
		System.out.println(sql); // needs to be log eventually
		ResultSet rs = s.executeQuery(sql);
		
		while (rs.next()) {
			Player player = new Player(rs.getString("name"), rs.getInt("id"));
			players.add(player);
		}
		
		s.close();
		conn.close();
		
		return players;
		
		
	}

	@Override
	public ArrayList<Player> getPlayer(int playerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player addPlayer(Player player) throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
		Class.forName(JDBC_DRIVER);
		
		Statement s = conn.createStatement();
		String sql = "INSERT INTO Players(id, name) VALUES (" 
				+ player.getPlayerId() + ", '" + player.getPlayerName() + "')";
		System.out.println(sql); // needs to be log eventually
		s.execute(sql);
		s.close();
		conn.close();
		
		return player;
	}

	@Override
	public Player deletePlayer(int playerId) throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
		Class.forName(JDBC_DRIVER);
		
		Statement s = conn.createStatement();
		String sql = "SELECT * FROM Players WHERE id = " + playerId + ";";
		System.out.println(sql); // needs to be log eventually
		ResultSet rs = s.executeQuery(sql);
		
		Player player = new Player(rs.getString("name"), rs.getInt("id"));
		if (rs.next()) {
			// this is not good - means the program found two players with the same id
			// handle this
		}
		
		sql = "DELETE * FROM PLayers WHERE id = " + playerId + ";";
		s.execute(sql);
		s.close();
		conn.close();
		
		return player;
	}

	
}