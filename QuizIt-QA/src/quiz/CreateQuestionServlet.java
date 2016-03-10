package quiz;

import user.*;
import java.util.*;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateQuestionServlet
 */
@WebServlet("/CreateQuestionServlet")
public class CreateQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateQuestionServlet() {
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
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String questionType = request.getParameter("questiontype");
		 System.out.println("Question type: "+questionType);
		String lastQuestionType = request.getParameter("lastquestiontype");
		 System.out.println("Last question type: "+lastQuestionType);
		if (lastQuestionType != null) { // Process the fields
		    System.out.println("Adding question");
			List<Question> createQuizQuestions = (ArrayList<Question>) getServletContext().getAttribute("createquestions");
			int placeholderId = createQuizQuestions.size();
			// Access persistent list of questions that we add to
			if (lastQuestionType.equals("qresponse")) {
				String questionStr = request.getParameter("question");
				String answerStr = request.getParameter("answer");
				//System.out.println("Question: "+questionStr);
				//System.out.println("Answer: "+answerStr);
				Set<Answer> possibleAnswers = new HashSet<Answer>();
				QResponseAnswer qra = new QResponseAnswer(answerStr, placeholderId);
				possibleAnswers.add(qra);
				QResponse newQuestion = new QResponse(questionStr, possibleAnswers, placeholderId);
				createQuizQuestions.add(newQuestion);
			} else if (lastQuestionType.equals("fillblank")) {
                String questionStr = request.getParameter("question");
                String answerStr = request.getParameter("answer");
                Set<Answer> fbAnswers = new HashSet<Answer>();
                FillBlankAnswer fba = new FillBlankAnswer(answerStr, placeholderId);
                fbAnswers.add(fba);
                FillBlank fb = new FillBlank(questionStr, fbAnswers, placeholderId);
                createQuizQuestions.add(fb);
			} else if (lastQuestionType.equals("multiplechoice")) {
			    String questionStr = request.getParameter("question");
			    String answerStr = request.getParameter("answer");
                List<Answer> choices = new ArrayList<Answer>();
                MultipleChoiceAnswer correctChoice = new MultipleChoiceAnswer(answerStr, 0);
                choices.add(correctChoice);
                int wrongChoices = 3; // Magic number defined in js file/design (decides how many more choices to include)
                for(int i = 0; i < wrongChoices; i++) {
                    String wrongStr = request.getParameter("wrong"+i);
                    choices.add(new MultipleChoiceAnswer(wrongStr, i+1));
                }
                MultipleChoice multipleChoiceQ = new MultipleChoice(questionStr, correctChoice, choices, placeholderId);
                createQuizQuestions.add(multipleChoiceQ);
			} else if (lastQuestionType.equals("pictureresponse")) {
                String questionStr = request.getParameter("question");
                String answerStr = request.getParameter("answer");
                String imageURL = request.getParameter("imageurl");
                PictureResponseAnswer pictureAnswer = new PictureResponseAnswer(answerStr, placeholderId);
                PictureResponse pictureQuestion = new PictureResponse(questionStr, imageURL, pictureAnswer, placeholderId);
                createQuizQuestions.add(pictureQuestion);
			}
		}
		// lastQuestionType = questionType;
		request.setAttribute("lastquestiontype", questionType);

		String lastQuestionNumber = request.getParameter("questionnumber");
		String lastqparsed = lastQuestionNumber.replaceAll("\\/", "");
		int nextQuestionNumber = Integer.parseInt(lastqparsed) + 1;
		request.setAttribute("questionnumber", nextQuestionNumber);

		String addAnotherQuestion = request.getParameter("add");
		String completeQuiz = request.getParameter("complete");
		if (addAnotherQuestion != null) {
			RequestDispatcher rd = request.getRequestDispatcher("quiz/quizquestion.jsp");
			rd.forward(request, response);
		} else if (completeQuiz != null) {
			RequestDispatcher rd = request.getRequestDispatcher("quiz/finishcreatequiz.jsp");
			rd.forward(request, response);
		}

	}

}
