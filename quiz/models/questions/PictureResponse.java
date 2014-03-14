package questions;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;
import models.Question;

public class PictureResponse extends Question {
	public String error = null;
	
	public static final String this_type = "question_picture_response";
	public static final int this_question_type_id = 4;
	
	public int pr_question_id = -1;
	public String question_text;
	public String picture_url;
	
	private DBConnector connector;
	
	public PictureResponse() {
		super();
		type = this_type;
		question_type_id = this_question_type_id;
		connector = new DBConnector();
		
	}
	
	@Override
	public Question clone() {
		PictureResponse clone = new PictureResponse();
		clone.question_number = this.question_number;
		clone.question_type_id = this.question_type_id;
		clone.quiz_id = this.quiz_id;
		clone.name = this.name;
		clone.question_text = this.question_text;
		clone.picture_url = this.picture_url;
		clone.answer = this.answer;
		clone.date_created = this.date_created;
		clone.type = this.type;
		
		return clone;
	}
	
	@Override
	public boolean save() {
		// If we have a positive question id, then we are just trying to update
				// We will force a question to keep both its type and quiz, thus the only things than can be updated are done so below
				if(pr_question_id >= 0){
					String[] updateStmt = new String[2];
					updateStmt[0] = "UPDATE question_picture_response SET question_number = " + question_number + ", " +
							"name = \"" + name + "\", " + 
							"question_text = \"" + question_text + "\", " +
							"picture_url = \"" + picture_url + "\", " +
							"answer = \"" + answer + "\" " + 
							"WHERE pr_question_id = " + pr_question_id;
					updateStmt[1] = "UPDATE quiz_question_number SET question_number = " + question_number + " " +
							"WHERE pr_question_id = " + pr_question_id;
					System.out.println("Picture Response update: " + updateStmt[0]);
					System.out.println(updateStmt[1]);
					int result = connector.updateOrInsert(updateStmt);
					if(result < 0){
						System.err.println("There was an error in the UPDATE call to the QUESTION_PICTURE_RESPONSE table");
						return false;	
					}
					return true;
					
				} else {
					// In this case, we don't have a legit fib_question_id and need to insert rows
					String[] insertStmt = new String[2];
					insertStmt[0] = "INSERT INTO question_picture_response(date_created, question_type_id, question_number," + 
							" quiz_id, name, question_text, picture_url, answer) VALUES ( NOW()," + question_type_id + ", " +
							question_number + ", " + quiz_id + ", \"" + name + "\", \"" + question_text + "\", \"" + 
							picture_url + "\", \"" + answer + "\")";
					insertStmt[1] = "INSERT INTO quiz_question_number(quiz_id, pr_question_id, question_number, "
							+ "question_type_id) VALUES( " + quiz_id + ", LAST_INSERT_ID(), " + question_number + ", " + question_type_id + ")";
					System.out.println("Picture Response insert: " + insertStmt[0] + "\n" + insertStmt[1]);
					int result = connector.updateOrInsert(insertStmt);
					if(result < 0){
						System.err.println("There was an error in the INSERT call to the QUESTION_PICTURE_RESPONSE table");
						return false;	
					}
					return true;		
				}
	}

	@Override
	public boolean fetch() {
		this.error = null;
		
		if (pr_question_id == -1) {
			this.error = "Question id not specified";
			return false;
		}
		
		String query = "SELECT * FROM question_picture_response WHERE pr_question_id = '" + this.pr_question_id + "'";
		ResultSet rs = connector.query(query);
		
		try {
			if (rs.next()) {
				
				name = rs.getString("name");
				date_created = rs.getString("date_created");
				question_number = rs.getInt("question_number");
				answer = rs.getString("answer");
				quiz_id = rs.getInt("quiz_id");
				
				question_text = rs.getString("question_text");
				picture_url = rs.getString("picture_url");
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
		if(pr_question_id == -1) {
			error = "No fib_question_id to delete";
			return false;
		}
		String[] deletePrQuestion = new String[2];
		
		// Delete from quiz_question_number
		deletePrQuestion[0] = "DELETE FROM quiz_question_number WHERE pr_question_id = " + pr_question_id;
		
		// Delete from question_fill_in_blank
		deletePrQuestion[1] = "DELETE FROM question_picture_response WHERE pr_question_id = " + pr_question_id;
		
		// Delete from the database
		int result = connector.updateOrInsert(deletePrQuestion);
		if(result < 0){
			System.err.println("There was an error in the DELETE call on a pr_question");
			error = "There was an error in the DELETE call on a pr_question";
			return false;
		}
		return true;
	}
	
}
