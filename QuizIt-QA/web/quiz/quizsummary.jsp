<%@ page import="quiz.*, user.*, java.util.*" %>
<% IUserRepository user = (UserRepository) session.getAttribute("user"); %>
<%
	String quizIdStr = request.getParameter("id");
	int quizId = Integer.parseInt(quizIdStr);
	QuizSummary wantedSummary = QuizRepository.GetQuizSummary(quizId);
	int totalAttempts = 0;
	QuizStats wantedStats = QuizRepository.GetQuizStats(quizId);
	if(wantedStats != null) {
		totalAttempts = wantedStats.getQuizAttempts();
	}
%>
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

	<title><%out.println(wantedSummary.getQuizName()); %></title>

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
				<li><a href="/user/userHomePage.jsp">Home</a></li>
				<li class="active"><a href="/quiz/quizhomepage.jsp">Quiz</a></li>
				<li><a href="/user/userFeed.jsp">Feed</a></li>
				<% if(user.isAdmin()) {
					out.println("<li><a href='/admin/dashboard.jsp'>Admin</a></li>");
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

<div class="jumbotron">
	<div class="container">
		<h1><%out.println(wantedSummary.getQuizName()); %></h1>
		<h2>Creator: <%out.println(wantedSummary.getCreatorId()); %></h2>
		<h2>Total Attempts: <%out.println(totalAttempts); %></h2>
		
		<form action="/MessageServlet" method="post">
		<p>Challenge a friend! <input type="text" name="username" />
		<input name="messagetype" type="hidden" value="challenge"/>
		<input name="content" type="hidden" value="<%= quizId %>"/>
		<input type="submit" value="Send Challenge" /></p>
		</form>
		<%
		if (request.getAttribute("status") != null) {
		%>
		<p><%= (String) request.getAttribute("status") %></p>
		<%
		}
		%>
		
		<p><%out.println(wantedSummary.getQuizDescription()); %></p>
		<p><a class="btn btn-primary btn-lg" href="takequiz.jsp?id=<%= quizId %>" role="button">Take Quiz</a>
			<p <%
				if (wantedSummary.getCreatorId() != user.getUserId())
					out.print(" hidden ");
			%>
			 >

	</div>
</div>

<div class="container">
	<div class="row">
		<div class="col-md-3">
			<h2>Statistics</h2>
		</div>

		<div class="col-md-3">
			<h2>All-Time Top Performers</h2>
		</div>

		<div class="col-md-3">
			<h2>24 Hr Top Performers</h2>
		</div>
		<div class="col-md-3">
			<h2>Your Performance</h2>
		</div>
	</div>
</div>

</body>




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
