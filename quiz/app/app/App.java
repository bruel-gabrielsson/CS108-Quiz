package app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import questions.FreeResponse;

import com.mysql.jdbc.Connection;

import database.DBConnector;
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
	public String error = null;
	
	public DBConnector connector = null;
	
	public int number_users;
	public int number_quizzes;
	
	public App app = null;
	
	//public ArrayList<Announcement> announcements = null;
	public ArrayList<Quiz> popular_quizzes = null;
	public ArrayList<Quiz> recent_quizzes = null;
	
	public User current_user = null;
	
	public Quiz current_quiz = null;
	public Question current_question = null;
	public ArrayList<Question> current_questions = null;
	
	public void initialize() {
		System.out.println("Starting APP!");
		// Fetch all the overall information for the app, top scores, etc
		
		number_users = 1;
		number_quizzes = 1;
		
		current_user = new User();
		
		/// TESTING
		
		current_user.user_name = "Tyler";
		if (current_user.fetch()) {
			System.out.println("success");
			
		}
		
		if(fetchPopularQuizzes()) {
			// fetching popular quizzes
		}
		
		if(fetchRecentQuizzes()) {
			// fetching recent quizzess
		}
		
//		// TEW: test user.save for update 
//		this.current_user.am_challenges_sent = 100;
//		if(this.current_user.save()) {
//			System.out.println("Successful update");
//		}
		
//		// TEW: test user.save for a new user
//		this.current_user = new User();
//		this.current_user.user_name = "Kerry";
//		this.current_user.password = "abc";
//		this.current_user.salt = "1234567";
//		if(this.current_user.save()) {
//			System.out.println("Successfully created new user");
//		}
		
//		// TEW: test user.destroy
//		if(this.current_user.destroy()){
//			System.out.println("Successfully deleted user");
//		}
								
//		FreeResponse fr = (FreeResponse) this.current_user.quizzes.get(0).questions.get(0);
//		System.out.println(fr.question_text);
//		System.out.println(fr.name);
//		System.out.println(fr.type);
//		
		if(this.current_user.quizzes != null){
			for (Question q : this.current_user.quizzes.get(0).questions) {
				if (q.type == "question_free_response") {
					FreeResponse nq = (FreeResponse) q;
					String s1 = nq.name + nq.answer + nq.question_text;
					System.out.println(s1);
	
				}
			}
		}
	
		
		
	}
	
	public App() {
		connector = new DBConnector();
	}
	
	private boolean fetchPopularQuizzes() {
		this.error = null;
		
		String quizQuery = "SELECT * FROM quiz ORDER BY times_taken LIMIT 5";
		ResultSet rs = connector.query(quizQuery);
		
		this.popular_quizzes = new ArrayList<Quiz>();	
		try {
			while(rs.next()) {
				int quiz_id = rs.getInt("quiz_id");
				Quiz temp_quiz = new Quiz();
				temp_quiz.quiz_id = quiz_id;
				if (temp_quiz.fetch()) {
					this.popular_quizzes.add(temp_quiz);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	private boolean fetchRecentQuizzes() {
		this.error = null;
		
		String quizQuery = "SELECT * FROM quiz ORDER BY date_created LIMIT 5";
		ResultSet rs = connector.query(quizQuery);
		
		this.recent_quizzes = new ArrayList<Quiz>();	
		try {
			while(rs.next()) {
				int quiz_id = rs.getInt("quiz_id");
				Quiz temp_quiz = new Quiz();
				temp_quiz.quiz_id = quiz_id;
				if (temp_quiz.fetch()) {
					this.recent_quizzes.add(temp_quiz);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
		
	// How are quizzes created???
}
