package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import questions.FillInTheBlank;
import questions.FreeResponse;
import questions.MultipleChoice;
import models.Question;
import models.Quiz;
import app.App;

/**
 * Servlet implementation class CreateQuizServlet
 */
@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		App app = (App) request.getSession().getAttribute("app");
		
		if(app.new_quiz == null) {
			app.new_quiz = new Quiz();
			app.new_quiz.questions = new ArrayList<Question>();
		} else {
			
		}
		
		if (request.getParameterMap().containsKey("submit") && request.getParameter("submit").equals("true")) {
			// SUBMITTING
			
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			String cat = request.getParameter("category");
			
			System.out.println(app.new_quiz.questions.size());
			app.new_quiz.category_name = cat;
			app.new_quiz.quiz_name = name;
			app.new_quiz.quiz_description = description;
			app.new_quiz.creator_id = app.current_user.user_id;
			
			int question_number = 1;
			if(app.new_quiz.save()) {
				for (Question q : app.new_quiz.questions) {
					q.question_number = question_number;
					q.quiz_id = app.new_quiz.quiz_id;
					System.out.println("quizid: " + q.quiz_id + "" + app.new_quiz.quiz_id);
					q.save();
					question_number += 1;
				}
			} else {
				
			}
			
			app.new_quiz = null;
			
			
		} else {
		
			System.out.println("new quiz");
			Map<String, String[]> params = request.getParameterMap();
			for (String key : params.keySet()) {
				for (String s : params.get(key)) {
					System.out.println(key + " Value:" + s);
				}
			}
			
			String type = request.getParameter("type");
			if (type.equals(FillInTheBlank.this_type)) {
				FillInTheBlank q = new FillInTheBlank();
				
				q.answer = request.getParameter("answer");
				q.name = request.getParameter("name");
				q.question_text_after = request.getParameter("question_text_after");
				q.question_text_before = request.getParameter("question_text_before");
				
				System.out.println("CREATING FIB" +  q.answer  + q.name + q.question_text_after);
				
				app.new_quiz.questions.add(q);
			} else if (type.equals(FreeResponse.this_type)) {
				FreeResponse q = new FreeResponse();
				q.answer = request.getParameter("answer");
				q.name = request.getParameter("name");
				q.question_text = request.getParameter("question_text");
				/*TEW: The question text is showing up null for some reason*/
				
				System.out.println("CREATING FR" +  q.answer  + q.name + q.question_text);
				
				app.new_quiz.questions.add(q);
			} else { // Multiplechoice
				MultipleChoice q = new MultipleChoice();
				q.answer = request.getParameter("answer");
				q.name = request.getParameter("name");
				q.question_text = request.getParameter("question_text");
				q.choice_a = request.getParameter("choice_a");
				q.choice_b = request.getParameter("choice_b");
				q.choice_c = request.getParameter("choice_c");
				q.choice_d = request.getParameter("choice_d");
				q.choice_e = request.getParameter("choice_e");
				q.answer = request.getParameter("answer");
				
				System.out.println("CREATING MC" +  q.answer  + q.name + q.question_text);
				
				app.new_quiz.questions.add(q);
			}
		
		
		}
		
	}

}
