package quiz;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
		String numQuestionsStr = request.getParameter("numquestions");
		String numQuestionsParsedStr = numQuestionsStr.replaceAll("\\/", "");
		int numQuestions = Integer.parseInt(numQuestionsParsedStr); 
		List<Question> questionList = new ArrayList<Question>();
		
		// Should redirect to create quiz if no questions were found 
		/*
		if (numQuestions == 0) {
			String noQuestions = "Try submitting a quiz with some questions"; 
			request.setAttribute("noquestions", noQuestions);
			RequestDispatcher rd = request.getRequestDispatcher("createquiz.jsp");
			rd.forward(request, response);
			return; 
		} */
		
		if (numQuestions != 0) { // TODO: Implement multiple answer choices, currently only takes in one possible answer for a question
			for(int i = 1; i < numQuestions; i++) {
				String questionStr = request.getParameter("question"+i); 
				String answerStr = request.getParameter("answer"+i);
				Set<Answer> possibleAnswers = new HashSet<Answer>(); 
				// Placeholder code: currently assumes qresponse, later will need to find out what question type then react accordingly
				// TODO: Decide how to assign answer id's, for now we'll use "i"
				QResponseAnswer qra = new QResponseAnswer(answerStr, i); 
				possibleAnswers.add(qra); 
				QResponse newQuestion = new QResponse(questionStr, possibleAnswers, i); 
				questionList.add(newQuestion); 
			}
		}
		int placeholderQuizId = quizTable.size(); // Will have to get the auto-increment quiz id from database
		int placeholderCreatorId = 4; // Replace this with the userId that's currently logged in 
		Quiz newQuiz = new Quiz(questionList, placeholderQuizId);
		quizTable.add(newQuiz);
		
		//Now we handle the Quiz Summary details 
		String quizName = request.getParameter("quizname"); 
		String quizDescription = request.getParameter("quizdescription"); 
		List<QuizSummary> quizSummaryTable = (ArrayList<QuizSummary>) getServletContext().getAttribute("quizsummary");
		QuizSummary newQuizSummary = new QuizSummary(placeholderQuizId, quizName, quizDescription, placeholderCreatorId); 
		quizSummaryTable.add(newQuizSummary);
		
		//Now we hand the Quiz Statistics details 
		List<QuizStats> quizStatsTable = (ArrayList<QuizStats>) getServletContext().getAttribute("quizstats");
		QuizStats newQuizStats = new QuizStats(placeholderQuizId);
		quizStatsTable.add(newQuizStats); 
		
		//TODO: May want to add a blurb to whatever quiz homepage that the quiz was successfully created
		RequestDispatcher rd = request.getRequestDispatcher("quizhomepage.jsp");
		rd.forward(request, response);
	}

}
