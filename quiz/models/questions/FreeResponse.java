package questions;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;
import models.Question;

public class FreeResponse extends Question {
	public String error = null;
	
	public static final String this_type = "question_free_response";
	
	public String question_text = null;
	//public String answer = null;
	public int fr_question_id = -1;
	
	private DBConnector connector;
	
	public FreeResponse() {
		super();
		type = this_type;
		connector = new DBConnector();
	}
	
	/**
	 * Returns a clone of this question, for deep copying
	 * NOTE: Does not provide id, so there are no real duplicates
	 */
	@Override
	public Question clone() {
		FreeResponse clone = new FreeResponse();
		clone.question_number = this.question_number;
		clone.question_type_id = this.question_type_id;
		clone.quiz_id = this.quiz_id;
		clone.name = this.name;
		clone.question_text = this.question_text;
		clone.answer = this.answer;
		clone.date_created = this.date_created;
		clone.type = this.type;
		
		return clone;
	}

	@Override
	public boolean save() {
		if(fr_question_id >= 0) {
			
			String[] updateStmt = new String[2];
			updateStmt[0] = "UPDATE question_free_response SET question_number = " + question_number + ", " +
					"name = \"" + name + "\", " + 
					"question_text = \"" + question_text + "\", " +
					"answer = \"" + answer + "\" " + 
					"WHERE fr_question_id = " + fr_question_id;
			updateStmt[1] = "UPDATE quiz_question_number SET question_number = " + question_number + " " +
					"WHERE fr_question_id = " + fr_question_id;
			System.out.println("Free Response update: " + updateStmt[0]);
			System.out.println(updateStmt[1]);
			int result = connector.updateOrInsert(updateStmt);
			if(result < 0){
				System.err.println("There was an error in the UPDATE call to the QUESTION_FREE_RESPONSE table");
				return false;	
			}
			return true;
			
		} else {
			
			// In this case, we don't have a legit fr_question_id and need to insert rows
			String[] insertStmt = new String[2];
			System.out.println("FREE RESPONSE QUESTION_TEXT: " + question_text);
			insertStmt[0] = "INSERT INTO question_free_response(date_created, question_type_id, question_number," + 
					" quiz_id, name, question_text, answer) VALUES ( NOW(), 1, " +
					question_number + ", " + quiz_id + ", \"" + name + "\", \"" + question_text +  "\", \"" + answer + "\")";
			insertStmt[1] = "INSERT INTO quiz_question_number(quiz_id, fr_question_id, question_number, "
					+ "question_type_id) VALUES( " + quiz_id + ", LAST_INSERT_ID(), " + question_number + ", 1)";
			System.out.println("Free Response insert: " + insertStmt[0] + "\n" + insertStmt[1]);
			int result = connector.updateOrInsert(insertStmt);
			if(result < 0){
				System.err.println("There was an error in the INSERT call to the QUESTION_FILL_IN_BLANK table");
				return false;	
			}
			return true;
			
		}
	}

	@Override
	public boolean fetch() {
		this.error = null;
		
		if (fr_question_id == -1) {
			this.error = "Question id not specified";
			return false;
		}
				
		String query = "SELECT * FROM question_free_response WHERE fr_question_id = '" + this.fr_question_id + "'";
		ResultSet rs = connector.query(query);
		
		try {
			if (rs.next()) {
				question_number = rs.getInt("question_number");
				question_type_id = rs.getInt("question_type_id");
				question_text = rs.getString("question_text");
				answer = rs.getString("answer");
				name = rs.getString("name");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean update() {
		return true;
	}

	@Override
	public boolean destroy() {
		if(fr_question_id == -1) {
			error = "No fr_question_id to delete";
			return false;
		}
		String[] deleteFrQuestion = new String[2];
		
		// Delete from quiz_question_number
		deleteFrQuestion[0] = "DELETE FROM quiz_question_number WHERE fr_question_id = " + fr_question_id;
		
		// Delete from question_free_response
		deleteFrQuestion[1] = "DELETE FROM question_free_response WHERE fr_question_id = " + fr_question_id;
		
		// Delete from the database
		int result = connector.updateOrInsert(deleteFrQuestion);
		if(result < 0){
			System.err.println("There was an error in the DELETE call on a fr_question");
			error = "There was an error in the DELETE call on a fr_question";
			return false;
		}
		return true;
	}
	
}
