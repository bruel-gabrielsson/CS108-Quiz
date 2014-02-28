package models;

import java.util.ArrayList;
import java.sql.*; 

import database.DBConnector;
import app.App;

/**
 * 
 * @author rickardbruelgabrielsson
 * User class
 * 
 * RELATIONS (Should always be specified):
 * HAS-MANY: QUIZZES
 * 
 * FETCH: BASED ON user_name
 */
public class User implements model {
	
	public int user_id;
	public String date_created;
	public String user_name = null;
	public int message_received;
	public int challenge_received;
	
	public ArrayList<Quiz> quizzes = null;
	
	private boolean is_admin = false;
	
	private DBConnector connector = null;
	
	
	/**
	 * 
	 */
	public User() {
		connector = new DBConnector();
		
	}
	
	public boolean signIn(String password) {
		if (this.user_name == null) {
			return false;
			// and if log in succeded
		}
		return true;
	}
	
	// Through relationship?
	public void friendRequest(int friend_id) {
		
	}
	
	// Checks if user is an admin 
	public boolean isAdming() {
		
		return true;
	}
	
	@Override
	public boolean save() {
		// Write to database
		return true;
	}
	
	@Override
	public boolean fetch() {
		// populate all the fields
		if (this.user_name == null) {
			return false;
		}
		
		String query = "SELECT * FROM user WHERE user_name = "+ this.user_name +"";
		connector.openConnection();
		ResultSet rs = connector.query(query);
		
		try {
			this.user_id = rs.getInt("user_id");
			this.date_created = rs.getString("date_created");
			this.user_name = rs.getString("user_name");
			this.message_received = rs.getInt("message_received");
			this.challenge_received = rs.getInt("challenge_received");
			this.is_admin = rs.getInt("is_admin") == 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// also populate the this.quizzes list with quizzes whose user_id == this.user_id
		
		String quiz_query = "SELECT quiz_id FROM quiz WHERE user_id = " + this.user_id + "";
		rs = null;
		rs = connector.query(quiz_query);
		
		try {
			while(rs.next()) {
				int quiz_id = rs.getInt("quiz_id");
				Quiz temp_quiz = new Quiz();
				temp_quiz.quiz_id = quiz_id;
				if (temp_quiz.fetch()) {
					this.quizzes.add(temp_quiz);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
