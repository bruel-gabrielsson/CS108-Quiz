package controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;
import app.App;

/**
 * Servlet implementation class FindFriendsController
 */
@WebServlet("/FindFriendsController")
public class FindFriendsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindFriendsController() {
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
		System.out.println("POST SEARCH USER");
		
		App app = (App) request.getSession().getAttribute("app");
		String user_name = request.getParameter("user_name");
		
		String query = "SELECT * FROM user WHERE user_name LIKE '%"+ user_name +"%'";
		ResultSet rs = app.connector.query(query);
		ArrayList<User> results = new ArrayList<User>();
		
		try {
			while (rs.next()) {
				User user = new User();
				user.user_id = rs.getInt("user_id");
				if (user.fetch()) results.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("results", results);
		RequestDispatcher rd = request.getRequestDispatcher("search_results.jsp");
		rd.forward(request, response);
	}

}
