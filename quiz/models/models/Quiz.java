package models;

import java.util.ArrayList;

import questions.FillInTheBlank;

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
	
	public int quiz_id;
	public String quiz_name;
	public String date_created;
	public int times_taken;
	public long quiz_timer;
	
	/** Mapping back to User */
	public int creator_id;
	
	public ArrayList<Question> questions;
	/**
	 * 
	 */
	Quiz() {
		
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
		String type = "FillInTheBlank";
		
		if (type.equals("FillInTheBlank")) {
			// then find all question ids in fillInTheBlank where quiz_id == quiz_id
			int fib_question_id = 1;
			
			FillInTheBlank temp_question = new FillInTheBlank();
			temp_question.fib_question_id = fib_question_id;
			if (temp_question.fetch()) {
				this.questions.add(temp_question);
			} else {
				//error fetching question
			}
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
