package questions;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;
import models.Question;

public class FreeResponse extends Question {
	public static final String this_type = "question_free_response";
	
	public String question_text = null;
	public String answer = null;
	public int fr_question_id = -1;
	
	private DBConnector connector;
	
	public FreeResponse() {
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
		
		if (fr_question_id == -1) {
			return false;
		}
		
		connector.openConnection();
		String query = "SELECT * FROM question_free_response WHERE fr_question_id = '" + this.fr_question_id + "'";
		ResultSet rs = connector.query(query);
		
		try {
			if (rs.next()) {
				question_type_id = rs.getInt("question_type_id");
				question_text = rs.getString("question_text");
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
