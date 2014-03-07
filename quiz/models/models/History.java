package models;

import java.sql.*;

import database.DBConnector;

public class History implements model {
	
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
		return true;
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
		return true;
	}
}
