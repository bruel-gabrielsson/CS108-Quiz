package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Question;
import models.Quiz;
import app.App;

/**
 * Servlet implementation class QuizController
 */
@WebServlet("/QuizController")
public class QuizController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private long start = 0;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    // PUT, creating new quiz
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("RECEIVED");
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Take the quiz, must give id
		
		int quiz_id = -1;
		try {
			quiz_id = Integer.parseInt(request.getParameter("quiz_id"));
		} catch(NumberFormatException e) {
			// not valid id
		}
		
		App app = (App) request.getSession().getAttribute("app");
		if (app != null && quiz_id != -1) { // Just in case
			
			System.out.println(request.getParameter("correction"));
			
			String practice = request.getParameter("practice");
			
			String correction = request.getParameter("correction");
			String order = request.getParameter("order");
			String pages = request.getParameter("pages");
			
			if (practice != null && order != null && pages != null && correction != null) {
			
				this.start = System.currentTimeMillis();
				
				Quiz quiz = new Quiz();
				quiz.quiz_id = quiz_id;
				if (quiz.fetch() && quiz.fetchQuestions()) {
					request.setAttribute("quiz", quiz);
					System.out.println(quiz.quiz_name.toString());
					
					if (request.getParameter("order").equals("ordered")) {
						app.current_quiz = quiz;
						app.current_questions = quiz.questions;
					} else { // random
						app.current_quiz = quiz;
						ArrayList<Question> random = new ArrayList<Question>();
						// copy and randomize
						for (Question q: quiz.questions) {
							random.add(q.clone());
						}
						Collections.shuffle(random);
						app.current_questions = random;
					}
					
					if (request.getParameter("pages").equals("one")) {
						RequestDispatcher rd = request.getRequestDispatcher("quizViews/quiz.jsp");
						rd.forward(request, response);
					} else { //multiple pages
						RequestDispatcher rd = request.getRequestDispatcher("quizViews/quiz_multiple_pages.jsp");
						rd.forward(request, response);
					}
					
					
				} else {
					System.out.println("ERROR FETCHING QUIZ");
					// error page
				}
			} else {
				
				RequestDispatcher rd = request.getRequestDispatcher("quiz_options.jsp");
				rd.forward(request, response);
				
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		App app = (App) request.getSession().getAttribute("app");
		
		HashMap<String, String> answers = new HashMap<String, String>();
		
		Map<String, String[]> params = request.getParameterMap();
		for (String key : params.keySet()) {
			for (String s : params.get(key)) {
				
				if (key.contains("question")) {
					System.out.println(key + " Value:" + s);
					answers.put(key, s);
				}
			}
		}
		
		// Corrects the quiz and returns receives an hashmap of errors or success message
		HashMap<String, String> feedback = app.current_quiz.correctMap(answers);
		request.setAttribute("feedback", feedback);
	
		app.current_quiz.times_taken += 1;
		long time = System.currentTimeMillis() - this.start;
		System.out.println("TIME: " + time);
		
		this.start = 0;
	
		if(app.current_quiz.save()) {
		
		}
	
		
		RequestDispatcher rd = request.getRequestDispatcher("quiz_feedback.jsp");
		rd.forward(request, response);
	}

}
