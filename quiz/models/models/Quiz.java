package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.DBConnector;
import questions.FillInTheBlank;
import questions.FreeResponse;
import questions.MultipleChoice;

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
	public String error = null;
	
	// private previous variables? remove update
	
	public int quiz_id = -1;
	public String quiz_name;
	public String date_created;
	public int times_taken;
	public long quiz_timer;
	
	/** Mapping back to User */
	public int creator_id = -1;
	private User creator = null;
	
	private DBConnector connector = null;
	
	public ArrayList<Question> questions = null;
	/**
	 * 
	 */
	public Quiz() {
		connector = new DBConnector();
	}
	
	// TAKING THE QUIZ, get the questions enough?
	public boolean correctQuestion(Question q, String answer) {
		//???
		
		return true;
	}
	
	@Override
	public boolean save() {
		// Must generate id if not already existing, can that be checked in database?
		
		return true;
	}
	
	/**
	 * When mapping back, its a function
	 * @return
	 */
	public User creator() {
		
		// Fetches creator if associated
		if(this.creator_id != -1 && this.creator == null) {
			User temp_user = new User();
			temp_user.user_id = this.creator_id;
			if(temp_user.fetch()) {
				System.out.println("success quiz user");
				this.creator = temp_user;
			} else {
				System.out.println(temp_user.error);
			}
		}
		
		return creator;
	}
	
	public HashMap<String, String> correctMap(HashMap<String, String> answers) {
		HashMap<String, String> feedback = new HashMap<String, String>();
		
		for (Question q : this.questions) {
			if (answers.containsKey("question" + q.question_number)) {
				System.out.println(q.answer);
				
				String answer = answers.get("question" + q.question_number);
				if (answer.equals(q.answer)) {
					feedback.put("question" + q.question_number, "Correct");
				} else {
					feedback.put("question" + q.question_number, "Incorrect");
				}
				
				/*
				if (q.type.equals("question_fill_in_blank") ) {
					FillInTheBlank fb = (FillInTheBlank) q;
					String solution = fb.answer;
					
				} else if (q.type.equals("question_free_response")) {
					FreeResponse fr = (FreeResponse) q;
					String solution = fr.answer;
					
				} else { // "question_multiple_choice"
					MultipleChoice ch = (MultipleChoice) q;
				}
				*/
			
			} else {
				feedback.put("question" + q.question_number, "No answer provided");
			}
		}
		
		return feedback;
	}
	
	/* ???
	public ArrayList<Quiz> parse(ResultSet rs) {
		ArrayList<Quiz> qs = new ArrayList<Quiz>();
		
		try {
			rs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while(rs.next()) {
				Quiz temp_quiz = new Quiz();
				temp_quiz.populate(rs);
				qs.add(temp_quiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qs;
	} */
	
	@Override
	public boolean fetch() {
		this.error = null;
		// Populate quiz info
		if (this.quiz_id == -1) {
			this.error = "Quiz id was not specified";
			return false;
		}
				
		String quizQuery = "SELECT * FROM quiz WHERE quiz_id = '" + this.quiz_id + "'";
		ResultSet rs = connector.query(quizQuery);
		
		if(rs == null) {
			this.error = "Database connection failed";
			return false;
		}
		
		this.populate(rs);
		
		return true;
	}
	
	private boolean populate(ResultSet rs) {
		try {
			while(rs.next()) {
				this.quiz_name = rs.getString("quiz_name");
				this.date_created = rs.getString("date_created");
				if (this.creator_id == -1) {
					this.creator_id = rs.getInt("creator_id");
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		// Populate questions, find the questions TYPE, to know what questions to create
		String query = "SELECT * FROM quiz_question_number WHERE quiz_id = '" + this.quiz_id + "' ORDER BY question_number";
		rs = null;
		rs = connector.query(query);
		
		// Creating instance of questions
		this.questions = new ArrayList<Question>();
		
		try {
			while(rs.next()) {
				int question_type_id = rs.getInt("question_type_id");
				
				switch(question_type_id) {
					case 1: {
						FreeResponse temp_question = new FreeResponse();
						temp_question.fr_question_id = rs.getInt("fr_question_id");
						
						
						if (temp_question.fetch()) {
							this.questions.add(temp_question);
						} else {
							System.out.println("Error fetching question type 1");
							//error fetching question
						}
						
					} break;
					
					case 2: {
						FillInTheBlank temp_question = new FillInTheBlank();
						temp_question.fib_question_id = rs.getInt("fib_question_id");
						if (temp_question.fetch()) {
							this.questions.add(temp_question);
						} else {
							System.out.println("Error fetching question type 2");
							//error fetching question
						}
						
					} break;
					
					case 3: {
						MultipleChoice temp_question = new MultipleChoice();
						temp_question.mc_question_id = rs.getInt("mc_question_id");
						if (temp_question.fetch()) {
							this.questions.add(temp_question);
						} else {
							System.out.println("Error fetching question type 2");
							//error fetching question
						}
						
					} break;
				}
					
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	@Override
	public boolean update() {
		return true;
	}
	
	@Override
	public boolean destroy() {
		return true;
	}
		
}
