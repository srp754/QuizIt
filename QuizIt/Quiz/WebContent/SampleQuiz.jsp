<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import = "quiz.*" %>
<%@ page import = "java.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
List<Question> questions = new ArrayList<Question>();
String q1 = "What is 2+2?"; 
QResponseAnswer qra1 = new QResponseAnswer("4", 0);
QResponse qr1 = new QResponse(q1, qra1, 1);
String q2 = "What is 3+3?"; 
QResponseAnswer qra2 = new QResponseAnswer("6", 1);
QResponse qr2 = new QResponse(q2, qra2, 2);
String q3 = "What is 4+4?"; 
QResponseAnswer qra3 = new QResponseAnswer("8", 2);
QResponse qr3 = new QResponse(q3, qra3, 3);
questions.add(qr1);
questions.add(qr2);
questions.add(qr3);  
Quiz sampleQuiz = new Quiz(questions, 0);
%>
<title>Sample Quiz</title>
</head>
<body>
<h1>Sample Quiz</h1>
<form> 
<%
List<Question> quizQuestions = sampleQuiz.getQuestions(); 
for(Question currQuestion : quizQuestions) {
%>	
<%out.println(currQuestion.toString());%>
<br>
<input type="text" name=<%=currQuestion.getId() %>>
<br>
<%
}
%>
<input type="submit" value="Submit">
</form>
</body>
</html>