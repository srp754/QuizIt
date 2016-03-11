<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Finalize Create Quiz</title>
</head>
<body>
<form id="questionform" action="../CreateQuizServlet" method="post">
Quiz Name: <input type="text" name="quizname"> <br>
Quiz Description: <input type="text" name="quizdescription"> <br>
    <input type="submit" value="Publish Quiz">
</form>
</body>
</html>
