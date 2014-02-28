package questions;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;
import models.Question;

public class FreeResponse extends Question {
	private static final String type = "QuestionResponse";
	
	public int fr_question_id = -1;
	
	public String name;
	public String question_text;
	public String answer;
	
	private DBConnector connector;
	
	public FreeResponse() {
		connector = new DBConnector();
	}

	@Override
	public boolean save() {
		return true;
	}

	@Override
	public boolean fetch() {
		
		if (fr_question_id == -1) {
			return false;
		}
		
		connector.openConnection();
		String query = "SELECT * FROM question_free_response WHERE fr_question_id = " + this.fr_question_id + "";
		ResultSet rs = connector.query(query);
		
		
		try {
			name = rs.getString("name");
			question_text = rs.getString("question_text");
			answer = rs.getString("answer");
		} catch (SQLException e) {
			return false;
			//e.printStackTrace();
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
