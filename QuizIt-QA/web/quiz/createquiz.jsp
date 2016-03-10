<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="createquiz.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz Creator</title>
</head>
<body>
<h1>Create Quiz</h1>
<%
String noQuestions = request.getParameter("noquestions");
if(noQuestions!= null) {
%>
</p><%out.println(noQuestions); %> </p>
<%
}
%>
<form id="questionform" action="CreateQuizServlet" method="post">
<input type="hidden" name="numquestions" id="numquestions" value="0">
Quiz Name: <input type="text" name="quizname"> <br>
Quiz Description: <input type="text" name="quizdescription"> <br> 
Question Type: 
<select id="questiontype">
	<option value="qresponse" selected="selected">Question-Response</option>
	<option value="fillblank">Fill in the Blank</option>
	<option value="multiplechoice">Multiple Choice</option>
</select>
<button type="button" onclick="javascript: addQuestion()" onsubmit="return false">Add A Question</button>
<span id = "dynamic"></span>
<br> 
<input type="submit" value="Continue">
</form>
</body>
</html>