package main.java.edu.gatech.cs2340.risk.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RiskUtil {
	
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String DB_NAME = "Risk_Database";
	private static final String DB_URL = "jdbc:mysql://localhost/";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "mypass";

	
	public static void buildDatabase()  {
		try {
			// try to create a new database 
			createNewDatabase();
			System.out.println("Successfully created database risk_database");
		}
		catch (SQLException e) {
			// assume that database already exists - SHOULD NOT TODO deal with this
			System.out.println("Risk_database already exists");
		} catch (InstantiationException e) {
			// TODO add log statements
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO add log statements
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// add log statements
			e.printStackTrace();
		}
	}
	
	private static void createNewDatabase() 
			throws InstantiationException, IllegalAccessException, 
			ClassNotFoundException, SQLException {

	      Class.forName(JDBC_DRIVER);
	      System.out.println("Connecting to database...");
	      Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	      System.out.println("Creating database...");
	      Statement stmt = conn.createStatement();
	      
	      String sql = "CREATE DATABASE " + DB_NAME +";";
	      stmt.executeUpdate(sql);
	      System.out.println("Database created successfully...");
	}
	
	public static void checkForExistingTable(String tableName) {
		try {
			createEmptyTableInDatabase(tableName);
			System.out.println("Successfully created table " + tableName);
		}
		catch (SQLException e) {
			// assume that table exists
			System.out.println("Table " + tableName + " already exists");
		} catch (ClassNotFoundException e) {
			// TODO do something here
			e.printStackTrace();
		}
	}
	
	private static void createEmptyTableInDatabase(String tableName) throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
		Class.forName(JDBC_DRIVER);
		
		Statement s = conn.createStatement();
		s.execute("CREATE TABLE " + tableName + 
				"( id INTEGER not NULL, " +
				"name CHAR(30) NOT NULL, " +
				"PRIMARY KEY (id) )");
	}
	
	public static void checkForExistingColumn(String tableName, String columnName, String type) {
		try {
			addColumnToTable(tableName, columnName, type);
		}
		catch (SQLException e) {
			// assume column existing
		} catch (ClassNotFoundException e) {
			// TODO do something here
			e.printStackTrace();
		}
	}
	
	private static void addColumnToTable(String tableName, String columnName, String type) 
			throws ClassNotFoundException, SQLException {
		Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
		Class.forName(JDBC_DRIVER);
		
		Statement s = conn.createStatement();
		String sql = "ALTER TABLE "+ tableName +" ADD "+ columnName + " " + type + ";";
		s.execute(sql);
	}
	
	public static void deleteDatabaseIfExists() {
		try {
			deleteDatabase();
			System.out.println("Successfully deleted database");
		}
		catch (SQLException e) {
			// assume database does not exist
			e.printStackTrace();
			System.out.println("Database does not exist");
		} catch (ClassNotFoundException e) {
			// TODO do something here
			e.printStackTrace();
		}
	}
	
	private static void deleteDatabase() throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
		Class.forName(JDBC_DRIVER);
		
		Statement s = conn.createStatement();
		String sql = "DROP DATABASE " + DB_NAME +";";
		s.execute(sql);
	}

}
