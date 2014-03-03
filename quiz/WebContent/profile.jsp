<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="models.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Profile</title>
</head>

<body>

	<% String username = request.getParameter("username"); %>
	<% if (username != null && !username.isEmpty()) {%>	

		<% 	User user = new User(); 
			user.user_name = username;  %>
	
		<!-- Fetch user based on user name -->
		<% 	if (user.fetch()) { %>
			
			<h1>Profile for <%= user.user_name %></h1>
			<h3>User ID: <%= user.user_id %></h3>
			<h3>Date Created: <%= user.date_created %></h3>
			<h3>Messages received: <%= user.message_received %></h3>
			
			<hr />
			
			<h2>Messages</h2>
			<ul>
				<% 	user.fetchMessages();
					for (Message msg : user.messages) { %>

						<li>From: <%= msg.from_user_name %> Title: <%= msg.title %></li>
							
				<% } %>
					
			</ul>
			
		<% } else { %>
			<h1>Profile for <%= username %> not found!</h1> <br/>
		<% } %>

	<% } %>
	

</body>
</html>