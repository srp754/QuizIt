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
		// Handles scoring for text/string based answers
		// Iterate through the list of questions to return a score
		/*Map<String, String[]> paramMap = request.getParameterMap();
		Set<String> keyset = paramMap.keySet(); 
		for (String key: keyset) {
			System.out.println(key);
		}*/
		String idStr = request.getParameter("quizid");
		String parsedId = idStr.replaceAll("\\/", "");
		int quizid = Integer.parseInt(parsedId);
		List<Quiz> quizList = (ArrayList<Quiz>) getServletContext().getAttribute("quizlist");
		Quiz quiz = quizList.get(quizid);
		List<Question> questions = quiz.getQuestions();
		int numPossible = questions.size(); 
		int totalCorrect = 0; 
		for(Question currQuestion: questions) {
			String currId = Integer.toString(currQuestion.getId()); 
			String currAnswer = request.getParameter(currId);
			if(currQuestion.checkAnswer(currAnswer)) {
				totalCorrect++; 
			}
		}
		request.setAttribute("possible", numPossible);
		request.setAttribute("correct", totalCorrect); 
		RequestDispatcher rd = request.getRequestDispatcher("quizresults.jsp");
		rd.forward(request, response); 
		
	}

}