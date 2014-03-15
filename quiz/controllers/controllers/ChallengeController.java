package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.App;
import models.Message;
import models.Notification;
import models.Relationship;
import models.User;
import models.Challenge;

/**
 * Servlet implementation class ChallengeController
 */
@WebServlet("/ChallengeController")
public class ChallengeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// The if statement should be if a user is accepting a challenge
		// The else will handle if a challenge is being sent
		String action = request.getParameter("action");
		if(action != null && action.equals("Read")){
			// Do shit if they accept reject
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("send_challenge.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ChallengeController: Received POST request to add new challenge");
		
		App app = (App)request.getSession().getAttribute("app");
		User fromUser = app.current_user;
		User toUser = new User();
		toUser.user_name = request.getParameter("recipient");
		// I"M NOT SURE HOW TO GET THE QUIZ ID
//		String s_quiz_id = request.getParameter("quiz_id");
//		System.out.println("THE QUIZ ID = "+ s_quiz_id);
//		Integer quiz_id = Integer.parseInt(s_quiz_id);
		int quiz_id =1;
		
		String ChallengeStatus = "Failed to send challenge!";
		
		if (toUser.user_name != null && toUser.fetch()) {
			Challenge chal = new Challenge();
			chal.from_user_id = fromUser.user_id;
			chal.to_user_id = toUser.user_id;
			chal.quiz_id = quiz_id;
			if (chal.save()) {
				ChallengeStatus = "Friend request sent!";
			}
		}
		
		request.getSession().setAttribute("ChallengeStatus", ChallengeStatus);
		
		String url = "profile.jsp?username=" + toUser.user_name;
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

}
