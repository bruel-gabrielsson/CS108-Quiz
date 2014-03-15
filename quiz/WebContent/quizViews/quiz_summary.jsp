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

	<!-- library -->
	<link rel="stylesheet" type="text/css" href="css/library/foundation.min.css">
	<link rel="stylesheet" type="text/css" href="css/library/normalize.css">
</head>
<body>

<!--HEADER BAR-->

	<a href="home.jsp" style="font-size:24px">HOME</a>

	<p>Quiz name: <%= quiz.quiz_name %></p>
	<p>Descritption: <%= quiz.quiz_description %></p>
	<p> Category: <%= quiz.category_name %></p>
	<p> Creator: <%= quiz.creator().user_name %></p>
	<p> </p>
	<p>Rating: <%= quiz.getRating() %></p>
	<p>Avg Score: <%= quiz.getAvgScore() %></p>
	<p>Avg Time: <%= quiz.getAvgTime()/1000 %>s</p>
	
	<a href="QuizController?quiz_id=<%= quiz.quiz_id %>">Take the Quiz</a>

</body>
</html>