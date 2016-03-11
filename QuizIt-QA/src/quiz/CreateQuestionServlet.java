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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String questionType = request.getParameter("questiontype");
		String lastQuestionType = request.getParameter("lastquestiontype");
		List<Question> createQuizQuestions = (ArrayList<Question>) getServletContext().getAttribute("createquestions");

		if (lastQuestionType != null)
		{
			if (lastQuestionType.equals("qresponse") || lastQuestionType.equals("qresponse"))
			{
				List<String> listAnswerStrings = Answer.answerToList(request.getParameter("answer").toLowerCase());
				List<Answer> listAnswers = new ArrayList<>();

				String questionStr = request.getParameter("question"); //quizId overwritten later anyway
				Question question = new Question(0, lastQuestionType, questionStr);

				for(String answerText : listAnswerStrings)
					listAnswers.add(new Answer(0, lastQuestionType, answerText, true)); //questionId overwritten later anyway
				question.setAnswers(listAnswers);
				createQuizQuestions.add(question);
			}
			else if (lastQuestionType.equals("fillblank"))
			{
//                String questionStr = request.getParameter("question");
//                String answerStr = request.getParameter("answer");
//                Set<Answer> fbAnswers = new HashSet<Answer>();
//                FillBlankAnswer fba = new FillBlankAnswer(answerStr, 1);
//                fbAnswers.add(fba);
//                FillBlank fb = new FillBlank(questionStr, fbAnswers, 1);
//                createQuizQuestions.add(fb);
			}
			else if(lastQuestionType.equals("multiplechoice"))
			{
//			    String questionStr = request.getParameter("question");
//			    String answerStr = request.getParameter("answer");
//                List<Answer> choices = new ArrayList<Answer>();
//                MultipleChoiceAnswer correctChoice = new MultipleChoiceAnswer(answerStr, 0);
//                Set<Answer> correctAnswers = new HashSet<Answer>(Arrays.asList(correctChoice));
//                choices.add(correctChoice);
//                int wrongChoices = 3; // Magic number defined in js file/design (decides how many more choices to include)
//                for(int i = 0; i < wrongChoices; i++) {
//                    String wrongStr = request.getParameter("wrong"+i);
//                    choices.add(new MultipleChoiceAnswer(wrongStr, i+1));
//                }
//                MultipleChoice multipleChoiceQ = new MultipleChoice(questionStr, correctAnswers, choices, false, 1);
//                createQuizQuestions.add(multipleChoiceQ);
			}
			else if (lastQuestionType.equals("pictureresponse"))
			{
//                String questionStr = request.getParameter("question");
//                String answerStr = request.getParameter("answer");
//                String imageURL = request.getParameter("imageurl");
//                PictureResponseAnswer pictureAnswer = new PictureResponseAnswer(answerStr, 1);
//                PictureResponse pictureQuestion = new PictureResponse(questionStr, imageURL, pictureAnswer, 1);
//                createQuizQuestions.add(pictureQuestion);
			}
		}

		request.setAttribute("lastquestiontype", questionType);
		request.setAttribute("createquestions", createQuizQuestions);

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
