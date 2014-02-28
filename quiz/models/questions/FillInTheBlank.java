package questions;

import models.Question;

public class FillInTheBlank extends Question {
	public static final String type = "FillInTheBlank";
	
	public int fib_question_id;
	public String date_created;
	public int question_type_id;
	public int question_number;
	public String name;
	public String question_text_before;
	public String question_text_after;
	public String answer;
	
	
	public FillInTheBlank() {
		// Call constructor of superclass, gives an unique id
		super();
	}

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
