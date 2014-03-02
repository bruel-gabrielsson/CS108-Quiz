package questions;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;
import models.Question;

public class FillInTheBlank extends Question {
	public static final String this_type = "question_fill_in_blank";
	
	public int fib_question_id = -1;
	public String question_text_before;
	public String question_text_after;
	public String answer;
	
	private DBConnector connector;
	
	public FillInTheBlank() {
		super();
		type = this_type;
		connector = new DBConnector();
	}

	@Override
	public boolean save() {
		
		return true;
	}

	@Override
	public boolean fetch() {
		if (fib_question_id == -1) {
			return false;
		}
		
		connector.openConnection();
		String query = "SELECT * FROM question_fill_in_blank WHERE fib_question_id = '" + this.fib_question_id + "'";
		ResultSet rs = connector.query(query);
		
		try {
			if (rs.next()) {
				question_text_before = rs.getString("question_text_before");
				question_text_after = rs.getString("question_text_after");
				answer = rs.getString("answer");
				name = rs.getString("name");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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
