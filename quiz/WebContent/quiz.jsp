<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="./resources/css/quiz.css?v=2"/>
	<title>Quiz title here</title>
</head>
<body>


<div id="wrap">
	
<!--HEADER BAR-->
	<div id="header">
		<div id="header-title">QUIZZ</div>
		<div id="header-form">
			<form> 
				username <input type="text" name="username">&nbsp;
				password <input type="text" name="password">
				<input type="submit" value="LOGIN">
			</form>	
		</div>
	</div>
	
	<div>
		<h1>Trivia on crabs and lobsters</h1>
		
		<ol>
			<li>
				What is the scientific family for lobsters?
				<form>
					<input type="radio" name="q1choice1" value="nephropidae">Nephropidae<br>
					<input type="radio" name="q1choice2" value="animalia">Animalia<br>
				</form>
			</li>
			
			<li>
				How long do they live?
				<form>
					<input type="radio" name="q2choice1" value="5years">5 years<br>
					<input type="radio" name="q2choice2" value="10years">10 years<br>
				</form>
			</li>
		</ol>
	</div>

</div>

</body>
</html>