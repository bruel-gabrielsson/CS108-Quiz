package database;

import java.sql.*; 

public class DBConnector {
	/*
	public static final String MYSQL_USERNAME = "ccs108rgabriel";
	public static final String MYSQL_PASSWORD = "ohfooghu";
	public static final String MYSQL_DATABASE_SERVER = "mysql-user.stanford.edu";
	public static final String MYSQL_DATABASE_NAME = "c_cs108_rgabriel";
	*/
	
	public static final String MYSQL_USERNAME = "ccs108twhittle";
	public static final String MYSQL_PASSWORD = "meimahae";
	public static final String MYSQL_DATABASE_SERVER = "mysql-user.stanford.edu";
	public static final String MYSQL_DATABASE_NAME = "c_cs108_twhittle";
	
	private static Connection connection = null;
	
	public DBConnector() {
		openConnection();
	}

	private boolean openConnection() {
		
		if (connection == null) {
			System.out.println("opening connection");
			try {
				Class.forName("com.mysql.jdbc.Driver"); 
				connection = DriverManager.getConnection 
						( "jdbc:mysql://" + MYSQL_DATABASE_SERVER, MYSQL_USERNAME, MYSQL_PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return false;
			} 
		}
		return true;
	}
	
	public ResultSet query(String  query) {
		ResultSet rs = null;
		
		Statement stmt;
		
		if (connection != null) {
			try {
				stmt = connection.createStatement();
				stmt.executeQuery("USE " + MYSQL_DATABASE_NAME);
				rs = stmt.executeQuery(query); 
			} catch (SQLException e) {
				//e.printStackTrace();
			} 
		} else {
			System.out.println("conneciton is null");
		}
		
		return rs;
	}
	
	// TEW: Added updateOrInsert method
	/*
	 * This method will be used to perform update or insert statements. 
	 * It will take in an array of strings, with each element representing a different statement.
	 * The database will the attempt to execute all of the statements within one transaction.
	 * If any of the transactions fail, the database will rollback to its previous state.
	 */
	public int updateOrInsert(String[]  statement) {
		int result = 0;
		Statement stmt;
		
		try {
			// Loop through the statements to be executed for the transaction
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			stmt.executeQuery("USE " + MYSQL_DATABASE_NAME);
			for(int i = 0; i < statement.length; i++){
				result = stmt.executeUpdate(statement[i]);
			}
			// If we make it to here then we have successfully executed the entire transaction
			connection.commit(); 
		} catch (SQLException e) {
			// Otherwise we encountered an error and need to rollback
			result = -1;
			e.printStackTrace();
	        if (connection != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                connection.rollback();
	            } catch(SQLException excep) {
	                e.printStackTrace();
	            }
	        }
		} finally {	
			// Regardless of the outcome, set autocommit back to true
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
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
