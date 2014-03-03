package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.App;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Recieved GET request");
		
		// Retrieve username & password from the request
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		App app = (App)this.getServletContext().getAttribute("app");
		if(app == null){
			System.out.println("App not found!");
			return;
		}
		
		if(!username.isEmpty() && !password.isEmpty()){
			
			// Check for auth here
			if (app.signIn(username, password)){
				// Login successful,Store username as a session variable
				request.getSession().setAttribute("username", username);
				request.getSession().setAttribute("loginStatus", "Success");
			} else {
				request.getSession().setAttribute("loginStatus", "Failed to login");
			}
		} else {
			request.getSession().setAttribute("loginStatus", "Invalid username/password");
		}
		
		
		RequestDispatcher dispatch = request.getRequestDispatcher("home.jsp");
		dispatch.forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated constructor stub
	}

}
