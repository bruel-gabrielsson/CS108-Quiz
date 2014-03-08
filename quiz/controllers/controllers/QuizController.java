package controllers;

import java.io.IOException;
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
		
		Map<String, String[]> params = request.getParameterMap();
		for (String key : params.keySet()) {
			System.out.println(params.get(key));
			for (String s : params.get(key)) {
				System.out.println(s);
			}
			
		}
	
	}

}
