package models;
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
	private int question_id;
	
	/** Mapping back to Quiz */
	public int quiz_id = -1;
	/**
	 * 
	 */
	public Question() {
		
	}
	
	
}
