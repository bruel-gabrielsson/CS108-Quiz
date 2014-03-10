package models;

import java.util.ArrayList;

/**
 * 
 * @author rickardbruelgabrielsson
 * Question class
 * 
 * RELATIONS:
 * 
 * BELONGS-TO: QUIZ
 */
public abstract class Question implements model {
	// Generic to all questions types:
	public String type = null; // MUST BE HERE, otherwise not accessible before knowing type
	public String name = null;
	public int question_type_id = -1;
	public int question_id = 1;
	public String date_created = null;
	public int question_number = -1;
	public String answer = null;
	
	/** Mapping back to Quiz */
	public int quiz_id = -1;
	
	

	/**
	 * 
	 */
	public Question() {
		
		
	}
	
	public abstract Question clone();
}
