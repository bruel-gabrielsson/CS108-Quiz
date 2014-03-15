<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="servlets.*" %>
<%@ page import="app.*" %>
<%@ page import="models.*" %>
<%@ page import="java.util.ArrayList" %>

<%
	ArrayList<User> results = (ArrayList<User>) request.getAttribute("results");
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>Search Results</title>
</head>

<body>
<!--HEADER BAR-->
	<%@ include file="header.jspf" %>

	<div id="content-users">
		<h3>Search Results:</h3>
		<ol>
			<% for (User u : results) { %>
				<li>  
					<a href="profile.jsp?username=<%= u.user_name %>"><%= u.user_name %></a>
					<%= u.date_created %> 
					</li>
			<% } %>
		</ol>
	</div>
</body>
</html>