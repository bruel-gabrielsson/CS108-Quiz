package models;

import java.sql.*;
import database.DBConnector;

public class Challenge implements model {
	
	public int challenge_id = -1;
	public int to_user_id;
	public int from_user_id;
	public int quiz_id;
	public int challenge_status;
	public String time_sent;
	
	public String from_user_name;
	public String quiz_name;
	
	private DBConnector connector;
	
	public Challenge() {
		connector = new DBConnector();
	}

	@Override
	public boolean save() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fetch() {
		if (this.challenge_id == -1) {
			return false;
		}
				
		String query;
		ResultSet rs;
		
		query = "SELECT * FROM challenge WHERE challenge_id = '" + this.challenge_id + "'";
		rs = connector.query(query);
		
		try {
			if (rs.next()) {
				this.to_user_id = rs.getInt("to_user_id");
				this.from_user_id = rs.getInt("from_user_id");
				this.quiz_id = rs.getInt("quiz_id");
				this.challenge_status = rs.getInt("challenge_status");
				this.time_sent = rs.getString("time_sent");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		query = "SELECT user_name FROM user WHERE user_id = '" + this.from_user_id + "'";
		rs = connector.query(query);
		
		try {
			if (rs.next()) {
				this.from_user_name = rs.getString("user_name");
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
		
		return true;
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean destroy() {
		return false;
	}

}
