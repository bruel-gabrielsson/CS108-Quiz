<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.*" %>
<%@ page import="questions.*" %>
<%@ page import="app.*" %>
    
<%
	Quiz quiz = (Quiz) request.getAttribute("quiz");
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<link rel="stylesheet" type="text/css" href="./resources/css/quiz.css"/>
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


	<% String quiz_id = request.getParameter("quiz_id"); %>
	<% if (quiz_id != null && !quiz_id.isEmpty()) {%>	
		
	<div>
		<h1>Quiz <%= quiz.quiz_name %></h1>
		<h1>Trivia on crabs and lobsters</h1>
		
		<ol>
			<% for (Question q : quiz.questions) { %>
				<li>
				<div>
				<% int type = q.question_type_id; %>
				<% switch (type) {
				
					case 1: 
						FreeResponse fr = (FreeResponse) q;
					%> 
					
					<%= fr.question_text %>
					<form>
						<input type="text" name="<%= fr.question_number %>">
						<br>
					</form>
					
					<%
						break;
					
					case 2:
						FillInTheBlank fib = (FillInTheBlank) q;
					%>
					
					<form>
						<%= fib.question_text_before %>
						<input type="text" name="<%= fib.question_number %>">
						<%= fib.question_text_after %>
					</form>
					
					<br/>
					
					<%
						break;
					
					case 3:
						MultipleChoice mc = (MultipleChoice) q;
					%>
					
					<%= mc.question_text %>
					<br></br>
					<p><%= mc.choice_a %>
					<input type="radio" name="q2choice1" value="<%= mc.choice_a %>"></input><br/>
					<%= mc.choice_b %>
					<input type="radio" name="q2choice1" value="<%= mc.choice_b %>"></input><br/>
					<%= mc.choice_c %>
					<input type="radio" name="q2choice1" value="<%= mc.choice_c %>"></input><br/>
					<%= mc.choice_d %>
					<input type="radio" name="q2choice1" value="<%= mc.choice_d %>"></input><br/>
					<%= mc.choice_e %>
					<input type="radio" name="q2choice1" value="<%= mc.choice_e %>"></input><br/>
					<%= mc.choice_f %>
					<input type="radio" name="q2choice1" value="<%= mc.choice_f %>"></input><br/>
					</p>
					<%
						break;
					
					default:
						// Ignore
						break;
					}
				%>
				</div>
				</li>
			<% } %>
		<% } %>
			
			
			<!--  
			<li>
				How long do they live?
				<form>
					<input type="radio" name="q2choice1" value="5years">5 years<br>
					<input type="radio" name="q2choice2" value="10years">10 years<br>
				</form>
			</li>
			-->
		</ol>
	</div>

</div>

</body>
</html>