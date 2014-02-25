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
	private int quiz_id;
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
		// TODO Auto-generated method stub
		
	}
	@Override
	public void fetch() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
