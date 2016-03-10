<%@ page import="quiz.*, user.*, java.util.*" %>
<% IUserRepository user = (UserRepository) session.getAttribute("user"); %>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Quiz Home Page</title>

    <!-- Bootstrap core CSS -->
    <link href="../dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="../assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../css/starter-template.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">QuizIt</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
<<<<<<< HEAD
                <li class="active"><a href="/user/userHomePage.jsp">Home</a></li>
                <li><a href="/quiz/quizhomepage.jsp">Quiz</a></li>
                <li><a href="#feed">Feed</a></li>
                <% if(user.isAdmin()) {
                    out.println("<li><a href='/user/dashboard.jsp'>Admin</a></li>");
=======
                <li><a href="/user/userHomePage.jsp">Home</a></li>
                <li><a href="/quiz/quizhomepage.jsp">Quiz</a></li>
                <li class="active"><a href="/user/userFeed.jsp">Feed</a></li>
                <% if(user.isAdmin()) {
                    out.println("<li><a href='/admin/dashboard.jsp'>Admin</a></li>");
>>>>>>> Ashavsky/master
                }
                %>
                <li><a href="/user/messages.jsp">&#128172;</a></li>
            </ul>
            <form class="navbar-form navbar-right" action="/SignOutServlet" method="post">
                <button type="submit" class="btn btn-primary">Sign Out</button>
            </form>
            <form class="navbar-form navbar-right" action="/UserSearchServlet" method="post">
                <div class="form-group">
                    <input type="text" placeholder="&#128269;" class="form-control" name="username">
                </div>
            </form>
        </div><!--/.navbar-collapse -->
    </div>
</nav>

<!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
    <div class="container">
        <h1>Take a Quiz</h1>
    </div>
</div>

<div class=""container-fluid>
    <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-11">
            <h1 class="page-header">Select a Quiz</h1>
            <div class="list-group">
            <%
                List<QuizSummary> quizSummaries = (List<QuizSummary>) getServletContext().getAttribute("quizsummary");
                for (QuizSummary currentSummary: quizSummaries) {
            %>
            <div class="row">
            <a href="quizsummary.jsp?id=<%=currentSummary.getQuizId() %>" class='list-group-item col-md-3'><%out.println(currentSummary.getQuizName());%></a></div>
            <%
                }
            %>
            </div>
        </div>
    </div>

</div><!-- /.container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="../dist/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>