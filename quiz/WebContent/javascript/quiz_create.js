window.onload = function () {
	console.log("quiz_create.js");
	
	var options = document.getElementById("add_new_question_options");
	
	options.style.visibility = "visible";
	
	var fillInTheBlank = document.getElementById("question_fill_in_blank_option");
	var freeResponse = document.getElementById("question_free_response_option");
	var multipleChoice = document.getElementById("question_multiple_choice_option");
	
	var form = document.getElementById("quiz_create_form");
	
	var submit = document.getElementById("quiz_create_submit");
	
	var ob = {questions_nr: 0};
	var quiz_question_count = document.getElementById("quiz_question_count");
	
	
	submit.onclick = function() {
		
		var name = document.getElementById("quiz_name_form").value;
		var description = document.getElementById("quiz_description_form").value;
		var category = document.getElementById("category_form").value;
		
		console.log("submitting final form");
		if (ob.questions_nr > 0 && name != "" && description != "") {
			$.ajax( {
				   type: "POST",
				   url: "CreateQuizServlet",
				   data: {submit: "true", name: name, description: description, category: category},
				   success: function( response ) {
					   console.log( response );
				       console.log("success");
				   }
			});
		} else {
			if(ob.questions_nr < 0) {
				quiz_question_count.innerHTML = "<h3 style='color:red'>Cannot submit an empty quiz</h3>";
			} else {
				quiz_question_count.innerHTML = "<h3 style='color:red'>Specify name and description please</h3>";
			}
		}
			
	}
	
	// Validate all values filled
	var validateForm = function(form) {
		var authent = form.serializeArray();
		for (var i in authent) {
			console.log(authent[i].value);
			if (authent[i].value === "") {
				return false;
			}
		}
		
		return true;
	}
	
	
	var submitAndNew = document.getElementById("submit_and_new_question");
	submitAndNew.onclick = function(e) {
		// SUBMIT POST REQUEST TO SERVELT TO CURRENT 
		
		//$("#quiz_create_form").ajaxSubmit({url: 'CreateQuizServlet', type: 'post'});
		
		var Qform = $('#quiz_create_form');
		console.log("submitting question");
		
		if(validateForm(Qform)) {
	
			$.ajax( {
			   type: "POST",
			   url: "CreateQuizServlet",
			   data: Qform.serialize(),
			   success: function( response ) {
				   console.log( response );
				   console.log("submitted question");
				   form.innerHTML = "";
				   options.style.display = "block";
				   e.target.style.display = "none";
				   ob.questions_nr++;
				   quiz_question_count.innerHTML = "Questions: " + ob.questions_nr;
						
			   }
			});
		} else {
			quiz_question_count.innerHTML = "<h3>Please fill in all values</h3>"
		}
		
		
	};
	
	fillInTheBlank.onclick = function() {
		options.style.display = "none";
		
		//var div = document.createElement('div');
		//div.id = 'block';
		//div.className = 'row';
		var html = "<h3>Fill In The Blank:</h3>";
		html += "<input type='hidden' name='type' value='question_fill_in_blank'></input>"
		html += "Name: <input type='text' name='name' ></input><br/>";
		html += "Question text before: <input type='text' name='question_text_before' ></input><br/>";
		html += "Answer: <input type='text' name='answer' ></input><br/>";
		html += "Question text before: <input type='text' name='question_text_after' ></input><br/>";
		//div.innerHTML = html;
		form.innerHTML = html;
		submitAndNew.style.display = "inline-block";
	}
	
	freeResponse.onclick = function() {
		options.style.display = "none";
		
		var html = "<h3>Free Response:</h3>";
		html += "<input type='hidden' name='type' value='question_free_response'></input>"
		html += "Name: <input type='text' name='name' ></input><br/>";
		html += "Question: <input type='text' name='question' ></input><br/>";
		html += "Answer: <input type='text' name='answer' ></input><br/>";
		form.innerHTML = html;
		submitAndNew.style.display = "inline-block";
	}
	
	multipleChoice.onclick = function() {
		options.style.display = "none";
		
		var letters = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];
		
		var html = "<h3>Multiple Choice:</h3>";
		html += "<input type='hidden' name='type' value='question_multiple_choice'></input>"
		html += "Name: <input type='text' name='name'></input><br/>";
		html += "Question: <input type='text' name='question_text' ></input><br/>";
		html += "<h4>Please Mark the Option That Is Correct</h4><br/>"
		html += "Option A: <input type='radio' name='answer' value='A'></input>";
		html += "<input type='text' name='choice_a' ></input><br/>";
		html += "Option B: <input type='radio' name='answer' value='B'></input>";
		html += "<input type='text' name='choice_b' ></input><br/>";
		html += "Option C: <input type='radio' name='answer' value='C'></input>";
		html += "<input type='text' name='choice_c' ></input><br/>";
		html += "Option D: <input type='radio' name='answer' value='D'></input>";
		html += "<input type='text' name='choice_d' ></input><br/>";
		html += "Option E: <input type='radio' name='answer' value='E'></input>";
		html += "<input type='text' name='choice_e' ></input><br/>";
		form.innerHTML = html;
		submitAndNew.style.display = "inline-block";
	}
	
	
	
	
}