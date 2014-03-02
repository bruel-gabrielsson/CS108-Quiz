package questions;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;
import models.Question;

public class MultipleChoice extends Question {
	public static final String type = "question_multiple_choice";
	
	public int mc_question_id = -1;
	public String question_text;
	public String answer;
	// Do an array instead??
	public String choice_a;
	public String choice_b;
	public String choice_c;
	public String choice_d;
	public String choice_e;
	public String choice_f;
	public String choice_g;
	public String choice_h;
	public String choice_i;
	public String choice_j;
	
	private DBConnector connector;
	
	public MultipleChoice() {
		super();
		connector = new DBConnector();
	}

	@Override
	public boolean save() {
		return true;
	}

	@Override
	public boolean fetch() {
		if (mc_question_id == -1) {
			return false;
		}
		
		connector.openConnection();
		String query = "SELECT * FROM question_multiple_choice WHERE mc_question_id = '" + this.mc_question_id + "'";
		ResultSet rs = connector.query(query);
		
		try {
			if (rs.next()) {
				// Question superclass
				question_text = rs.getString("question_text");
				answer = rs.getString("answer");
				name = rs.getString("name");
				choice_a = rs.getString("choice_a");
				choice_b = rs.getString("choice_b");
				choice_c = rs.getString("choice_c");
				choice_d = rs.getString("choice_d");
				choice_e = rs.getString("choice_e");
				choice_f = rs.getString("choice_f");
				choice_g = rs.getString("choice_g");
				choice_h = rs.getString("choice_h");
				choice_i = rs.getString("choice_i");
				choice_j = rs.getString("choice_j");
				
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
