package database;

import java.sql.*; 

public class DBConnector {
	
	public static final String MYSQL_USERNAME = "ccs108rgabriel";
	public static final String MYSQL_PASSWORD = "ohfooghu";
	public static final String MYSQL_DATABASE_SERVER = "mysql-user.stanford.edu";
	public static final String MYSQL_DATABASE_NAME = "c_cs108_rgabriel";
	
	private Connection connection = null;
	
	public DBConnector() {
		
	}

	public boolean openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			connection = DriverManager.getConnection 
					( "jdbc:mysql://" + MYSQL_DATABASE_SERVER, MYSQL_USERNAME, MYSQL_PASSWORD);
		} catch (SQLException e) {
			return false;
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return true;
	}
	
	public ResultSet query(String  query) {
		ResultSet rs = null;
		
		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.executeQuery("USE " + MYSQL_DATABASE_NAME);
			rs = stmt.executeQuery(query); 
		} catch (SQLException e) {
			//e.printStackTrace();
		} 
		
		return rs;
	}
	
	
	public boolean closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			return false;
			//e.printStackTrace();
		}
		
		return true;
	}
}
