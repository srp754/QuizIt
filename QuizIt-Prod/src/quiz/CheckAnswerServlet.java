package quiz;
import user.*;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Servlet implementation class CheckAnswer
 */
@WebServlet("/CheckAnswerServlet")
public class CheckAnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckAnswerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		IUserRepository user = (UserRepository) session.getAttribute("user");
		int userId = user.getUserId();

		String idStr = request.getParameter("quizid");
		String parsedId = idStr.replaceAll("\\/", "");
		int quizid = Integer.parseInt(parsedId);

		Quiz quiz = QuizRepository.GetQuiz(quizid);
		List<Question> questions = QuizRepository.GetQuestions(quizid);
		int numPossible = questions.size();
		int totalCorrect = 0;
		// Grading Logic needs to be rewritten
		for (Question currQuestion : questions) {
			String currId = Integer.toString(currQuestion.getQuestionId());
			String currAnswer = request.getParameter(currId);
			System.out.println("currAnswer: "+currAnswer);
			if (currAnswer != null && QuizRepository.CheckAnswer(currQuestion.getQuestionId(), currAnswer)) {
				totalCorrect++;
			}

		}

		// Create new quiz attempt
		String startTimeStr = request.getParameter("starttime");
		String endTimeStr = request.getParameter("endtime");
		long startTime = Long.parseLong(startTimeStr);
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime-startTime;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateCreated = formatter.format(new Date());
		QuizAttempt newAttempt = new QuizAttempt(quizid, userId, totalCorrect, numPossible, elapsedTime, dateCreated);
		// Save it to the datebase
		QuizRepository.AddAttempt(newAttempt);
		// Update the QuizStats accordingly
		QuizRepository.UpdateQuizStats(newAttempt);
		String timeTaken =  ( new SimpleDateFormat("mm:ss:SSS")).format(new Date(elapsedTime)).toString();
		request.setAttribute("timeelapsed", timeTaken) ;
		request.setAttribute ("possible", numPossible);
		request.setAttribute("correct", totalCorrect);
		RequestDispatcher rd = request.getRequestDispatcher("quiz/quizresults.jsp");
		rd.forward(request, response);

	}

}
