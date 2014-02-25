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
public class Question implements model {
	private final int id;
	private static int id_counter = 0;
	
	/** Mapping back to Quiz */
	public int quiz_id = -1;
	/**
	 * 
	 */
	public Question() {
		// Every instance gets a unique id
		id_counter ++;
		id = id_counter;
	}
	@Override
	public void save() {
		// General verification
		if (quiz_id != -1) {
			// No mapping to a quiz
		}	
	}
	@Override
	public void fetch() {
		
	}
	@Override
	public void update() {
		
	}
	@Override
	public void destroy() {
		
	}
	
}
