<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>Create a new Quiz</title>
	<!-- CSS - library -->
	<link rel="stylesheet" type="text/css" href="css/library/foundation.min.css">
	<link rel="stylesheet" type="text/css" href="css/library/normalize.css">
	
	<link rel="stylesheet" type="text/css" href="css/quiz_create.css">
	
	<!-- JAVASCRIPT -->
	<script type="text/javascript" src="javascript/quiz_create.js" ></script>
	<script type="text/javascript" src="javascript/jquery.js" ></script>
</head>
<body>
	<div class="row">
		<h3>Create a new quiz!</h3>
	</div>
	<div class="row" id="quiz_question_count">
		Questions: 0
	</div>
	<div class=row>
	 	<div class="row">
	 		<div class="columns large-6"></div>
				<form id="quiz_create_form">
				
					
				</form>
			</div>
		</div>
			<div class="row">
				<button id="submit_and_new_question">Submit Question and Add New Question</button>
			</div>
			<br/>
			<div class="row" id="add_new_question_options">
				<h5>Choose what type of questions you'd like to add</h5>
				<button id="question_fill_in_blank_option">Fill In The Blank</button>
				<button id="question_free_response_option">Free Response</button>
				<button id="question_multiple_choice_option">Multiple Choice</button>
			</div>
			<br/>
			<div class="row">
				<button id="quiz_create_submit">Submit quiz</button>
			</div>
			
	</div>
	



</body>
</html>