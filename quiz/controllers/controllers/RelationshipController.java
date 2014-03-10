package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RelationshipController: Received POST request");
		
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
