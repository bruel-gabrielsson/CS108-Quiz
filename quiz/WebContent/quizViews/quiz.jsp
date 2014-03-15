<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.*" %>
<%@ page import="questions.*" %>
<%@ page import="app.*" %>
    
<%
	App app = (App) request.getAttribute("app");
	Quiz quiz = (Quiz) request.getAttribute("quiz");
	String correction_value = (String) request.getParameter("correction");
	String practice_value = (String) request.getParameter("practice");
	long time_v  = app.current_quiz.quiz_timer;
	String time_value = "240";
	if (quiz != null) {
		System.out.println("TIMER:!!!" + quiz.quiz_timer);
		time_value = (String) String.valueOf(quiz.quiz_timer);
	}
	
	
	

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

<div id="practice_div" practice_value="<%= practice_value %>" ></div>
<div id="correction_div" correction_value="<%= correction_value %>" ></div>
<div id="time_div" time_value="<%= time_value %>" ></div>
	
<div id="wrap">
	
<!--HEADER BAR-->
	<div id="header">
		<div id="header-title">QUIZZ</div>

		<!-- Grab username and loginStatus (if they exist) from the session  -->
		<%	String username = (String)session.getAttribute("username");
			String loginStatus = (String)session.getAttribute("loginStatus"); %>
		
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
		<h1><%= quiz.category_name %>, <%= quiz.quiz_description %></h1>
		<div id="show_timer_value_div"></div>
		
		<form href="QuizController" method="post">
			Rate this quiz:
			<select name="rating" id="rating_form" >
			  <option value="1">1</option>
			  <option value="2">2</option>
			  <option value="3">3</option>
			  <option value="4">4</option>
			  <option value="5">5</option>
			</select>
		
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
				} else if(type.equals("question_multiple_choice")) { // "question_multiple_choice"
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
					
					</div>
					<hr></hr>
					
					<div style="hidden" class="answer" answer="<%= mc.answer %>" ></div>
					
					<%
				} else { // picture response
					PictureResponse pr = (PictureResponse) q;
					%>
					
					<%= pr.name %>:
					<%= pr.question_text %>
					
					<img src='<%= pr.picture_url %>'></img>
					
					<input type="text" name="question<%= pr.question_number %>"></input>
				
					<div style="hidden" class="answer" answer="<%= pr.answer %>" ></div>
				<%
				}
				%>
				</div>
				</li>
			<% } %>
		<% } %>
		</ul>
		
		<button id="submit_quiz_correction" type="submit" >Submit Answers</button>
		</form>
	</div>

</div>

</body>

</html>