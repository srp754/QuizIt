var i = 1; // used to track how many extra questions were added 

function increment() {
	var numQuestions = document.getElementById("numquestions");
	numQuestions.value = i; 
	i+=1; // Increments the counter when "add a question" is pressed
}

// TODO: Consider validateData method 

function addQuestion() { 
	var questionOptions = document.getElementById("questiontype");
	var questionType = questionOptions.options[questionOptions.selectedIndex].value; // i.e. "qresponse"
	if (questionType == "qresponse") {
		addQResponse(); 
		return false; 
	} else if(questionType == "fillblank") {
		
	} else if(questionType == "multiplechoice") {
		
	} else {
		// Please select a question type 
	}
	return false; 
}

function addQResponse() {
	var question = document.getElementById("dynamic");
	question.appendChild(document.createElement("br"));
	question.appendChild(document.createTextNode(i+". Question Text: ")); 
	var input = document.createElement("input");
	input.type = "text";
	input.name = "question" + i; // How to reference the field in the servlet 
	question.appendChild(input); 
	question.appendChild(document.createElement("br"));
	question.appendChild(document.createTextNode(i+". Answer Text: ")); 
	var answerInput = document.createElement("input");
	answerInput.type = "text";
	answerInput.name = "answer" + i; 
	question.appendChild(answerInput);
	question.appendChild(document.createElement("br"));
	increment(); 
	return false; 
}

function addFillBlank(){
	
	increment(); 
	return false; 
}

