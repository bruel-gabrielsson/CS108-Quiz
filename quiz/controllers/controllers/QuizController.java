package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Quiz;
import app.App;

/**
 * Servlet implementation class QuizController
 */
@WebServlet("/QuizController")
public class QuizController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizController() {
        super();
        // TODO Auto-generated constructor stub
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
			
			Quiz quiz = new Quiz();
			quiz.quiz_id = quiz_id;
			if (quiz.fetch()) {
				request.setAttribute("quiz", quiz);
				System.out.println(quiz.quiz_name.toString());
				
				app.current_quiz = quiz;
				RequestDispatcher rd = request.getRequestDispatcher("quiz.jsp");
				rd.forward(request, response);
			} else {
				System.out.println("ERROR FETCHING QUIZ");
				// error page
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
	
		RequestDispatcher rd = request.getRequestDispatcher("quiz_feedback.jsp");
		rd.forward(request, response);
	}

}
