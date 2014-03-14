package questions;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnector;
import models.Question;

public class MultipleChoice extends Question {
	public String error = null;
	
	public static final String this_type = "question_multiple_choice";
	public static final int this_question_type_id = 3;
	
	public int mc_question_id = -1;
	public String question_text;

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
		question_type_id = this_question_type_id;
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
		if(mc_question_id >= 0) {

			String[] updateStmt = new String[2];
			updateStmt[0] = "UPDATE question_multiple_choice SET question_number = " + question_number + ", " +
					"name = \"" + name + "\", " + 
					"question_text = \"" + question_text + "\", " +
					"answer = \"" + answer + "\", " + 
					"choice_a = ";
			if (choice_a != null) {
				updateStmt[0] += "\"" + choice_a + "\", ";
			} else {
				updateStmt[0] += "NULL, ";
			}
			
			updateStmt[0] +="choice_b = ";
			if (choice_b != null) {
				updateStmt[0] += "\"" + choice_b + "\", ";
			} else {
				updateStmt[0] += "NULL, ";
			}
			
			updateStmt[0] +="choice_c = ";
			if (choice_c != null) {
				updateStmt[0] += "\"" + choice_c + "\", ";
			} else {
				updateStmt[0] += "NULL, ";
			}
			
			updateStmt[0] +="choice_d = ";
			if (choice_d != null) {
				updateStmt[0] += "\"" + choice_d + "\", ";
			} else {
				updateStmt[0] += "NULL, ";
			}
			
			updateStmt[0] +="choice_e = ";	
			if (choice_e != null) {
				updateStmt[0] += "\"" + choice_e + "\", ";
			} else {
				updateStmt[0] += "NULL, ";
			}
			
			updateStmt[0] +="choice_f = ";
			if (choice_f != null) {
				updateStmt[0] += "\"" + choice_f + "\", ";
			} else {
				updateStmt[0] += "NULL, ";
			}
			
			updateStmt[0] +="choice_g = ";	
			if (choice_g != null) {
				updateStmt[0] += "\"" + choice_g + "\", ";
			} else {
				updateStmt[0] += "NULL, ";
			}
			
			updateStmt[0] +="choice_h = ";
			if (choice_h != null) {
				updateStmt[0] += "\"" + choice_h + "\", ";
			} else {
				updateStmt[0] += "NULL, ";
			}
			
			updateStmt[0] +="choice_i = ";
			if (choice_i != null) {
				updateStmt[0] += "\"" + choice_i + "\", ";
			} else {
				updateStmt[0] += "NULL, ";
			}
			
			updateStmt[0] +="choice_j = ";
			if (choice_j != null) {
				updateStmt[0] += "\"" + choice_j + "\" ";
			} else {
				updateStmt[0] += "NULL ";
			}
			
			updateStmt[0] += "WHERE mc_question_id = " + mc_question_id;
			updateStmt[1] = "UPDATE quiz_question_number SET question_number = " + question_number + " " +
					"WHERE mc_question_id = " + mc_question_id;
			System.out.println("Multiple Choice update: " + updateStmt[0]);
			System.out.println(updateStmt[1]);
			int result = connector.updateOrInsert(updateStmt);
			if(result < 0){
				System.err.println("There was an error in the UPDATE call to the QUESTION_MULTIPLE_CHOICE table");
				return false;	
			}
			return true;
			
		} else {
			
			// In this case, we don't have a legit fib_question_id and need to insert rows
			String[] insertStmt = new String[2];
			insertStmt[0] = "INSERT INTO question_multiple_choice (date_created, question_type_id, question_number," + 
					" quiz_id, name, question_text, answer, choice_a, choice_b, choice_c, choice_d, choice_e, choice_f, " +
					"choice_g, choice_h, choice_i, choice_j) VALUES ( NOW(), " + question_type_id + ", " + question_number + ", " + quiz_id + ", \"" + 
					name + "\", \"" + question_text +  "\", \"" + answer + "\", "; 
			if (choice_a != null) {
				insertStmt[0] += "\"" + choice_a + "\", ";
			} else {
				insertStmt[0] += "NULL, ";
			}
			
			if (choice_b != null) {
				insertStmt[0] += "\"" + choice_b + "\", ";
			} else {
				insertStmt[0] += "NULL, ";
			}
			
			if (choice_c != null) {
				insertStmt[0] += "\"" + choice_c + "\", ";
			} else {
				insertStmt[0] += "NULL, ";
			}
			
			if (choice_d != null) {
				insertStmt[0] += "\"" + choice_d + "\", ";
			} else {
				insertStmt[0] += "NULL, ";
			}
			
			if (choice_e != null) {
				insertStmt[0] += "\"" + choice_e + "\", ";
			} else {
				insertStmt[0] += "NULL, ";
			}
			
			if (choice_f != null) {
				insertStmt[0] += "\"" + choice_f + "\", ";
			} else {
				insertStmt[0] += "NULL, ";
			}
			
			if (choice_g != null) {
				insertStmt[0] += "\"" + choice_g + "\", ";
			} else {
				insertStmt[0] += "NULL, ";
			}
			
			if (choice_h != null) {
				insertStmt[0] += "\"" + choice_h + "\", ";
			} else {
				insertStmt[0] += "NULL, ";
			}
			
			if (choice_i != null) {
				insertStmt[0] += "\"" + choice_i + "\", ";
			} else {
				insertStmt[0] += "NULL, ";
			}
			
			if (choice_j != null) {
				insertStmt[0] += "\"" + choice_j + "\" ";
			} else {
				insertStmt[0] += "NULL ";
			}
			
			insertStmt[0] += ")";
			insertStmt[1] = "INSERT INTO quiz_question_number(quiz_id, mc_question_id, question_number, "
					+ "question_type_id) VALUES( " + quiz_id + ", LAST_INSERT_ID(), " + question_number + ", " + question_type_id + ")";
			System.out.println("Multiple Choice insert: " + insertStmt[0] + "\n" + insertStmt[1]);
			int result = connector.updateOrInsert(insertStmt);
			if(result < 0){
				System.err.println("There was an error in the INSERT call to the QUESTION_MULTIPLE_CHOICE table");
				return false;	
			}
			return true;
			
		}
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
		if(mc_question_id == -1) {
			error = "No mc_question_id to delete";
			return false;
		}
		String[] deleteMcQuestion = new String[2];
		
		// Delete from quiz_question_number
		deleteMcQuestion[0] = "DELETE FROM quiz_question_number WHERE mc_question_id = " + mc_question_id;
		
		// Delete from question_free_response
		deleteMcQuestion[1] = "DELETE FROM question_multiple_choice WHERE mc_question_id = " + mc_question_id;
		
		// Delete from the database
		int result = connector.updateOrInsert(deleteMcQuestion);
		if(result < 0){
			System.err.println("There was an error in the DELETE call on a mc_question");
			error = "There was an error in the DELETE call on a mc_question";
			return false;
		}
		return true;
	}
	
	public static void main(String args[]) {
		MultipleChoice mc =	new MultipleChoice();
		mc.mc_question_id = 1;
		mc.fetch();
		mc.choice_b = null;
		mc.save();
		mc.fetch();
	}
	
}
