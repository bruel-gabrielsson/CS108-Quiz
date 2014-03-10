<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="models.*" %>
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
	<%@ include file="media/home_style.jspf" %>
	<title>Profile</title>
</head>

<body>

<!--HEADER BAR-->
	<%@ include file="header.jspf" %>

	<% String profile_username = request.getParameter("username"); %>
	<% if (profile_username != null && !profile_username.isEmpty()) {%>	

		<!--  TODO: DON"T FUCKING DO THIS!!! Put profile_user as a session object that gets passed in -->
		<% 	User profile_user = new User(); 
			profile_user.user_name = profile_username;  %>
	
		<!-- Fetch user based on user name -->
		<% 	if (profile_user.fetch()) { %>
			
			<h1>Profile for <%= profile_user.user_name %></h1>
			<h3>User ID: <%= profile_user.user_id %></h3>
			<h3>Date Created: <%= profile_user.date_created %></h3>
			<h3>Messages received: <%= profile_user.message_received %></h3>
			
			<% if (!user.user_name.equals(profile_username)) { %>
				
				<!-- Check if user is friends with profile_user -->
				<% if (!user.isFriendWith(profile_user.user_id)) {  %>
					<% String friendRequestStatus = (String)request.getSession().getAttribute("friendRequestStatus"); %>
					<% if (friendRequestStatus != null) { %>
						<%= friendRequestStatus %>
						<% request.getSession().setAttribute("friendRequestStatus", null); %>
					<% } else { %>
						<form action="RelationshipController" method="post">
							<input type="hidden" name="recipient" value="<%= profile_username %>"/> 
							<input type="submit" value="Add Friend" />
						</form>
					<% } %>
				<% } %>
				
				
				<a href="MessageController?recipient=<%= profile_username %>">Send a message</a>
				
			<% } %>
			

			<hr />
			
			<h2>Messages</h2>
			<ul>
				<%
					for (Message msg : profile_user.messages) { %>
						<li>
							Date: <%= msg.time_sent %> 
							From: <a href="profile.jsp?username=<%=msg.from_user_name%>"><%= msg.from_user_name %></a>
							Title: <%= msg.title %>
							
							<!-- This is really fucking hacky way of showing messages, figure out a better, faster way -->
							<% String focusedmessage = request.getParameter("focusedmessage"); %>
							<% if (focusedmessage != null && !focusedmessage.isEmpty() && focusedmessage.equals(Integer.toString(msg.message_id))) {%>
								<a href="profile.jsp?username=<%=profile_username%>">HIDE</a>
								<div><%= msg.body %></div>
							<% } else { %>
								<a href="profile.jsp?username=<%=profile_username%>&focusedmessage=<%=msg.message_id%>">SHOW</a>
							<% } %>
						
						</li>
				<% } %>
					
			</ul>
			
		<% } else { %>
			<h1>Profile for <%= profile_username %> not found!</h1> <br/>
		<% } %>

	<% } %>
	

</body>
</html>