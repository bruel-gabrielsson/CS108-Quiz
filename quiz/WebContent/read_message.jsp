<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    
<%@ page import="servlets.*" %>
<%@ page import="app.*" %>
<%@ page import="models.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>

	<% Message msg = (Message)session.getAttribute("message"); %>
	<% if (msg != null) { %>
		<h3>TITLE: <%= msg.title %></h3>
		<h5><%= msg.body %></h5>
	<% } else { %>
		<h4> MESSAGE NOT FOUND!</h4>
	<% } %>

</body>
</html>