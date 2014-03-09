package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.App;
import models.User;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RegistrationServlet: Obtained registration POST request");

		// Retrieve username & password from the request
		String user_name = request.getParameter("username");
		String password = request.getParameter("password");
		String confirm_password = request.getParameter("confirm_password");
		String admin = request.getParameter("admin");
		
		System.out.println("admin status of this user is :" + admin);
		
		String registrationStatus = "";
		
		if (user_name.isEmpty() || password.isEmpty() || confirm_password.isEmpty() || !password.equals(confirm_password)){
			// Invalid form data
			registrationStatus = "Invalid form data";
		} else {
		
			User newUser = new User();
			newUser.user_name = user_name;
			
			if(newUser.exists()){
				// User exists
				registrationStatus = "This user already exists";
			} else {
				// Commit new user object
				newUser.salt = newUser.generateSalt();
				newUser.password = newUser.hashPassword(password, newUser.salt);
				newUser.is_admin = admin.equals("on") ? 1 : 0;
				if(!newUser.save()){
					registrationStatus = "Failed to create new user in database!";
				}
				
				// User creation successful
				App app = (App)request.getSession().getAttribute("app");
				if(app == null){
					System.out.println("RegistrationServlet: App not found!");
					return;
				}
				
				// Login
				if (newUser.signIn(password)) {
					app.current_user = newUser;
					request.getSession().setAttribute("username", user_name);
					request.getSession().setAttribute("app", app);
				} else {
					registrationStatus = "User creation successful, but failed to login!";
				}
			}
		}
		
		// Redirection based on registration success
		if(registrationStatus.length() > 0) {
			// Unsuccessful registration
			request.getSession().setAttribute("registrationStatus", registrationStatus);
			RequestDispatcher dispatch = request.getRequestDispatcher("registration.jsp");
			dispatch.forward(request, response);
		} else {
			// Redirect to home
			RequestDispatcher dispatch = request.getRequestDispatcher("home.jsp");
			dispatch.forward(request, response);	
		}
	}

}
