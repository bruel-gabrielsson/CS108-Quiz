<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="servlets.*" %>
<%@ page import="app.*" %>
<%@ page import="models.*" %>
<%@ page import="java.util.ArrayList" %>

<%
	App app = (App)session.getAttribute("app");
	User user = app.current_user;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="media/home.css">
	
	<!-- library -->
	<link rel="stylesheet" type="text/css" href="css/library/foundation.min.css">
	<link rel="stylesheet" type="text/css" href="css/library/normalize.css">
	
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
				
			<button type="button" id="new-quiz-button" onclick="location.href='quiz_create.jsp'">+ Create a new quiz!</button>
			
			<!-- Global announcement -->
			<div id="announcements">
				<% 	Announcement current_ann = new Announcement(); 
					current_ann.fetch();
				%>
				<div id="announcements-header">LATEST ANNOUNCEMENTS</div>
				<div id="announcements-body"><%= current_ann.announcement %></div>
			</div>

			<!-- User's notifications -->
			<div class="smallHeader">YOUR NOTIFICATIONS</div>
				<% user.fetchNotifications(); %>
			 	<% if (user.notifications.size() == 0) { %>
			 		You have no notifications at this time!
			 	<% } else { %>
					<ul>
						<% for (Notification n : user.notifications) { %>
							
							<!-- Message -->
							<% if (n.notification_type_id == 1) {  %>
								<li> 
									<%= n.notification_text %>
									<form action="MessageController" method="get">
										<input type="hidden" name="message_id" value="<%= n.message_id %>" />	
										<input type="hidden" name="notification_id" value="<%= n.notification_id %>" />
									    <input type="submit" name="action" value="Read" />
									</form>
								</li>
							
							<!-- challenge -->
							<% } else if (n.notification_type_id == 2) { %>
								<li> 
									<%= n.notification_text %> 
										<form action="ChallengeController" method="get">
										<input type="hidden" name="challenge_id" value="<%= n.challenge_id %>" />	
										<input type="hidden" name="notification_id" value="<%= n.notification_id %>" />
									    <input type="submit" name="action" value="Accept" />
									    <input type="submit" name="action" value="Decline" />
									</form>
								</li>				
							
							<!--  Friend request -->
							<% } else if (n.notification_type_id == 3) { %>
								<li> 
									<%= n.notification_text %>
									<form action="RelationshipController" method="get">
										<input type="hidden" name="relationship_id" value="<%= n.relationship_id %>" />	
										<input type="hidden" name="notification_id" value="<%= n.notification_id %>" />
									    <input type="submit" name="action" value="Accept" />
									    <input type="submit" name="action" value="Decline" />
									</form>
								</li>
							<% } %>	
											
						<% } %>
					</ul>
				<% } %>
				
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
			<% user.fetchFriends(); %>
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