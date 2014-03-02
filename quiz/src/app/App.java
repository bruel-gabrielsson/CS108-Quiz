package app;

import java.util.ArrayList;

import questions.FreeResponse;

import com.mysql.jdbc.Connection;

import models.Question;
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
	
	public App app = null;
	
	//public ArrayList<Announcement> announcements = null;
	public ArrayList<Quiz> popular_quizzes = null;
	public ArrayList<Quiz> recently_quizzes = null;
	
	public User current_user = null;
	
	public void initialize() {
		// Fetch all the overall information for the app, top scores, etc
		
		app = new App();
		
		app.number_users = 1;
		app.number_quizzes = 1;
		
		current_user = new User();
		
		/// TESTING
		
		this.current_user.user_name = "Tyler";
		if (this.current_user.fetch()) {
			System.out.println("success");
			String s = this.current_user.challenge_received + this.current_user.date_created.toString() + this.current_user.user_name + Integer.toString(this.current_user.user_id) + this.current_user.quizzes.toString();
			System.out.println(s);
			
			FreeResponse fr = (FreeResponse) this.current_user.quizzes.get(0).questions.get(0);
			System.out.println(fr.question_text);
			System.out.println(fr.name);
			System.out.println(fr.type);
			
			for (Question q : this.current_user.quizzes.get(0).questions) {
				if (q.type == "question_free_response") {
					FreeResponse nq = (FreeResponse) q;
					String s1 = nq.name + nq.answer + nq.question_text;
					System.out.println(s1);
					
				}
			}
			
		}
	}
	
	public boolean signIn(String user_name, String password) {
		this.current_user.user_name = user_name;
		if (this.current_user.signIn(password)) {
			return this.current_user.fetch();
		} else {
			this.current_user.user_name = null;
			return false;
		}
	}
	
	
	// How are quizzes created???
}
