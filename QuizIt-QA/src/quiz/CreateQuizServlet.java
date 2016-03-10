package quiz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Servlet implementation class CreateQuizServlet
 */
@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//First handle the creation of the quiz object
		List<Quiz> quizTable = (ArrayList<Quiz>) getServletContext().getAttribute("quizlist"); // Add the quiz to the quizTable
		List<Question> createQuizQuestions = (ArrayList<Question>) getServletContext().getAttribute("createquestions");
		//List<Question> questionList = new ArrayList<Question>();
		
		// Should redirect to create quiz if no questions were found 
		/*
		if (numQuestions == 0) {
			String noQuestions = "Try submitting a quiz with some questions"; 
			request.setAttribute("noquestions", noQuestions);
			RequestDispatcher rd = request.getRequestDispatcher("createquiz.jsp");
			rd.forward(request, response);
			return; 
		} */

		int placeholderQuizId = quizTable.size(); // Will have to get the auto-increment quiz id from database
		int placeholderCreatorId = 4; // Replace this with the userId that's currently logged in 
		Quiz newQuiz = new Quiz(createQuizQuestions, placeholderQuizId);
		quizTable.add(newQuiz);
		
		//Now we handle the Quiz Summary details 
		String quizName = request.getParameter("quizname"); 
		String quizDescription = request.getParameter("quizdescription"); 
		List<QuizSummary> quizSummaryTable = (ArrayList<QuizSummary>) getServletContext().getAttribute("quizsummary");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		//QuizSummary newQuizSummary = new QuizSummary(quizName, quizDescription, placeholderCreatorId,formatter.format(new Date()) );
		QuizSummary newQuizSummary = new QuizSummary(quizName, quizDescription, placeholderCreatorId,formatter.format(new Date()), placeholderQuizId);
		quizSummaryTable.add(newQuizSummary);
		
		//Now we hand the Quiz Statistics details 
		List<QuizStats> quizStatsTable = (ArrayList<QuizStats>) getServletContext().getAttribute("quizstats");
		QuizStats newQuizStats = new QuizStats(placeholderQuizId);
		quizStatsTable.add(newQuizStats); 

		createQuizQuestions = new ArrayList<Question>(); // Reset the quiz questions
		getServletContext().setAttribute("createquestions", createQuizQuestions);
		//TODO: May want to add a blurb to whatever quiz homepage that the quiz was successfully created
		RequestDispatcher rd = request.getRequestDispatcher("/quiz/quizhomepage.jsp");
		rd.forward(request, response);
	}

}
