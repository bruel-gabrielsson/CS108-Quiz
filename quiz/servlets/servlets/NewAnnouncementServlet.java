package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Announcement;

/**
 * Servlet implementation class NewAnnouncementServlet
 */
@WebServlet("/NewAnnouncementServlet")
public class NewAnnouncementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewAnnouncementServlet() {
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

		String announcement_name = request.getParameter("announcement-name");
		String announcement = request.getParameter("announcement");
		
		if(!announcement_name.isEmpty() && !announcement.isEmpty()){
			Announcement new_ann = new Announcement();
			new_ann.announcement_name = announcement_name;
			new_ann.announcement = announcement;
			if (new_ann.save()) {
				System.out.println("Successfully created a new announcement!");
			} else {
				System.out.println("Failed to create a new announcement!");
			}
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("admin.jsp");
		dispatch.forward(request, response);	
	}

}
