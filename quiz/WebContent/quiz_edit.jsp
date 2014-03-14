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
		<p>Quiz Name: <input type="text" name="quiz_name" value="<%= quiz.quiz_name%>" /></p>
		<p>Quiz Description: <input type="text" name="quiz_description" value="<%= quiz.quiz_description%>" /></p>
		<% for (Question q: app.current_questions) { 
				String type = q.type;
				if (type.equals("question_free_response")) {
					FreeResponse fr = (FreeResponse) q;
		%>
					<p>Question Name: </p><input type="text" name="question<%= fr.question_number%>name" value="<%= fr.name%>" />
					<p>Question Text:</p><input type="text" name="question<%= fr.question_number%>question_text" value="<%= fr.question_text%>" />
					<p>Question Answer:</p><input type="text" name="question<%= fr.question_number%>answer" value="<%= fr.answer%>" />
				<%
				} else if (type.equals("question_fill_in_blank")) {
					FillInTheBlank fib = (FillInTheBlank) q;
				%>
					<p>Question Name: </p><input type="text" name="question<%= fib.question_number%>name" value="<%= fib.name%>" />
					<p>Question Text Before:</p><input type="text" name="question<%= fib.question_number%>question_text_before" value="<%= fib.question_text_before%>" />
					<p>Question Answer:</p><input type="text" name="question<%= fib.question_number%>answer" value="<%= fib.answer%>" />
					<p>Question Text After:</p><input type="text" name="question<%= fib.question_number%>question_text_after" value="<%= fib.question_text_after%>" />
				<%
				} else if (type.equals("question_multiple_choice")) {
					MultipleChoice mc = (MultipleChoice) q;
				%>
					<p>Question Name: </p><input type="text" name="question<%= mc.question_number%>name" value="<%= mc.name%>" />
					<p>Question Text:</p><input type="text" name="question<%= mc.question_number%>question_text" value="<%= mc.question_text%>" />
					<%
					if (mc.choice_a != null) {
					%>
						<p>Choice A:</p><input type="text" name="question<%= mc.question_number%>choice_a" value="<%= mc.choice_a%>" />
					<%
					}
					if (mc.choice_b != null) {
					%>
						<p>Choice B:</p><input type="text" name="question<%= mc.question_number%>choice_b" value="<%= mc.choice_b%>" />
					<%
					}
					if (mc.choice_c != null) {
					%>
						<p>Choice C:</p><input type="text" name="question<%= mc.question_number%>choice_c" value="<%= mc.choice_c%>" />
					<%
					}
					if (mc.choice_d != null) {
					%>
						<p>Choice D:</p><input type="text" name="question<%= mc.question_number%>choice_d" value="<%= mc.choice_d%>" />
					<%
					}
					if (mc.choice_e != null) {
					%>
						<p>Choice E:</p><input type="text" name="question<%= mc.question_number%>choice_e" value="<%= mc.choice_e%>" />
					<%
					}
					if (mc.choice_f != null) {
					%>
						<p>Choice F:</p><input type="text" name="question<%= mc.question_number%>choice_f" value="<%= mc.choice_f%>" />
					<%
					}
					if (mc.choice_g != null) {
					%>
						<p>Choice G:</p><input type="text" name="question<%= mc.question_number%>choice_g" value="<%= mc.choice_g%>" />
					<%
					}
					if (mc.choice_h != null) {
					%>
						<p>Choice H:</p><input type="text" name="question<%= mc.question_number%>choice_h" value="<%= mc.choice_h%>" />
					<%
					}
					if (mc.choice_i != null) {
					%>
						<p>Choice I:</p><input type="text" name="question<%= mc.question_number%>choice_i" value="<%= mc.choice_i%>" />
					<%
					}
					if (mc.choice_j != null) {
					%>
						<p>Choice J:</p><input type="text" name="question<%= mc.question_number%>choice_j" value="<%= mc.choice_j%>" />
					<%
					}
					%>
					<p>Question Answer:</p><input type="text" name="question<%= mc.question_number%>answer" value="<%= mc.answer%>" />
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