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
		if (challenge_id >= 0) {
			String[] updateStmt = new String[1];
			updateStmt[0] = "UPDATE challenge SET to_user_id = " + to_user_id + ", " +
					"from_user_id = " + from_user_id + ", " + 
					"quiz_id = " + quiz_id + ", " + 
					"challenge_status = " + challenge_status + ", " +
					"time_sent = \"" + time_sent + "\", " +
					"WHERE challenge_id = " + challenge_id;
			System.out.println("challenge update: " + updateStmt[0]);
			int result = connector.updateOrInsert(updateStmt);
			if(result < 0){
				System.err.println("There was an error in the UPDATE call to the CHALLENGE table");
				return false;
			}
			return true;
		} else {
			String[] insertStmt = new String[1];
			insertStmt[0] = "INSERT INTO challenge(to_user_id, from_user_id, quiz_id, challenge_status, time_sent) VALUES(" +
			to_user_id + ", " + from_user_id + ", " + quiz_id + ", " + challenge_status + ", NOW())";
			System.out.println("challenge insert: " + insertStmt[0]);
			int result = connector.updateOrInsert(insertStmt);
			if(result < 0){
				System.err.println("There was an error in the INSERT call to the CHALLENGE table");
				return false;
			}
			return true;
		}
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
