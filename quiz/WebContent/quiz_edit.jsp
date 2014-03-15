<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.*" %>
<%@ page import="questions.*" %>
<%@ page import="app.*" %>
    
<%
	Quiz quiz = (Quiz) request.getAttribute("quiz");
	System.out.println("ENTERED QUIZ_EDIT");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" /> 
	
	<!-- JAVASCRIPT -->
	<script type="text/javascript" src="javascript/quiz.js" ></script>
	
	<!-- library -->
	<link rel="stylesheet" type="text/css" href="css/library/foundation.min.css">
	<link rel="stylesheet" type="text/css" href="css/library/normalize.css">
	<title>Quiz</title>
</head>
<body>

<div id="wrap">
	
<!--HEADER BAR-->
	<div id="header">
		<div id="header-title">QUIZZ</div>

		<!-- Grab username and loginStatus (if they exist) from the session  -->
		<%	String username = (String)session.getAttribute("username");
			String loginStatus = (String)session.getAttribute("loginStatus"); 
			App app = (App)session.getAttribute("app"); %>
		
		<%	if (username != null && !username.isEmpty()) { %>
			<!-- User is logged in  -->
			<div id="header-profile"> 
				Welcome <a href="profile.jsp?username=<%= username %>"><%= username %></a>! &#124; <a href="/quiz/LogoutServlet">LOGOUT</a>
			</div>
			
		<% } else { %>	
			<!-- User isn't logged in. Also display login form / login message if login failed-->
			<div id="header-form">
				<% if (loginStatus != null && !loginStatus.isEmpty()) { %>
					<p><%= loginStatus %>&nbsp;</p>
				<% } %>
			
				<form action="LoginServlet" method="post"> 
					username <input type="text" name="username" />&nbsp;
					password <input type="text" name="password" />
					<input type="submit" value="LOGIN" />
				</form>	
			</div>
		
		<% } %>
		
	</div>
	
<!-- Quiz main content -->

	<% if (quiz != null) {%>	
	<h1>Quiz <%= quiz.quiz_name %></h1>
	<form href="QuizEditController" method="post">
		<p>Edit Quiz Information:</p>
		Quiz Name: <input type="text" name="quiz_name" value="<%= quiz.quiz_name%>" /><br>
		Quiz Description: <input type="text" name="quiz_description" value="<%= quiz.quiz_description%>" /><br>
		<% 
		for (Question q: app.current_questions) {
		%>
			<p>Edit Question <%=q.question_number %></p>
			
			<%
			String type = q.type;
			if (type.equals("question_free_response")) {
				FreeResponse fr = (FreeResponse) q;
			%>
				Question Name: <input type="text" name="question<%= fr.question_number%>name" value="<%= fr.name%>" /><br>
				Question Text:<input type="text" name="question<%= fr.question_number%>question_text" value="<%= fr.question_text%>" /><br>
				Question Answer:<input type="text" name="question<%= fr.question_number%>answer" value="<%= fr.answer%>" /><br>
			<%
			} else if (type.equals("question_fill_in_blank")) {
				FillInTheBlank fib = (FillInTheBlank) q;
			%>
				Question Name: <input type="text" name="question<%= fib.question_number%>name" value="<%= fib.name%>" /><br>
				Question Text Before:<input type="text" name="question<%= fib.question_number%>question_text_before" value="<%= fib.question_text_before%>" /><br>
				Question Answer:<input type="text" name="question<%= fib.question_number%>answer" value="<%= fib.answer%>" /><br>
				Question Text After:<input type="text" name="question<%= fib.question_number%>question_text_after" value="<%= fib.question_text_after%>" /><br>
			<%
			} else if (type.equals("question_multiple_choice")) {
				MultipleChoice mc = (MultipleChoice) q;
			%>
				Question Name: <input type="text" name="question<%= mc.question_number%>name" value="<%= mc.name%>" /><br>
				Question Text:<input type="text" name="question<%= mc.question_number%>question_text" value="<%= mc.question_text%>" /><br>
				<%
				if (mc.choice_a != null) {
				%>
					Choice A:<input type="text" name="question<%= mc.question_number%>choice_a" value="<%= mc.choice_a%>" /><br>
				<%
				}
				if (mc.choice_b != null) {
				%>
					Choice B:<input type="text" name="question<%= mc.question_number%>choice_b" value="<%= mc.choice_b%>" /><br>
				<%
				}
				if (mc.choice_c != null) {
				%>
					Choice C:<input type="text" name="question<%= mc.question_number%>choice_c" value="<%= mc.choice_c%>" /><br>
				<%
				}
				if (mc.choice_d != null) {
				%>
					Choice D:<input type="text" name="question<%= mc.question_number%>choice_d" value="<%= mc.choice_d%>" /><br>
				<%
				}
				if (mc.choice_e != null) {
				%>
					Choice E:<input type="text" name="question<%= mc.question_number%>choice_e" value="<%= mc.choice_e%>" /><br>
				<%
				}
				if (mc.choice_f != null) {
				%>
					Choice F:<input type="text" name="question<%= mc.question_number%>choice_f" value="<%= mc.choice_f%>" /><br>
				<%
				}
				if (mc.choice_g != null) {
				%>
					Choice G:<input type="text" name="question<%= mc.question_number%>choice_g" value="<%= mc.choice_g%>" /><br>
				<%
				}
				if (mc.choice_h != null) {
				%>
					Choice H:<input type="text" name="question<%= mc.question_number%>choice_h" value="<%= mc.choice_h%>" /><br>
				<%
				}
				if (mc.choice_i != null) {
				%>
					Choice I:<input type="text" name="question<%= mc.question_number%>choice_i" value="<%= mc.choice_i%>" /><br>
				<%
				}
				if (mc.choice_j != null) {
				%>
					Choice J:<input type="text" name="question<%= mc.question_number%>choice_j" value="<%= mc.choice_j%>" /><br>
				<%
				}
				%>
				Question Answer:<input type="text" name="question<%= mc.question_number%>answer" value="<%= mc.answer%>" /><br>
			<%
			} else if (type.equals("question_picture_response")) {
				PictureResponse pr = (PictureResponse) q;
			%>
				Question Name: <input type="text" name="question<%= pr.question_number%>name" value="<%= pr.name%>" /><br>
				Question Text:<input type="text" name="question<%= pr.question_number%>question_text" value="<%= pr.question_text%>" /><br>
				Picture URL: <input type="text" name="question<%= pr.question_number%>picture_url" value="<%= pr.picture_url%>" /><br>
				Question Answer:<input type="text" name="question<%= pr.question_number%>answer" value="<%= pr.answer%>" /><br>
			<%	
			}	
		}
		%>
		
	
	<button type="submit" >Submit Changes</button>
	</form>	
	<%
	}
	%>
</body>

</html>