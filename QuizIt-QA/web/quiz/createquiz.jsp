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

<form id="questionform" action="../CreateQuestionServlet" method="post">
	<input type="hidden" name="questionnumber" value="0" />
Select Question Type:
<select id="questiontype" name="questiontype">
	<option value="qresponse" selected="selected">Question-Response</option>
	<option value="fillblank">Fill in the Blank</option>
	<option value="multiplechoice">Multiple Choice</option>
	<option value="pictureresponse">Picture-Response</option>
</select>
<br>
	<input type="submit" name="add" value="Add Question">
</form>
</body>
</html>