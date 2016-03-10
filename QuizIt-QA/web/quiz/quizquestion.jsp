<%--
  Created by IntelliJ IDEA.
  User: Larry268
  Date: 3/10/2016
  Time: 2:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="quiz.*, user.*, java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="/quiz/createquiz.js"></script>
    <title>Add Quiz Question</title>
</head>
<body>
<form id="questionform" action="../CreateQuestionServlet" method="post">
    <%
        int questionNumber = (int) request.getAttribute("questionnumber");
        String lastQuestionType = (String) request.getAttribute("lastquestiontype");
    %>
    <input type="hidden" name="questionnumber" value="<%=questionNumber%>" />
    <input type="hidden" name="lastquestiontype" value="<%=lastQuestionType%>"/>
    <%out.println(questionNumber+".");%>
    <span id = "dynamic"></span>
    <script>var jsVar ="<%out.print(lastQuestionType);%>"; addQuestion(jsVar)</script>
    <input type="hidden" name="jspName" value="test" />
    Select Question Type:
<select id="questiontype" name="questiontype">
    <option value="qresponse" selected="selected">Question-Response</option>
    <option value="fillblank">Fill in the Blank</option>
    <option value="multiplechoice">Multiple Choice</option>
    <option value="pictureresponse">Picture-Response</option>
</select>
<br>
<input type="submit" name="add" value="Add Question">
    <br>
    <input type="submit" name="complete" value="Complete Quiz">
</form>

</body>
</html>
