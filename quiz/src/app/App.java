package app;

import java.util.ArrayList;

import models.User;
import models.Quiz;


/**
 * 
 * @author rickardbruelgabrielsson
 * Class for the overlaying application
 * Takes care of the initialization of the whole application
 * and fundamental communication, gluing together components
 * 
 * Create a new app for every visit?
 */
public class App {
	public int number_users;
	public int number_quizzes;
	
	//public ArrayList<Announcement> announcements = null;
	public ArrayList<Quiz> popular_quizzes = null;
	public ArrayList<Quiz> recently_quizzes = null;
	
	public User current_user = null;
	
	public void initialize() {
		// Fetch all the overall information for the app, top scores, etc
		
		this.number_users = 1;
		this.number_quizzes = 1;
		
		
	}
	
	public boolean signIn(String user_name, String password) {
		
		return true;
	}
	
	public boolean signOut() {
		
		return true;
	}
	
	// How are quizzes created???
}
