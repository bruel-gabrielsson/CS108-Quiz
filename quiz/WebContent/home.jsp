<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="servlets.*" %>
<%@ page import="app.*" %>
<%@ page import="models.*" %>
<%@ page import="java.util.ArrayList" %>

<%
 App app = (App)session.getAttribute("app");
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/home.css"/>
	<title>Welcome to Quizz!</title>
</head>
<body>


<div id="wrap">
	
<!--HEADER BAR-->
	<div id="header">
		<div id="header-title">QUIZZ</div>

		<!-- Grab username and loginStatus (if they exist) from the session  -->
		<%	String username = (String)session.getAttribute("username");
			String loginStatus = (String)session.getAttribute("loginStatus"); 
			App app = (App)session.getAttribute("app"); %>
		
		<%	if (username != null && !username.isEmpty()) { %>
			<!-- User is logged in  -->
			<div id="header-profile"> 
				Welcome <a href="profile.jsp?username=<%= username %>"><%= username %></a>! &#124; <a href="/quiz/LogoutServlet">LOGOUT</a>
			</div>
			
		<% } else { %>	
			<!-- User isn't logged in. Also display login form / login message if login failed-->
			<div id="header-form">
				<% if (loginStatus != null && !loginStatus.isEmpty()) { %>
					<p><%= loginStatus %>&nbsp;</p>
				<% } %>
			
				<form action="LoginServlet" method="post"> 
					username <input type="text" name="username" />&nbsp;
					password <input type="text" name="password" />
					<input type="submit" value="LOGIN" />
				</form>	
			</div>
		
		<% } %>
		
	</div>

<!--MAIN, LEFT SECTION-->
	<div id="main">
	
		<div class="largeHeader">Most Popular</div>
		<ul>
			<!--TODO: Auto-generate quiz items based on below template-->
			<%	for (int i=0; i<3; i++) {%>
				<li>
					<div class="quizWidget">
						<div class="imgDiv">
							<img src="crab.jpg" alt="crab.jpg" height="42" width="42"></img>
						</div>
						<div class="contentDiv">
							<a href="quiz.jsp">Trivia on crabs</a><br/>
							Created by <a href="profile.jsp">Jikyu Choi</a>
						</div>
						<div class="dateDiv">
							February 4th, 2014
						</div>
					</div>
				</li>	
			<% } %>
			
			
		</ul>
		
		<div class="largeHeader">Recently Created</div>
		<ul>
			<!--TODO: Auto-generate quiz items based on below template-->
			<%	for (int i=0; i<2; i++) {%>
				<li>
					<div class="quizWidget">
						<div class="imgDiv">
							<img src="crab.jpg" alt="crab.jpg" height="42" width="42"></img>
						</div>
						<div class="contentDiv">
							<a href="quiz.jsp">Trivia on lobsters</a><br/>
							Created by <a href="profile.jsp">Jikyu Choi</a>
						</div>
						<div class="dateDiv">
							February 4th, 2014
						</div>
					</div>
				</li>	
			<% } %>
		</ul>

	</div>

<!--RIGHT SECTION, ONLY DISPLAYED IF USER IS LOGGED IN-->
	<%	if (username != null && !username.isEmpty() && app != null) { %>
		<% 	User user = app.current_user; %>
		<div id="sidebar">
			<div id="announcements">
				<div id="announcements-header">LATEST ANNOUNCEMENTS</div>
				<div id="announcements-body">Welcome to Quizz! This home page should contain an announcement section, list of popular quizzes, etc.</div>
			</div>
		
			<!-- Arraylist of user's quizzes -->
			<div class="smallHeader">YOUR QUIZZES</div>
			<ul>
				<%	for (Quiz quiz : user.quizzes) { %>
					<li><a href="quiz.jsp?quiz_id=<%= quiz.quiz_id %>"><%= quiz.quiz_name %></a> <%= quiz.date_created %></li>	
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