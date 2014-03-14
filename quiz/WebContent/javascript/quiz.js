window.onload = function () {
		
		console.log("quiz javascript");
		
		var correction = document.getElementById("correction_div").getAttribute("correction_value");	
		
		var practice = document.getElementById("practice_div").getAttribute("practice_value");
		
		var time_value = document.getElementById("time_div").getAttribute("time_value");
		
		console.log("TIME VALUE" + time_value);
		
		var timer_value = parseInt(time_value);
		
		var submit_correction = document.getElementById("submit_quiz_correction");
		
		var counter=setInterval(timer, 1000); //1000 will  run it every 1 second
		
		var timer_div = document.getElementById("show_timer_value_div");
		
		if (isNaN(timer_value)) {timer_value = 240;}; // 240 default value	
		
		timer_div.innerHTML = "Time Left: " + timer_value + "s";

		function timer()
		{
		timer_value = timer_value-1;
		  if (timer_value <= 0)
		  {
		     clearInterval(counter);
		     //counter ended, do something here
		     submit_correction.submit();
		     return;
		  }
		  //Do code for showing the number of seconds here
		  timer_div.innerHTML = "Time Left: " + timer_value + "s";
		}
		
		console.log(correction + " " + practice);
		
		if (practice === "on") {
			
			if (submit_correction) {
				submit_correction.innerHTML = "Done With Practice";
				submit_correction.onclick = function(e) {
					e.preventDefault();
					window.history.back();
				}
			}
			
			var form = document.getElementsByTagName("UL")[0];
			var list = form.getElementsByTagName("LI");
			var length = list.length;
			console.log("LENGTH" + length);
			for(var i = 0; i < length; i ++) {
				var test = list[i].getElementsByTagName("INPUT");
				console.log(test[0]);
				if(test[0]) {
					if(test[0].getAttribute("type") === "text") {
						
						test[0].addEventListener('change', function(e) {
							
							console.log("input:" + e.target.value);
							var value = e.target.value;
							var answer = e.target.parentNode.getElementsByClassName("answer")[0].getAttribute("answer");
							console.log("answer" + answer);
							
							if (answer === value) {
								var content = "Answer Correct!";
								var feedback = e.target.parentNode.getElementsByClassName("question_feedback")[0];
								feedback.innerHTML = content;
								feedback.style.color = "green";
							} else {
								var content = "Answer Incorret";
								var feedback = e.target.parentNode.getElementsByClassName("question_feedback")[0];
								feedback.innerHTML = content;
								feedback.style.color = "red";
							}
							
							
						}, false);
					} else if (test[0].getAttribute("type") === "radio") {
						for ( var n = 0; n < test.length; n++ ) 
						{ 
							test[n].onclick = function(e) {
								console.log("CLICKED" + e.target.value);
								
								var value = e.target.value;
								var answer = e.target.parentNode.parentNode.getElementsByClassName("answer")[0].getAttribute("answer");
								console.log("answer" + answer);
								
								if (answer === value) {
									var content = "Answer Correct!";
									var feedback = e.target.parentNode.parentNode.getElementsByClassName("question_feedback")[0];
									feedback.innerHTML = content;
									feedback.style.color = "green";
								} else {
									var content = "Answer Incorret";
									var feedback = e.target.parentNode.parentNode.getElementsByClassName("question_feedback")[0];
									feedback.innerHTML = content;
									feedback.style.color = "red";
								}
								
							};

						}
						
					}
				}
			}
			
		} else { // practice none
			if (correction == "immediate") {
				console.log("immediate");
			} else { //later
				console.log("later");
			}
			
		}
		
		multipleQuestions(practice, correction);
		
	};
	
	var multipleQuestions = function(practice, correction) {
		
		var questions = document.getElementsByClassName("question_multiple_pages");
		if(questions.length) {
			var i = questions.length - 1;
			console.log(i);
			
			questions[i].style.display = "inline-block";
			var first = questions[i];
			i = i - 1;
			console.log(i);
			
			var next = document.getElementsByClassName("multiple_pages_next")[0];
			next.addEventListener("click", function(e) {
				//e.target.removeEventListener(e.type, arguments.callee);
				if (i >= 0) {
					var previous = i + 1;
					// Immediate feedback
					if (correction == "immediate") {
						
						var input = questions[previous].getElementsByTagName("input");
						
						if(input[0].getAttribute("type") === "text") {
						
							var value = input[0].value;
							var answer = questions[previous].getElementsByClassName("answer")[0].getAttribute("answer");
							console.log("answer" + answer + " " + value);
							
							if (answer === value) {
								alert("Your answer was correct!");
							} else {
								alert("Your answer was incorrect, better luck next time");
							}
							
						} else if (input[0].getAttribute("type") === "radio") {
							for (var n = 0; n < input.length; n ++) {
								if (input[n].checked) {
									var answer = questions[previous].getElementsByClassName("answer")[0].getAttribute("answer");
									var value = input[n].value;
									console.log("answer" + answer + " " + value);
									if (answer === value) {
										alert("Your answer was correct!");
									} else {
										alert("Your answer was incorrect, better luck next time");
									}
									
								}
								
							}
							
						}
					}
					
					
					questions[i].style.display = "inline-block";
					
					console.log(previous);
					questions[parseInt(previous)].style.display = "none";
					
					i = i-1;
					
					
				} else {
					if (practice != "on") {
						var submit = document.getElementsByClassName("multiple_pages_submit")[0];
						submit.style.display = "inline-block";
						e.target.style.display = "none";
					} else { // on
						var submit = document.getElementsByClassName("multiple_pages_submit")[0];
						submit.style.display = "inline-block";
						e.target.style.display = "none";
						submit.innerHTML = "Done With Practice";
						submit.onclick = function(e) {
							e.preventDefault();
							window.history.back();
						}
					}
				}
				
			});
		}
}