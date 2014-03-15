<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
 <%@ page import="models.*" %>
<%@ page import="questions.*" %>
<%@ page import="app.*" %>
    
<%
	Quiz quiz = (Quiz) request.getAttribute("quiz");
%>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Quiz Summary</title>
</head>
<body>

	<p>Quiz name: <%= quiz.quiz_name %></p>
	<p>DescritptionL <%= quiz.quiz_description %></p>
	<p> Category: <%= quiz.category_name %></p>
	<p> Creator: <%= quiz.creator().user_name %></p>
	<p> </p>

</body>
</html>