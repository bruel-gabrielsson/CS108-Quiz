package questions;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;
import models.Question;

public class MultipleChoice extends Question {
	public String error = null;
	
	public static final String this_type = "question_multiple_choice";
	
	public int mc_question_id = -1;
	public String question_text;
	//public String answer;
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
		type = this_type;
		connector = new DBConnector();
	}
	
	/**
	 * Returns a clone of this question, for deep copying
	 * NOTE: Does not provide id, so there are no real duplicates
	 */
	@Override
	public Question clone() {
		MultipleChoice clone = new MultipleChoice();
		clone.question_number = this.question_number;
		clone.question_type_id = this.question_type_id;
		clone.quiz_id = this.quiz_id;
		clone.name = this.name;
		clone.question_text = this.question_text;
		clone.answer = this.answer;
		clone.date_created = this.date_created;
		clone.type = this.type;
		clone.choice_a = this.choice_a; clone.choice_b = this.choice_b; clone.choice_c = this.choice_c;
		clone.choice_d = this.choice_d; clone.choice_e = this.choice_e; clone.choice_f = this.choice_f;
		clone.choice_g = this.choice_g; clone.choice_h = this.choice_h; clone.choice_i = this.choice_i;
		clone.choice_j = this.choice_j;
		
		return clone;
	}

	@Override
	public boolean save() {
		return true;
	}

	@Override
	public boolean fetch() {
		this.error = null;
		
		if (mc_question_id == -1) {
			this.error = "Question id not specified";
			return false;
		}
		
		String query = "SELECT * FROM question_multiple_choice WHERE mc_question_id = '" + this.mc_question_id + "'";
		ResultSet rs = connector.query(query);
		
		try {
			if (rs.next()) {
				question_number = rs.getInt("question_number");
				question_type_id = rs.getInt("question_type_id");
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
