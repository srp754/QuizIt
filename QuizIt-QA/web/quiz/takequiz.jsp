<%@ page import="quiz.*, user.*, java.util.*" %>
<% IUserRepository user = (UserRepository) session.getAttribute("user"); %>
<%
	String startTime =  String.valueOf(System.currentTimeMillis());
	String quizIdStr = request.getParameter("id");
	int quizId = Integer.parseInt(quizIdStr);
	QuizSummary wantedSummary = QuizRepository.GetQuizSummary(quizId);
	int userId = user.getUserId();
	Quiz quiz = QuizRepository.GetQuiz(quizId);
	List<Question> quizQuestions = quiz.getQuestions();
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

	<title><% out.println(wantedSummary.getQuizName()); %></title>

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


<div class="container">

	<div class="starter-template">
		<h1>
			<%
				out.println(wantedSummary.getQuizName());
			%>
		</h1>

		<form action="../CheckAnswerServlet" method="post">
			<input type="hidden" name="starttime" value="<%=startTime%>" />
			<input type="hidden" name="quizid"
				   value=<%=wantedSummary.getQuizId()%> />
			<%
				for (Question currQuestion : quizQuestions) {
					String questionType = currQuestion.getQuestionType();
					if (questionType.equals("qresponse"))
					{
						%>
						<br>
						<%
							out.println(currQuestion.toString());
						%>
						<br> <input type="text" name="<%=currQuestion.getQuestionId()%>">
						<br>
						<%
					}
					else if (questionType.equals("fillblank"))
					{
						String unparsedQ = currQuestion.toString();
						String parsedQ = unparsedQ.replace("|", "_____");
						%>
						<%
							out.println(parsedQ);
						%>
						<br> <input type="text" name="<%=currQuestion.getQuestionId()%>">
						<br>
						<%
					}
					else if (questionType.equals("multiplechoice"))
					{
						MultipleChoice mcq = (MultipleChoice) currQuestion;
						out.println(currQuestion.toString());
						List<Answer> answerChoices = mcq.getAnswerChoices();

							for (Answer currAnswer : answerChoices)
							{
								%>
								<br> <input type="radio" name="<%=currQuestion.getQuestionId()%>"
											id="<%=currAnswer.getAnswerId()%>" value="<%=currAnswer.toString()%>">
								<%
									out.println(currAnswer.toString());
								%>
								<br>
								<%
							}
						%>

						<%
					}
					else if (questionType.equals("pictureresponse"))
					{
						PictureResponse currPictureResponse = (PictureResponse) currQuestion;
						String imageURL = currPictureResponse.getImageURL();
						String questionStr = currPictureResponse.toString();
						%>

						<img src="<%=imageURL%>" />
						<br>
						<%out.println(currPictureResponse.toString());%>
						<br>
						<input type="text" name="<%=currPictureResponse.getId() %>">
						<br>
						<%
							}
						%>
						<br>
						<%
				}
			%>
			<button type="submit" class="btn btn-success">Submit</button>
		</form>
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
