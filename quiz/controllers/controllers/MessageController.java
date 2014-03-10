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
		
		App app = (App)request.getSession().getAttribute("app");
		
		if (title == null) title = "No title";
		if (body == null) body = "Empty message";
		
		if (recipient == null){
			messageStatus = "You must specify a recipient!";
		} else {
			User recUser = new User();
			recUser.user_name = recipient;
			if (recUser.fetch()){
				Message msg = new Message();
				msg.title = title;
				msg.body = body;
				msg.to_user_id = recUser.user_id;
				msg.from_user_id = app.current_user.user_id;
				if(msg.save()){
					messageStatus = "Successfully sent message to " + recUser.user_name;
				} else {
					messageStatus = "Failed to send message!";
				}
				
			} else {
				messageStatus = "You must specify an existing recipient!";
			}
		}
		
		request.getSession().setAttribute("messageStatus", messageStatus);
		RequestDispatcher rd = request.getRequestDispatcher("send_message.jsp");
		rd.forward(request, response);
	}

}
