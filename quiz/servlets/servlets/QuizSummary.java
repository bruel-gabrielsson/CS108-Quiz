package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Quiz;
import app.App;

/**
 * Servlet implementation class QuizSummary
 */
@WebServlet("/QuizSummary")
public class QuizSummary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizSummary() {
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
		
		if (quiz_id != -1) {
			Quiz q = new Quiz();
			q.quiz_id = quiz_id;
			if(q.fetch()) {
				request.setAttribute("quiz", q);
				
				RequestDispatcher rd = request.getRequestDispatcher("quizViews/quiz_summary.jsp");
				rd.forward(request, response);
			}
			
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
