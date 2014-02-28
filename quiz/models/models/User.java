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
	public boolean is_admin = false;
	
	public Quiz[] quizzes;
	
	
	/**
	 * 
	 */
	User(String name) {
		// Every instance gets a unique id
		this.user_name = name;
	}
	
	// Through relationship?
	public void friendRequest(int friend_id) {
		
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
