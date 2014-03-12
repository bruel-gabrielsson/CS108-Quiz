<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="models.*" %>
<%@ page import="questions.*" %>
<%@ page import="app.*" %>
<%@ page import="java.util.HashMap" %>
    
<%
	HashMap<String, String> feedback = (HashMap<String, String>) request.getAttribute("feedback");
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Feedback</title>
</head>
<body>
	<ul>
	<% for (String key : feedback.keySet()) { %>
		<% if (!key.equals("score")) { %>
		<li>		
			<%= feedback.get(key) %>
		</li>
		<% } %>
	<% } %>
	</ul>
	<h3>Total Score: <%= feedback.get("score") %></h3>
</body>
</html>