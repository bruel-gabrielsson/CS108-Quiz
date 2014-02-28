package models;

import java.util.ArrayList;

/**
 * 
 * @author rickardbruelgabrielsson
 * User class
 * 
 * RELATIONS (Should always be specified):
 * HAS-MANY: QUIZZES
 * 
 */
public class User implements model {
	public int user_id;
	public String date_created;
	public String user_name;
	
	public ArrayList<Quiz> quizzes = null;
	
	private boolean is_admin = false;
	
	
	/**
	 * 
	 */
	User() {
		
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
		this.date_created = "";
		this.user_name = "";
		this.is_admin = false;
		
		// also populate the this.quizzes list with quizzes whose user_id == this.user_id
		Quiz temp_quiz = new Quiz();
		temp_quiz.quiz_id = 1;
		if (temp_quiz.fetch()) {
			this.quizzes.add(temp_quiz);
		} else {
			// error fetching quizzes
		}
		
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
