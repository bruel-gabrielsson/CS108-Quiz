<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="./resources/css/home.css?v=2"/>
	<title>Welcome to Quizz!</title>
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
			</form>	
		</div>
	</div>

<!--MAIN, LEFT SECTION-->
	<div id="main">
		<div class="largeHeader">Most Popular</div>
		<ul>
		
			<!--TODO: Auto-generate quiz items based on below template-->
			<li>
				<div class="quizWidget">
					<div class="imgDiv">
						<img src="crab.jpg" alt="crab.jpg" height="42" width="42"></img>
					</div>
					<div class="contentDiv">
						Trivia on crabs<br/>
						Created by Jikyu Choi
					</div>
					<div class="dateDiv">
						February 4th, 2014
					</div>
				</div>
			</li>
		</ul>
		
		<div class="largeHeader">Recently Created</div>

	</div>

<!--RIGHT SECTION-->
	<div id="sidebar">
		<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit...</p>
		<div class="smallHeader">YOUR QUIZZES</div>
		<div class="smallHeader">YOUR FRIENDS</div>
		<div class="smallHeader">YOUR ACHIEVEMENTS</div>
	</div>
	
</div>


</body>
</html>