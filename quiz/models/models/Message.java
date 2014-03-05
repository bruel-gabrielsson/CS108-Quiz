package models;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;

public class Message implements model {
	public String error = null;

	public int message_id = -1;
	public int to_user_id = -1;
	public int from_user_id = -1;
	
	public String title;
	public String body;
	public String time_sent;
	public String from_user_name;
	
	private DBConnector connector;
	
	public Message() {
		connector = new DBConnector();
	}
	
	@Override
	public boolean save() {
		// Must generate id if not already existing, can that be checked in database?
		return true;
	}
	
	@Override
	public boolean fetch() {
		this.error = null;
		
		// Populate quiz info
		if (this.message_id == -1) {
			this.error = "id was not specified";
			return false;
		}
		
		connector.openConnection();
		
		String msgQuery = "SELECT * FROM message WHERE message_id = '" + this.message_id + "'";
		ResultSet rs = connector.query(msgQuery);
		try {
			while(rs.next()) {
				this.to_user_id = rs.getInt("to_user_id");
				this.from_user_id = rs.getInt("from_user_id");
				this.title = rs.getString("title");
				this.body = rs.getString("body");
				this.time_sent = rs.getString("time_sent");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			//return false;
		}		
		
		
		System.out.println("Obtaining info for user " + this.from_user_id);
		
		String userQuery = "SELECT * FROM user WHERE user_id = '" + this.from_user_id + "'";
		rs = null;
		rs = connector.query(userQuery);
		try {
			while(rs.next()) {
				this.from_user_name = rs.getString("user_name");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}	
		
		connector.closeConnection();
		
		return true;
	}
	
	@Override
	public boolean update() {
		// Update fields to database
		return true;
	}
	
	@Override
	public boolean destroy() {
		// Destroy the column from the database
		
		// Set all the quizzes user_id to null or something similar to indicate that user has been destroyed
		return true;
	}
	
}
