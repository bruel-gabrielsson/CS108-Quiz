package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnector;
import questions.FillInTheBlank;
import questions.FreeResponse;

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
	
	public ArrayList<Question> questions;
	/**
	 * 
	 */
	Quiz() {
		connector = new DBConnector();
	}
	
	// TAKING THE QUIZ, get the questions enough?
	
	@Override
	public boolean save() {
		// Must generate id if not already existing, can that be checked in database?
		
		return true;
	}
	@Override
	public boolean fetch() {
		
		
		// populate everything
		// Populare questions, find the questions TYPE, to know what questions to create
		if (this.quiz_id == -1) {
			return false;
		}
		
		connector.openConnection();
		String query = "SELECT * FROM quiz_question_number WHERE quiz_id = " + this.quiz_id + "ORDER BY question_number";
		ResultSet rs = connector.query(query);
		
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
							//error fetching question
						}
						
					} break;
					
					case 2: {
						
					} break;
					
					case 3: {
						
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
