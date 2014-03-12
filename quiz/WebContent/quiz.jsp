<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.*" %>
<%@ page import="questions.*" %>
<%@ page import="app.*" %>
    
<%
	Quiz quiz = (Quiz) request.getAttribute("quiz");
	String correction_value = (String) request.getParameter("correction");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" /> 
	
	<!-- JAVASCRIPT -->
	<%@ include file="media/quiz_js.jspf" %>
	<title>Quiz</title>
</head>
<body>

<div id="correction_div" correction_value="<%= correction_value %>" ></div>
	
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


	<% String quiz_id = request.getParameter("quiz_id"); %>
	<% if (quiz_id != null && !quiz_id.isEmpty()) {%>	
		
	<div>
		<h1>Quiz <%= quiz.quiz_name %></h1>
		<h1>Trivia on crabs and lobsters</h1>
		
		<form href="QuizController" method="post">
		
		<ul>
			<% for (Question q : app.current_questions) { %>
				<li>
				<div>
					<div class="question_feedback"></div>
				<% String type = q.type; %>
				<% if (type.equals("question_free_response")) {
						FreeResponse fr = (FreeResponse) q;
						
					%> 
					
					<%= fr.name %>:
					<%= fr.question_text %>
						<input type="text" name="question<%= fr.question_number %>">
						<hr></hr>
						<br>
						
						<div style="hidden" class="answer" answer="<%= fr.answer %>" ></div>
					
					<%
				} else if (type.equals("question_fill_in_blank")) {
						FillInTheBlank fib = (FillInTheBlank) q;
					%>
						<%= fib.name %>:
						<%= fib.question_text_before %>
						<input type="text" name="question<%= fib.question_number %>">
						<%= fib.question_text_after %>
						<hr></hr>
						<br/>
						
						<div style="hidden" class="answer" answer="<%= fib.answer %>" ></div>
					
					<%
				} else { // "question_multiple_choice"
						MultipleChoice mc = (MultipleChoice) q;
					%>
					
					<%= mc.name %>:
					<%= mc.question_text %>
					<br></br>
					<div>
					<% if (mc.choice_a != null) { %>
						<%= mc.choice_a %>
						<input type="radio" name="question<%= mc.question_number %>" value="A"></input><br/>
					<% } %>
					<% if (mc.choice_b != null) { %>
						<%= mc.choice_b %>
						<input type="radio" name="question<%= mc.question_number %>" value="B"></input><br/>
					<% } %>
					<% if (mc.choice_c != null) { %>
						<%= mc.choice_c %>
						<input type="radio" name="question<%= mc.question_number %>" value="C"></input><br/>
					<% } %>
					<% if (mc.choice_d != null) { %>
						<%= mc.choice_d %>
						<input type="radio" name="question<%= mc.question_number %>" value="D"></input><br/>
					<% } %>
					<% if (mc.choice_e != null) { %>
						<%= mc.choice_e %>
						<input type="radio" name="question<%= mc.question_number %>" value="E"></input><br/>
					<% } %>
					<% if (mc.choice_f != null) { %>
						<%= mc.choice_f %>
						<input type="radio" name="question<%= mc.question_number %>" value="F"></input><br/>
					<% } %>
					<% if (mc.choice_g != null) { %>
						<%= mc.choice_g %>
						<input type="radio" name="question<%= mc.question_number %>" value="G"></input><br/>
					<% } %>
					
					</div>
					<hr></hr>
					
					<div style="hidden" class="answer" answer="<%= mc.answer %>" ></div>
					
					<%
					}
				%>
				</div>
				</li>
			<% } %>
		<% } %>
		</ul>
		
		<button type="submit" >Submit Answers</button>
		</form>
	</div>

</div>

</body>

</html>