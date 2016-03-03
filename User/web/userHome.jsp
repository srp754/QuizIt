<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,User.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% User user = (User) session.getAttribute("user"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%= user.getUsername() %> Home</title>
</head>
<body>
<h1><%= user.getUsername() %> Home Page</h1>
<h5>
    <% if(user.isAdmin()) {
        out.println("Admin User");
        out.println("<a href='admin.jsp'>Admin options</a>");
        }
    else {
        out.println("Standard User");
    }
    %>
</h5>
<p>Create a new quiz</p>
<form action="CreatedQuizServlet" method="post">
    <p>Quiz ID: <input type="text" name="quizid" /></p>
        <input type="submit" value="CreateQuiz"/></p>
</form>
<h1>Achievements</h1>
<ul>
    <%
        for(String s : user.getAchievements()) {
            out.println("<li>" + s + "</li>");
        }
    %>
</ul>
<h1>Recently Created Quizzes</h1>
<ul>
    <%
        for(String s : user.getRecentlyCreatedQuizzes()) {
            out.println("<li>" + s + "</li>");
        }
    %>
</ul>
</body>
</html>