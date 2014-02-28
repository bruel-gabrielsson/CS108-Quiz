package questions;

import models.Question;

public class MultipleChoice extends Question {
	public static final String type = "MultipleChoice";
	
	public int mc_question_id;
	public String date_created;
	public int question_type_id;
	public int question_number;
	public String name;
	public String question_text;
	public String answer;
	// Do an array instead??
	public String choce_a;
	public String choce_b;
	public String choce_c;
	public String choce_d;
	public String choce_e;
	public String choce_f;
	public String choce_g;
	public String choce_h;
	public String choce_i;
	public String choce_j;

	@Override
	public boolean save() {
		return true;
	}

	@Override
	public boolean fetch() {
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
