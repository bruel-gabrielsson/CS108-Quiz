package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Notification;
import models.Relationship;
import models.User;
import app.App;

/**
 * Servlet implementation class RelationshipController
 */
@WebServlet("/RelationshipController")
public class RelationshipController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelationshipController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RelationshipController: Received GET request to update existing relationship");
		String relationship_id = request.getParameter("relationship_id");
		String notification_id = request.getParameter("notification_id");
		String action = request.getParameter("action");
		
		if (relationship_id != null && !relationship_id.isEmpty() && action != null && !action.isEmpty()) {
			Relationship rel = new Relationship();	
			rel.relationship_id = Integer.parseInt(relationship_id);
			
			if (rel.fetch()) {
				// User wishes to accept relationship
				if (action.equals("Accept")) {
					if (!rel.isAccepted()) System.out.println("Failed to accept relationship ID" + relationship_id);
					
					// Update the number of friends the sending user has
					int fromUserID = rel.user_id;
					User fromUser = new User();
					fromUser.user_id = fromUserID;
					fromUser.fetch();
					fromUser.am_number_friends++;
					fromUser.save();
					
					// Update the number of friends the accepting user has
					int toUserID = rel.friend_id;
					User toUser = new User();
					toUser.user_id = toUserID;
					toUser.fetch();
					toUser.am_number_friends++;
					toUser.save();
				}
				
				// User wishes to decline relationship
				if (action.equals("Decline")) {
					if (!rel.isRejected()) System.out.println("Failed to decline relationship ID" + relationship_id);
				}
			} else {
				System.out.println("Failed to fetch relationship ID: " + relationship_id);
			}
		}
		
		if (notification_id != null && !notification_id.isEmpty()) {
			Notification notify = new Notification();
			notify.notification_id = Integer.parseInt(notification_id);
			if (notify.fetch()) {
				notify.is_viewed = 1;
				if (!notify.save()) System.out.println("Failed to update notificaiton " + notify.notification_id);
			}
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("home.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RelationshipController: Received POST request to add new relationship");
		
		App app = (App)request.getSession().getAttribute("app");
		User fromUser = app.current_user;
		User toUser = new User();
		toUser.user_name = request.getParameter("recipient");
		
		String friendRequestStatus = "Failed to send friend request!";
		
		if (toUser.user_name != null && toUser.fetch()) {
			Relationship rel = new Relationship();
			rel.user_id = fromUser.user_id;
			rel.friend_id = toUser.user_id;
			if (rel.save()) {
				friendRequestStatus = "Friend request sent!";
			}
		}
		
		request.getSession().setAttribute("friendRequestStatus", friendRequestStatus);
		
		String url = "profile.jsp?username=" + toUser.user_name;
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

}
