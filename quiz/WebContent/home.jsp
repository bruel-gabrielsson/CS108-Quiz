<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="servlets.*" %>
<%@ page import="app.*" %>
<%@ page import="models.*" %>
<%@ page import="java.util.ArrayList" %>

<%
	App app = (App) session.getAttribute("app");
	User user = app.current_user;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<link rel="stylesheet" type="text/css" href="./media/home.css"/>
	<title>Welcome to Quizz!</title>
</head>
<body>


<div id="wrap">
	
<!--HEADER BAR-->
	<%@ include file="header.jspf" %>

<!--MAIN, LEFT SECTION-->
	<div id="main">
	
		<div class="largeHeader">Most Popular</div>
		<ul>
			<!--TODO: Auto-generate quiz items based on below template-->
			<%	for (Quiz quiz : app.popular_quizzes) { %>
				<li>
					<div class="quizWidget">
						<div class="imgDiv">
							<img src="crab.jpg" alt="crab.jpg" height="42" width="42"></img>
						</div>
						<div class="contentDiv">
							<a href="QuizController?quiz_id=<%= quiz.quiz_id %>"><%= quiz.quiz_name %></a><br/>
							<% if (quiz.creator() != null) { %>
								Created by <a href="profile.jsp?username=<%= quiz.creator().user_name %>"><%= quiz.creator().user_name %></a>	
							<% } else { %>
								Created by Anonymous <%= quiz.creator_id %>
							<% } %>
						</div>
						<div class="dateDiv">
							<%= quiz.date_created %>
						</div>
					</div>
				</li>	
			<% } %>
			
			
		</ul>
		
		<div class="largeHeader">Recently Created</div>
		<ul>
			<!--TODO: Auto-generate quiz items based on below template-->
			<%	for (Quiz quiz : app.recent_quizzes) { %>
				<li>
					<div class="quizWidget">
						<div class="imgDiv">
							<img src="crab.jpg" alt="crab.jpg" height="42" width="42"></img>
						</div>
						<div class="contentDiv">
							<a href="QuizController?quiz_id=<%= quiz.quiz_id %>"><%= quiz.quiz_name %></a><br/>
							<% if (quiz.creator() != null) { %>
								Created by <a href="profile.jsp?username=<%= quiz.creator().user_name %>"><%= quiz.creator().user_name %></a>	
							<% } else { %>
								Created by Anonymous <%= quiz.creator_id %>
							<% } %>
						</div>
						<div class="dateDiv">
							<%= quiz.date_created %>
						</div>
					</div>
				</li>	
			<% } %>
		</ul>

	</div>

<!--RIGHT SECTION, ONLY DISPLAYED IF USER IS LOGGED IN-->
	<%	if (user != null && user.user_name != null) { %>
		<div id="sidebar">
			<div id="announcements">
				<div id="announcements-header">LATEST ANNOUNCEMENTS</div>
				<div id="announcements-body">Welcome to Quizz! This home page should contain an announcement section, list of popular quizzes, etc.</div>
			</div>
		
			<!-- Arraylist of user's quizzes -->
			<div class="smallHeader">YOUR QUIZZES</div>
			<ul>
				<%	for (Quiz quiz : user.quizzes) { %>
					<li><a href="QuizController?quiz_id=<%= quiz.quiz_id %>"><%= quiz.quiz_name %></a> <%= quiz.date_created %></li>	
				<% } %>
			</ul>
			
			<!-- Arraylist of user's friends -->
			<!--  TODO For now it's simply friends list, but needs to be history of friend's activities -->
			<div class="smallHeader">YOUR FRIENDS</div>
			User has <%= user.friends.size() %> friends
			<ul>
				<%	for (String friend : user.friends) { %>
					<li><a href="profile.jsp?username=<%= friend %>"><%= friend %></a></li>	
				<% } %>
			</ul>
			
			<!-- Arraylist of user's achievements -->
			<div class="smallHeader">YOUR ACHIEVEMENTS</div>
			
		</div>
	<% } %>
</div>


</body>
</html>