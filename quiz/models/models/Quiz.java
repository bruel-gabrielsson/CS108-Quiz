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
	private final int id;
	private static int id_counter = 0;
	
	/** Mapping back to User */
	public int user_id;
	/**
	 * 
	 */
	Quiz() {
		// Every instance gets a unique id
		id_counter ++;
		id = id_counter;
	}
	@Override
	public void save() {
		
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
