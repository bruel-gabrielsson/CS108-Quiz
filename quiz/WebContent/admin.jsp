<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="servlets.*" %>
<%@ page import="app.*" %>
<%@ page import="models.*" %>
<%@ page import="java.util.ArrayList" %>

<%
	App app = (App)session.getAttribute("app");
	User user = app.current_user;
	if (user == null || !user.isAdmin()){
		// Redirect to home page if user isn't an admin
		response.sendRedirect("/home.jsp");
	}
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title> Admin panel for <%= app.current_user.user_name %></title>
</head>

<body>
<!--HEADER BAR-->
	<%@ include file="header.jspf" %>

	<div id="content-users">
		<h3>Users</h3>
		<% ArrayList<User> users = User.getAllUsers(); %>
		<ol>
			<% for (User u : users) { %>
				<li>  
					<a href="profile.jsp?username=<%= u.user_name %>"><%= u.user_name %></a>
					<%= u.date_created %> 
					<a href="DeleteServlet?username=<%= u.user_name %>">DELETE</a>
					</li>
			<% } %>
		</ol>
	</div>
	
	<div id="content-quizzes">
		<h3>Quizzes</h3>
		<% ArrayList<Quiz> quizzes = Quiz.getAllQuizzes(); %>
		<ol>
			<% for (Quiz q : quizzes) { %>
				<li> <%= q.quiz_name %> <%= q.date_created %> <a href="QuizEditController?quiz_id=<%= q.quiz_id %>">EDIT</a></li>
			<% } %>
		</ol>
	</div>
	
	<div id="content-statistics">
		
	</div>
	
	<div id="content-announcements">
		<h3>Announcements</h3>
		
		<h5>Current Announcement:</h5>
		<% 	Announcement current_ann = new Announcement(); 
			current_ann.fetch();
		%>
		<%= current_ann.announcement %>
		
		<h5>Set New Announcement:</h5>
		<form action="NewAnnouncementServlet" method="post">
			Title: <input type="text" name="announcement-name" /> <br /> 
			Announcement: <input type="text" name="announcement" /> <br />
			<input type="submit" value="SUBMIT" />
		</form>
	</div>
	
</body>
</html>