<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="servlets.*" %>
<%@ page import="app.*" %>
<%@ page import="models.*" %>
<%@ page import="java.util.ArrayList" %>

<%
	App app = (App)session.getAttribute("app");
	User user = app.current_user;
	if (user == null || user.user_name.isEmpty()){
		// Redirect to home page if user isn't logged in
		response.sendRedirect("/home.jsp");
	}
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<%@ include file="media/home_style.jspf" %>
	<title>Insert title here</title>
</head>

<body>
<!--HEADER BAR-->
	<%@ include file="header.jspf" %>

	<% 	String recipient = request.getParameter("recipient"); 
		if(recipient == null) recipient = "";
	%>

	<form action="MessageController" method="post">
		Recipient: <input type="text" name="recipient" value="<%= recipient %>"/><br /> 
		Title: <input type="text" name="title" /><br />  
		Message: <input type="text" name="body" /><br />
		<input type="submit" value="Send" />
	</form>
	
	<% String messageStatus = (String)session.getAttribute("messageStatus"); %>
	<% if (messageStatus != null) { %>
		<%= messageStatus %>
		<% request.getSession().setAttribute("messageStatus", null); %>
	<% } %>

</body>
</html>