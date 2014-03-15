<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="servlets.*" %>
<%@ page import="app.*" %>
<%@ page import="models.*" %>
<%@ page import="java.util.ArrayList" %>

<%
	App app = (App)session.getAttribute("app");
	System.out.println(app.number_quizzes);
	User user = app.current_user;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<%@ include file="media/home_style.jspf" %>
	<title>Send Challenge</title>
</head>

<body>
<!--HEADER BAR-->
	<%@ include file="header.jspf" %>

	<% 	String recipient = request.getParameter("recipient"); 
		if(recipient == null) recipient = "";
	%>

	<form action="ChallengeController" method="post">
		Recipient: <input type="text" name="recipient" value="<%= recipient %>"/><br /> 
		Quiz: <select id = "dropdown">
				<%for(Quiz q : Quiz.getAllQuizzes()){ %>
					<option value = "<%=q.quiz_id%>"><%=q.quiz_name %></option>
				<%} %>
			  </select>  
		<input type="submit" value="Send" />
	</form>
	
	<% String messageStatus = (String)session.getAttribute("messageStatus"); %>
	<% if (messageStatus != null) { %>
		<%= messageStatus %>
		<% request.getSession().setAttribute("messageStatus", null); %>
	<% } %>

</body>

</html>

</html>