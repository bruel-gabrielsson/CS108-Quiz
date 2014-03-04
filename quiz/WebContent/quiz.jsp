<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.*" %>
<%@ page import="questions.*" %>
    
<%
	Quiz quiz = (Quiz) request.getAttribute("quiz");
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<link rel="stylesheet" type="text/css" href="./resources/css/quiz.css?v=2"/>
	<title>Quiz</title>
</head>
<body>


<div id="wrap">
	
<!--HEADER BAR-->
	<div id="header">
		<div id="header-title">QUIZZ</div>
		<div id="header-form">
			<form> 
				username <input type="text" name="username" />&nbsp;
				password <input type="text" name="password" />
				<input type="submit" value="LOGIN" />
			</form>	
		</div>
	</div>
	
<!-- Quiz main content -->

	<div>
		<h1>Quiz <%= quiz.quiz_name %></h1>
		<h1>Trivia on crabs and lobsters</h1>
		
		<ol>
			<% for (Question q : quiz.questions) { %>
				<li>
				<% int type = q.question_type_id; %>
				<%= Integer.toString(type) %>
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
					
					<%= fib.question_text_before %>
					<form>
						<input type="text" name="<%= fib.question_number %>">
					</form>
					<%= fib.question_text_after %>
					<br/>
					
					<%
						break;
					
					case 3:
						MultipleChoice mc = (MultipleChoice) q;
					%>
					
					<%= mc.question_text %>
					<input type="radio" name="q2choice1" value="<%= mc.choice_a %>"><br>
					<input type="radio" name="q2choice1" value="<%= mc.choice_b %>"><br>
					<input type="radio" name="q2choice1" value="<%= mc.choice_c %>"><br>
					<input type="radio" name="q2choice1" value="<%= mc.choice_d %>"><br>
					<input type="radio" name="q2choice1" value="<%= mc.choice_e %>"><br>
					<input type="radio" name="q2choice1" value="<%= mc.choice_f %>"><br>
				
					<%
						break;
					
					default:
						// Ignore
						break;
					}
				%>
				</li>
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