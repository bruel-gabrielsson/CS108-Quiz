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
	
	private String name;
	
	/**
	 * 
	 */
	User(String name) {
		// Every instance gets a unique id
		id_counter ++;
		id = id_counter;
		
		this.name = name;
	}
	
	// Through relationship?
	public void friendRequest(int friend_id) {
		
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
