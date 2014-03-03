package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	// private previous variables? remove update
	
	public int quiz_id = -1;
	public String quiz_name;
	public String date_created;
	public int times_taken;
	public long quiz_timer;
	
	/** Mapping back to User */
	public int creator_id;
	
	private DBConnector connector;
	
	public ArrayList<Question> questions = null;
	/**
	 * 
	 */
	Quiz() {
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
	@Override
	public boolean fetch() {
		
		
		// Populate quiz info
		if (this.quiz_id == -1) {
			return false;
		}
		connector.openConnection();
		String quizQuery = "SELECT * FROM quiz WHERE quiz_id = '" + this.quiz_id + "'";
		ResultSet rs = connector.query(quizQuery);
		try {
			while(rs.next()) {
				this.quiz_name = rs.getString("quiz_name");
				this.date_created = rs.getString("date_created");
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
		
		connector.closeConnection();
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
