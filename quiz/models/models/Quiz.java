package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.DBConnector;
import questions.FillInTheBlank;
import questions.FreeResponse;
import questions.MultipleChoice;
import questions.PictureResponse;

/**
 * 
 * @author rickardbruelgabrielsson
 * Quiz class
 * 
 * RELATIONS:
 * 
 * HAS-MANY: QUESTIONS
 * 
 * BELONGS-TO: USER
 */
public class Quiz implements model {
	public String error = null;
	
	// private previous variables? remove update
	
	public int quiz_id = -1;
	public String quiz_name;
	public String quiz_description;
	public String date_created;
	public int random_yn;
	public int times_taken = 0;
	public long quiz_timer = 600000;
	public String category_name;
	public double rating;
	
	/** Mapping back to User */
	public int creator_id = -1;
	private User creator = null;
	
	private DBConnector connector = null;
	
	public ArrayList<Question> questions = null;
	
	public ArrayList<History> topScores = null;
	public ArrayList<History> topScoresToday = null;
	public ArrayList<History> userScores = null;
	public ArrayList<History> recentTakers = null;
	/**
	 * 
	 */
	public Quiz() {
		connector = new DBConnector();
	}
	
	// TAKING THE QUIZ, get the questions enough?
	public boolean correctQuestion(Question q, String answer) {
		//???
		
		return true;
	}
	
	@Override
	public boolean save() {		
		if (quiz_id >= 0) {

			String[] updateStmt = new String[1];
			updateStmt[0] = "UPDATE quiz SET quiz_name = \"" + quiz_name + "\", " +
					"quiz_description = \"" + quiz_description + "\", " +
					"creator_id = " + creator_id + ", " +
					"random_yn = " + random_yn + ", " +
					"times_taken = " + times_taken + ", " +
					"quiz_timer = " + quiz_timer + ", " +
					"category_name = \"" + category_name + "\" " +
					"WHERE quiz_id = " + quiz_id;
			System.out.println("quiz update: " + updateStmt[0]);
			int result = connector.updateOrInsert(updateStmt);
			if (result < 0) {
				System.err.println("There was an error in the UPDATE call to the QUIZ table");
				return false;
			}
			return true;

		} else {
		
			String[] insertStmt = new String[1];
			insertStmt[0] =  "INSERT INTO quiz(quiz_name, quiz_description, creator_id, date_created, random_yn, times_taken, " + 
					"quiz_timer, category_name) VALUES ( \"" + quiz_name + "\", \"" + quiz_description + "\", " + creator_id + ", NOW(), " + random_yn + ", " +
					times_taken + ", " + quiz_timer + ", \"" + category_name + "\")";
			System.out.println("Quiz insert: " + insertStmt[0]);
			int result = connector.updateOrInsert(insertStmt);
			if(result < 0){
				System.err.println("There was an error in the INSERT call to the QUIZ table");
				return false;	
			}
			
			String getQuizID = "SELECT LAST_INSERT_ID() FROM dual";
			ResultSet rs = connector.query(getQuizID);
			try{
				rs.first();
				int temp_id = rs.getInt("LAST_INSERT_ID()");
				System.out.println("THE QUIZ ID IS: "+ temp_id);
				this.quiz_id = temp_id;
			} catch (SQLException e){
				e.printStackTrace();
				return false;
			}
			
			
			return true;
		}
	}
	
	/**
	 * When mapping back, its a function
	 * @return
	 */
	public User creator() {
		
		// Fetches creator if associated
		if(this.creator_id != -1 && this.creator == null) {
			User temp_user = new User();
			temp_user.user_id = this.creator_id;
			if(temp_user.fetch()) {
				System.out.println("success quiz user");
				this.creator = temp_user;
			} else {
				System.out.println(temp_user.error);
			}
		}
		
		return creator;
	}
	
	public HashMap<String, String> correctMap(HashMap<String, String> answers) {
		HashMap<String, String> feedback = new HashMap<String, String>();
		int score = 0;
		
		for (Question q : this.questions) {
			if (answers.containsKey("question" + q.question_number)) {
				System.out.println(q.answer);
				
				String answer = answers.get("question" + q.question_number);
				if (answer.equals(q.answer)) {
					feedback.put("question" + q.question_number, q.name + ": Correct");
					score ++;
				} else {
					feedback.put("question" + q.question_number, q.name + ": Incorrect");
				}
				
				/*
				if (q.type.equals("question_fill_in_blank") ) {
					FillInTheBlank fb = (FillInTheBlank) q;
					String solution = fb.answer;
					
				} else if (q.type.equals("question_free_response")) {
					FreeResponse fr = (FreeResponse) q;
					String solution = fr.answer;
					
				} else { // "question_multiple_choice"
					MultipleChoice ch = (MultipleChoice) q;
				}
				*/
			
			} else {
				feedback.put("question" + q.question_number, q.name + ": No answer provided");
			}
		}
		
		feedback.put("score", Integer.toString(score));
		
		return feedback;
	}
	
	public boolean editQuiz(HashMap<String, String> updates) {
		if (updates.containsKey("quiz_name")) {
			quiz_name = updates.get("quiz_name");
		}
		if (updates.containsKey("quiz_description")) {
			quiz_description = updates.get("quiz_description");
		}
		System.out.println(category_name);
		return save();
	}
	
	public boolean editQuizQuestions(HashMap<String, String> updates) {
		for (Question q : this.questions) {
			String type = q.type;
			if (type.equals("question_free_response")) {
				FreeResponse fr = (FreeResponse) q;
				if (updates.containsKey("question" + fr.question_number + "name")) {
					fr.name = updates.get("question" + fr.question_number + "name");
				}
				if (updates.containsKey("question" + fr.question_number + "question_text")) {
					fr.question_text = updates.get("question" + fr.question_number + "question_text");
				}
				if (updates.containsKey("question" + fr.question_number + "answer")) {
					fr.answer = updates.get("question" + fr.question_number + "answer");
				}
			} else if (type.equals("question_fill_in_blank")) {
				FillInTheBlank fib = (FillInTheBlank) q;
				if (updates.containsKey("question" + fib.question_number + "name")) {
					fib.name = updates.get("question" + fib.question_number + "name");
				}
				if (updates.containsKey("question" + fib.question_number + "question_text_before")) {
					fib.question_text_before = updates.get("question" + fib.question_number + "question_text_before");
				}
				if (updates.containsKey("question" + fib.question_number + "answer")) {
					fib.answer = updates.get("question" + fib.question_number + "answer");
				}
				if (updates.containsKey("question" + fib.question_number + "question_text_after")) {
					fib.question_text_after = updates.get("question" + fib.question_number + "question_text_after");
				}
			} else if (type.equals("question_multiple_choice")) {
				MultipleChoice mc = (MultipleChoice) q;
				if (updates.containsKey("question" + mc.question_number + "name")) {
					mc.name = updates.get("question" + mc.question_number + "name");
				}
				if (updates.containsKey("question" + mc.question_number + "question_text")) {
					mc.question_text = updates.get("question" + mc.question_number + "question_text");
				}
				if (updates.containsKey("question" + mc.question_number + "answer")) {
					mc.answer = updates.get("question" + mc.question_number + "answer");
				}
				if (updates.containsKey("question" + mc.question_number + "choice_a")) {
					mc.choice_a = updates.get("question" + mc.question_number + "choice_a");
				}
				if (updates.containsKey("question" + mc.question_number + "choice_b")) {
					mc.choice_b = updates.get("question" + mc.question_number + "choice_b");
				}
				if (updates.containsKey("question" + mc.question_number + "choice_c")) {
					mc.choice_c = updates.get("question" + mc.question_number + "choice_c");
				}
				if (updates.containsKey("question" + mc.question_number + "choice_d")) {
					mc.choice_d = updates.get("question" + mc.question_number + "choice_d");
				}
				if (updates.containsKey("question" + mc.question_number + "choice_e")) {
					mc.choice_e = updates.get("question" + mc.question_number + "choice_e");
				}
				if (updates.containsKey("question" + mc.question_number + "choice_f")) {
					mc.choice_f = updates.get("question" + mc.question_number + "choice_f");
				}
				if (updates.containsKey("question" + mc.question_number + "choice_g")) {
					mc.choice_g = updates.get("question" + mc.question_number + "choice_g");
				}
				if (updates.containsKey("question" + mc.question_number + "choice_h")) {
					mc.choice_h = updates.get("question" + mc.question_number + "choice_h");
				}
				if (updates.containsKey("question" + mc.question_number + "choice_i")) {
					mc.choice_i = updates.get("question" + mc.question_number + "choice_i");
				}
				if (updates.containsKey("question" + mc.question_number + "choice_j")) {
					mc.choice_j = updates.get("question" + mc.question_number + "choice_j");
				}
			}
			if (!q.save()) return false;
		}
		return true;
	}
	
	/* ???
	public ArrayList<Quiz> parse(ResultSet rs) {
		ArrayList<Quiz> qs = new ArrayList<Quiz>();
		
		try {
			rs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while(rs.next()) {
				Quiz temp_quiz = new Quiz();
				temp_quiz.populate(rs);
				qs.add(temp_quiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return qs;
	} */
	
	@Override
	public boolean fetch() {
		this.error = null;
		// Populate quiz info
		if (this.quiz_id == -1) {
			this.error = "Quiz id was not specified";
			return false;
		}
				
		String quizQuery = "SELECT * FROM quiz WHERE quiz_id = '" + this.quiz_id + "'";
		ResultSet rs = connector.query(quizQuery);
		
		if(rs == null) {
			this.error = "Database connection failed";
			return false;
		}
		
		try {
			while(rs.next()) {
				this.quiz_name = rs.getString("quiz_name");
				this.quiz_description = rs.getString("quiz_description");
				this.category_name = rs.getString("category_name");
				this.date_created = rs.getString("date_created");
				this.times_taken = rs.getInt("times_taken");
				this.quiz_timer = rs.getInt("quiz_timer");
				if (this.creator_id == -1) {
					this.creator_id = rs.getInt("creator_id");
				}
			}
			//TEW: GETS RATING FOR A QUIZ!
			this.rating = getRating();
			System.out.println("Quiz: "+ quiz_name +" has rating: " + rating);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		
		return true;
	}
	
	public boolean fetchQuestions() {

		// Populate questions, find the questions TYPE, to know what questions to create
		String query = "SELECT * FROM quiz_question_number WHERE quiz_id = '" + this.quiz_id + "' ORDER BY question_number";
		ResultSet rs = connector.query(query);
		
		// Creating instance of questions
		this.questions = new ArrayList<Question>();
		
		try {
			while(rs.next()) {
				int question_type_id = rs.getInt("question_type_id");
				
				switch(question_type_id) {
					case 1: {
						FreeResponse temp_question = new FreeResponse();
						temp_question.fr_question_id = rs.getInt("fr_question_id");
						
						
						if (temp_question.fetch()) {
							this.questions.add(temp_question);
						} else {
							System.out.println("Error fetching question type 1");
							//error fetching question
						}
						
					} break;
					
					case 2: {
						FillInTheBlank temp_question = new FillInTheBlank();
						temp_question.fib_question_id = rs.getInt("fib_question_id");
						if (temp_question.fetch()) {
							this.questions.add(temp_question);
						} else {
							System.out.println("Error fetching question type 2");
							//error fetching question
						}
						
					} break;
					
					case 3: {
						MultipleChoice temp_question = new MultipleChoice();
						temp_question.mc_question_id = rs.getInt("mc_question_id");
						if (temp_question.fetch()) {
							this.questions.add(temp_question);
						} else {
							System.out.println("Error fetching question type 3");
							//error fetching question
						}
						
					} break;
					
					case 4: {
						PictureResponse temp_question = new PictureResponse();
						temp_question.pr_question_id = rs.getInt("pr_question_id");
						if (temp_question.fetch()) {
							this.questions.add(temp_question);
						} else {
							System.out.println("Error fetching question type 4");
							//error fetching question
						}
						
						
					}
				}
					
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean fetchTopScores() {
		
		String query = "SELECT * FROM history WHERE quiz_id = " + quiz_id + 
				" ORDER BY total_score DESC, quiz_time LIMIT 5"; 
		ResultSet rs = connector.query(query);
		
		topScores = new ArrayList<History>();
		
		try {
			while(rs.next()) {
				History hist = new History();
				hist.history_id = rs.getInt("history_id");
				if (hist.fetch()) {
					topScores.add(hist);
				} else {
					System.out.println("Error: History not found");
				}
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		query = "SELECT * FROM history WHERE quiz_id = " + quiz_id + 
				" AND timestamp >= NOW() + INTERVAL 1 DAY ORDER BY total_score DESC, quiz_time LIMIT 5";
		
		rs = connector.query(query);
		
		topScoresToday = new ArrayList<History>();
		
		try {
			while(rs.next()) {
				History hist = new History();
				hist.history_id = rs.getInt("history_id");
				if (hist.fetch()) {
					topScoresToday.add(hist);
				} else {
					System.out.println("Error: History not found");
				}
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean fetchUserScores(int user_id) {
		String query = "SELECT * FROM history WHERE quiz_id = " + quiz_id + " AND "
				+ "user_id =" + user_id + " ORDER BY timestamp DESC";
		
		ResultSet rs = connector.query(query);
		
		userScores  = new ArrayList<History>();
		
		try {
			while(rs.next()) {
				History hist = new History();
				hist.history_id = rs.getInt("history_id");
				if (hist.fetch()) {
					userScores.add(hist);
				} else {
					System.out.println("Error: History not found");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean fetchRecentTakers() {
		String query = "SELECT * FROM history WHERE quiz_id = "+ quiz_id +
				" ORDER BY timestamp DESC;";
		
		ResultSet rs = connector.query(query);
		
		recentTakers  = new ArrayList<History>();
		
		try {
			while(rs.next()) {
				History hist = new History();
				hist.history_id = rs.getInt("history_id");
				if (hist.fetch()) {
					recentTakers.add(hist);
				} else {
					System.out.println("Error: History not found");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public boolean update() {
		return true;
	}
	
	@Override
	public boolean destroy() {
		// Check and see if we have a quiz
		if(quiz_id == -1) {
			// Set error message
			return false;
		}
		String[] deleteQuiz = new String[7];
		
			// Delete from quiz_question_number
			deleteQuiz[0] = "DELETE FROM quiz_question_number WHERE quiz_id = " + quiz_id;
			
			// Delete from question_free_response
			deleteQuiz[1] = "DELETE FROM question_free_response WHERE quiz_id = " + quiz_id;
			
			// Delete from question_fill_in_blank
			deleteQuiz[2] = "DELETE FROM question_fill_in_blank WHERE quiz_id = " + quiz_id;
			
			// Delete from question_multiple_choice
			deleteQuiz[3] = "DELETE FROM question_multiple_choice WHERE quiz_id = " + quiz_id;
			
			// Delete from question_picture_response
			deleteQuiz[4] = "DELETE FROM question_picture_response WHERE quiz_id = " + quiz_id;
			
			// Delete from challenge
				// Get all the challenges and call destroy (this allows notifications to be deleted too)
				try{
					String quizChallenges = "SELECT challenge_id FROM challenge WHERE quiz_id = "+ quiz_id;
					ResultSet rs = connector.query(quizChallenges);
					rs.beforeFirst();
					Challenge challenge = new Challenge();
					while(rs.next()){
						int challenge_id = rs.getInt("challenge_id");
						challenge.challenge_id = challenge_id;
						challenge.destroy();
					}
				} catch(SQLException e){
					e.printStackTrace();
				}
			
			// Update history table to show the quiz was deleted
			deleteQuiz[5] = "DELETE FROM history WHERE quiz_id = " + quiz_id;
			
			// Delete from quiz
			deleteQuiz[6] = "DELETE FROM quiz WHERE quiz_id = " + quiz_id;
			
		// Delete from the database
		int result = connector.updateOrInsert(deleteQuiz);
		if(result < 0){
			System.err.println("There was an error in the DELETE call on a user_id");
			// Set error message on quiz
			return false;
		}
		return true;
	}
	
	/*
	 * TEW: This will calculate the rating for a quiz. Use it as a model to get the other metrics we need
	 */
	
	public double getAvgScore() {
		String query = "SELECT avg(total_score) FROM history WHERE quiz_id = " + quiz_id;
		ResultSet rs = connector.query(query);
		try {
			rs.first();
			return rs.getDouble("avg(total_score)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public double getAvgTime() {
		String query = "SELECT avg(quiz_time) FROM history WHERE quiz_id = " + quiz_id;
		ResultSet rs = connector.query(query);
		try {
			rs.first();
			return rs.getDouble("avg(quiz_time)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public double getRating() {
		String ratingQuery = "SELECT avg(rating) FROM history WHERE quiz_id = " + quiz_id + " AND rating >= 0";
		ResultSet rs = connector.query(ratingQuery);
		try{
			rs.first();
			return rs.getDouble("avg(rating)");
		} catch (SQLException e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public static ArrayList<Quiz> getAllQuizzes() {
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		DBConnector connector = new DBConnector();
		
		// Populate quizzes list
		String quiz_query = "SELECT * FROM quiz ORDER BY quiz_id";
		ResultSet rs = connector.query(quiz_query);
		try {
			while(rs.next()) {
				Quiz quiz = new Quiz();
				quiz.quiz_id = rs.getInt("quiz_id");
				quiz.quiz_name = rs.getString("quiz_name");
				quiz.date_created = rs.getString("date_created");
				quizzes.add(quiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return quizzes;
	}
	
}
