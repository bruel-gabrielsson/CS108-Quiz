package models;
/**
 * 
 * @author rickardbruelgabrielsson
 * Quiz class
 * 
 * RELATIONS:
 * 
 * HAS-MANY: QUESTIONS
 * 
 * BELONGS-TO: USER
 */
public class Quiz implements model {
	public int quiz_id;
	public String quiz_name;
	public int creator_id;
	public String date_created;
	public int times_taken;
	public long quiz_timer;
	
	/** Mapping back to User */
	public int user_id;
	
	public Question[] questions;
	/**
	 * 
	 */
	Quiz() {
		// Every instance gets a unique id
		
	}
	@Override
	public boolean save() {
		return true;
	}
	@Override
	public boolean fetch() {
		return true;
		
		// populate everything
		// Populare questions, find the questions type, to know what questions to create
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
