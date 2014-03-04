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
	public String type; // MUST BE HERE, otherwise not accessible before knowing type
	public String name;
	public int question_type_id = -1;
	public int question_id;
	public String date_created;
	public int question_number;
	
	/** Mapping back to Quiz */
	public int quiz_id = -1;
	

	/**
	 * 
	 */
	public Question() {
		
		
	}
	
	
}
