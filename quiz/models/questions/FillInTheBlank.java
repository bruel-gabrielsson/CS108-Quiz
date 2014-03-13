package questions;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;
import models.Question;

public class FillInTheBlank extends Question {
	public String error = null;
	
	public static final String this_type = "question_fill_in_blank";
	
	public int fib_question_id = -1;
	public int question_number;
	public int quiz_id;
	public int question_type_id = 2;
	public String name = null;
	public String question_text_before;
	public String question_text_after;
	//public String answer;
	
	
	private DBConnector connector;
	
	public FillInTheBlank() {
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
		FillInTheBlank clone = new FillInTheBlank();
		clone.question_number = this.question_number;
		clone.question_type_id = this.question_type_id;
		clone.quiz_id = this.quiz_id;
		clone.name = this.name;
		clone.question_text_after = this.question_text_after;
		clone.question_text_before = this.question_text_before;
		clone.answer = this.answer;
		clone.date_created = this.date_created;
		clone.type = this.type;
		
		return clone;
	}
	
	// TEW: implemented save function (needs testing!)
	@Override
	public boolean save() {
		// If we have a positive question id, then we are just trying to update
		// We will force a question to keep both its type and quiz, thus the only things than can be updated are done so below
		if(fib_question_id >= 0){
			String[] updateStmt = new String[2];
			updateStmt[0] = "UPDATE question_fill_in_blank SET question_number = " + question_number + ", " +
					"name = \"" + name + "\", " + 
					"question_text_before = \"" + question_text_before + "\", " +
					"question_text_after = \"" + question_text_after + "\", " +
					"answer = \"" + answer + "\" " + 
					"WHERE fib_question_id = " + fib_question_id;
			updateStmt[1] = "UPDATE quiz_question_number SET question_number = " + question_number + " " +
					"WHERE fib_question_id = " + fib_question_id;
			System.out.println("Fill In The Blank update: " + updateStmt[0]);
			System.out.println(updateStmt[1]);
			int result = connector.updateOrInsert(updateStmt);
			if(result < 0){
				System.err.println("There was an error in the UPDATE call to the QUESTION_FILL_IN_BLANK table");
				return false;	
			}
			return true;
			
		} else {
			// In this case, we don't have a legit fib_question_id and need to insert rows
			String[] insertStmt = new String[2];
			insertStmt[0] = "INSERT INTO question_fill_in_blank(date_created, question_type_id, question_number," + 
					" quiz_id, name, question_text_before, question_text_after, answer) VALUES ( NOW(), 2, " +
					question_number + ", " + quiz_id + ", " + name + ", " + question_text_before + ", " + 
					question_text_after + ", " + answer + ")";
			insertStmt[1] = "INSERT INTO quiz_question_number(quiz_id, fib_question_id, question_number, "
					+ "question_type_id) VALUES( " + quiz_id + ", LAST_INSERT_ID, " + question_number + ", 2)";
			System.out.println("Fill In Blank insert: " + insertStmt[0] + "\n" + insertStmt[1]);
			int result = connector.updateOrInsert(insertStmt);
			if(result < 0){
				System.err.println("There was an error in the INSERT call to the QUESTION_FILL_IN_BLANK table");
				return false;	
			}
			return true;		
		}
	}

	// TEW: updated fetch method to include question_number, quiz_id, name
	@Override
	public boolean fetch() {
		this.error = null;
		
		if (fib_question_id == -1) {
			this.error = "Question id not specified";
			return false;
		}
		
		String query = "SELECT * FROM question_fill_in_blank WHERE fib_question_id = '" + this.fib_question_id + "'";
		ResultSet rs = connector.query(query);
		
		try {
			if (rs.next()) {
				question_type_id = rs.getInt("question_type_id");
				question_number = rs.getInt("question_number");
				quiz_id = rs.getInt("quiz_id");
				name = rs.getString("name");
				question_text_before = rs.getString("question_text_before");
				question_text_after = rs.getString("question_text_after");
				answer = rs.getString("answer");
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
		if(fib_question_id == -1) {
			error = "No fib_question_id to delete";
			return false;
		}
		String[] deleteFibQuestion = new String[2];
		
		// Delete from quiz_question_number
		deleteFibQuestion[0] = "DELETE FROM quiz_question_number WHERE fib_question_id = " + fib_question_id;
		
		// Delete from question_fill_in_blank
		deleteFibQuestion[1] = "DELETE FROM question_fill_in_blank WHERE fib_question_id = " + fib_question_id;
		
		// Delete from the database
		int result = connector.updateOrInsert(deleteFibQuestion);
		if(result < 0){
			System.err.println("There was an error in the DELETE call on a fib_question");
			error = "There was an error in the DELETE call on a fib_question";
			return false;
		}
		return true;
	}
	
}
