package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Message;
import models.User;

/**
 * Servlet implementation class MessageController
 */
@WebServlet("/MessageController")
public class MessageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("send_message.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String recipient = request.getParameter("recipient");
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		
		String messageStatus = "";
		
		if (title == null) title = "No title";
		if (body == null) body = "Empty message";
		
		if (recipient == null){
			messageStatus = "You must specify a recipient!";
		} else {
			User recUser = new User();
			recUser.user_name = recipient;
			if (recUser.exists()){
				Message msg = new Message();
				msg.title = title;
				msg.body = body;
			} else {
				messageStatus = "You must specify an existing recipient!";
			}
			
		}
		
		request.getSession().setAttribute("messageStatus", messageStatus);
		RequestDispatcher rd = request.getRequestDispatcher("send_message.jsp");
		rd.forward(request, response);
	}

}
