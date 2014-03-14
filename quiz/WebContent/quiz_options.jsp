<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    
<%
	String edit_feedback = (String) request.getAttribute("edit_feedback");
	if (edit_feedback == null) edit_feedback = "";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<!-- library -->
	<link rel="stylesheet" type="text/css" href="css/library/foundation.min.css">
	<link rel="stylesheet" type="text/css" href="css/library/normalize.css">
	<title>Customize Quiz</title>
</head>
<body>
	<h3>Customize your quiz experience</h3>
	<form action="QuizController" method="get" >
		<p><b> Pages:</b><br>
			One <input type="radio" name="pages" value="one"></input>
			Multiple <input type="radio" name="pages" value="multiple"></input>
		<p>
		
		<p><b>Order:</b><br>
			Ordered <input type="radio" name="order" value="ordered"></input>
			Random <input type="radio" name="order" value="random"></input>
		</p>
			
		<p><b>Correction:</b><br>
			Immediate <input type="radio" name="correction" value="immediate"></input>
			Later <input type="radio" name="correction" value="later"></input>
		</p>
		
		<input type="hidden" name="quiz_id" value="<%= request.getParameter("quiz_id") %>"></input>
			
		<button type="submit">Start Quiz</button>
	</form>
	
	<p><a href="QuizEditController?quiz_id=<%= request.getParameter("quiz_id") %>">Edit This Quiz</a></p>
	<p><%= edit_feedback%></p>

</body>
</html>