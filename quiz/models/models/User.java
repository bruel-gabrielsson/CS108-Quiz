package models;

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
	private final int id;
	private static int id_counter = 0;
	/**
	 * 
	 */
	User() {
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
