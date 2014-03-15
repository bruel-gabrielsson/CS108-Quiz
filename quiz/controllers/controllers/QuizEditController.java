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
 * Servlet implementation class QuizEditController
 */
@WebServlet("/QuizEditController")
public class QuizEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizEditController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			if (quiz.fetch() && quiz.fetchQuestions()) {
				if (app.current_user != null && app.current_user.user_id == quiz.creator_id) {
					
					request.setAttribute("quiz", quiz);
					app.current_quiz = quiz;
					app.current_questions = quiz.questions;
					RequestDispatcher rd = request.getRequestDispatcher("quiz_edit.jsp");
					rd.forward(request, response);
					
				} else {
					String edit_feedback = "You don't have permission to edit that quiz";
					System.out.println(edit_feedback);
					request.setAttribute("edit_feedback", edit_feedback);
					RequestDispatcher rd = request.getRequestDispatcher("quiz_options.jsp");
					rd.forward(request, response);
				}
				
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
		
		HashMap<String, String> quiz_updates = new HashMap<String, String>();
		HashMap<String, String> question_updates = new HashMap<String, String>();
		
		Map<String, String[]> params = request.getParameterMap();
		for (String key : params.keySet()) {
			for (String s : params.get(key)) {
				if (key.contains("quiz")) {
					System.out.println(key + "Value:" + s);
					quiz_updates.put(key, s);
				}
				if (key.contains("question")) {
					System.out.println(key + " Value:" + s);
					question_updates.put(key, s);
				}
			}
		}
		
		String edit_feedback;
		if (!app.current_quiz.editQuiz(quiz_updates)) {
			edit_feedback = "That quiz name is already taken";
		} else {
			if (app.current_quiz.editQuizQuestions(question_updates)) {
				edit_feedback = "Quiz edit succeeded";
			} else {
				edit_feedback = "Quiz edit failed";
			}
		}
		request.setAttribute("edit_feedback", edit_feedback);
		RequestDispatcher rd = request.getRequestDispatcher("quiz_options.jsp");
		rd.forward(request, response);
	}

}
