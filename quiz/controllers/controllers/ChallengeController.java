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
import models.Challenge;
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
		System.out.println("RelationshipController: Received GET request to update existing relationship");
		String challenge_id = request.getParameter("challenge_id");
		String notification_id = request.getParameter("notification_id");
		
		String action = request.getParameter("action");
		// The if statement should be if a user is accepting/declining a challenge
		// The else will handle if a challenge is being sent
		if (challenge_id != null && !challenge_id.isEmpty() && action != null && !action.isEmpty()) {
			Challenge chal = new Challenge();	
			chal.challenge_id = Integer.parseInt(challenge_id);
			
			if (chal.fetch()) {
				// User wishes to accept relationship
				if (action.equals("Accept")) {
//					if (!.isAccepted()) System.out.println("Failed to accept relationship ID" + relationship_id);
//					
//					// Update the number of friends the sending user has
//					int fromUserID = rel.user_id;
//					User fromUser = new User();
//					fromUser.user_id = fromUserID;
//					fromUser.fetch();
//					fromUser.am_number_friends++;
//					fromUser.save();
//					
//					// Update the number of friends the accepting user has
//					int toUserID = rel.friend_id;
//					User toUser = new User();
//					toUser.user_id = toUserID;
//					toUser.fetch();
//					toUser.am_number_friends++;
//					toUser.save();
				}
				
				// User wishes to decline relationship
				if (action.equals("Decline")) {
//					if (!rel.isRejected()) System.out.println("Failed to decline relationship ID" + relationship_id);
				}
			} else {
//				System.out.println("Failed to fetch relationship ID: " + relationship_id);
			}
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
		
		int challenge_id = -1;
		if (toUser.user_name != null && toUser.fetch()) {
			Challenge chal = new Challenge();
			chal.from_user_name = fromUser.user_name;
			chal.from_user_id = fromUser.user_id;
			chal.to_user_id = toUser.user_id;
			chal.quiz_id = quiz_id;
			if (chal.save()) {
				ChallengeStatus = "Friend request sent!";
				challenge_id = chal.challenge_id;
			}
		}
		
		request.getSession().setAttribute("ChallengeStatus", ChallengeStatus);
		request.getSession().setAttribute("challenge_id", challenge_id);
		
		String url = "QuizController?quiz_id=" + quiz_id;
		url = "profile.jsp?username=" + toUser.user_name;
		System.out.println("URL: "+url);
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

}
