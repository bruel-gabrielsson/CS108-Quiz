package models;

import java.sql.*;

import database.DBConnector;

public class History implements model {
	public String error = null;
	
	public int history_id = -1;
	public int quiz_id;
	public int user_id;
	public int total_score;
	public double percent_score;
	public int quiz_time;
	
	public String quiz_name;
	public String user_name;
	
	private DBConnector connector;
	
	public History() {
		connector = new DBConnector();
	}
	
	@Override
	public boolean save() {
		if (history_id >= 0) {
			
			String[] updateStmt = new String[1];
			updateStmt[0] = "UPDATE history SET quiz_id = " + quiz_id + ", " +
					"user_id = " + user_id + ", " + 
					"total_score = " + total_score + ", " + 
					"percent_score = " + percent_score + ", " +
					"quiz_time = " + quiz_time +
					" WHERE history_id = " + history_id;
			System.out.println("history update: " + updateStmt[0]);
			int result = connector.updateOrInsert(updateStmt);
			if(result < 0){
				System.err.println("There was an error in the UPDATE call to the HISTORY table");
				return false;
			}
			return true;
			
		} else {
			
			String[] insertStmt = new String[1];
			insertStmt[0] = "INSERT INTO history(quiz_id, user_id, total_score, percent_score, quiz_time) VALUES(" +
			quiz_id + ", " + user_id + ", " + total_score + ", " + percent_score + ", " + quiz_time + ")";
			System.out.println("history insert: " + insertStmt[0]);
			int result = connector.updateOrInsert(insertStmt);
			if(result < 0){
				System.err.println("There was an error in the INSERT call to the HISTORY table");
				return false;
			}
			return true;
			
		}
	}
	
	@Override
	public boolean fetch() {
		if (this.history_id == -1) {
			return false;
		}
				
		String query;
		ResultSet rs;
		
		query = "SELECT * FROM history WHERE history_id = '" + this.history_id + "'";
		rs = connector.query(query);
		
		try {
			if (rs.next()) {
				this.quiz_id = rs.getInt("quiz_id");
				this.user_id = rs.getInt("user_id");
				this.total_score = rs.getInt("total_score");
				this.percent_score = rs.getDouble("percent_score");
				this.quiz_time = rs.getInt("quiz_time");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		query = "SELECT quiz_name FROM quiz WHERE quiz_id = '" + this.quiz_id + "'";
		rs = connector.query(query);
		
		try {
			if (rs.next()) {
				this.quiz_name = rs.getString("quiz_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		query = "SELECT user_name FROM user WHERE user_id = '" + this.user_id + "'";
		rs = connector.query(query);
		
		try {
			if (rs.next()) {
				this.user_name = rs.getString("user_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean update() {
		return true;
	}
	
	@Override
	public boolean destroy() {
		
		if(history_id == -1) {
			error = "No history_id to delete";
			return false;
		}
		String[] deleteHistory = new String[1];
		
		// Delete from history
		deleteHistory[0] = "DELETE FROM history WHERE history_id = " + history_id;
		
		
		// Delete from the database
		int result = connector.updateOrInsert(deleteHistory);
		if(result < 0){
			System.err.println("There was an error in the DELETE call on a hisotry");
			error = "There was an error in the DELETE call on a history";
			return false;
		}
		return true;
	}
	
}
