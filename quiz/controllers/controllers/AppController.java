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
 * Servlet implementation class AppController
 */
// @WebServlet("/AppController")
// HOME:
@WebServlet("/")
public class AppController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// CREATING THE APP FOR THE SESSION
		if (request.getSession().getAttribute("app") == null) {
			System.out.println("APP CREATED");
	    	App app = new App();
	    	app.initialize();
	    	
	    	request.getSession().setAttribute("app", app);
		}
			
		RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
