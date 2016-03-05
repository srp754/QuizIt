package quiz;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.*; 
/**
 * Application Lifecycle Listener implementation class SampleQuizListener
 *
 */
@WebListener
public class SampleQuizListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public SampleQuizListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  {
    	List<Quiz> quizList = new ArrayList<Quiz>(); 
    	
    	List<Question> questions = new ArrayList<Question>();
    	String q1 = "What is 2+2?"; 
    	Set<Answer> answers1 = new HashSet<Answer>();
    	QResponseAnswer qra1 = new QResponseAnswer("4", 0);
    	answers1.add(qra1);
    	QResponse qr1 = new QResponse(q1, answers1, 1);
    	String q2 = "What is 3+3?"; 
    	Set<Answer> answers2 = new HashSet<Answer>();
    	QResponseAnswer qra2 = new QResponseAnswer("6", 1);
    	answers2.add(qra2);
    	QResponse qr2 = new QResponse(q2, answers2, 2);
    	String q3 = "What is 4+4?"; 
    	Set<Answer> answers3 = new HashSet<Answer>();
    	QResponseAnswer qra3 = new QResponseAnswer("8", 2);
    	answers3.add(qra3);
    	QResponse qr3 = new QResponse(q3, answers3, 3);
    	questions.add(qr1);
    	questions.add(qr2);
    	questions.add(qr3);  
    	Quiz qResponseQuiz = new Quiz(questions, 0);
    	quizList.add(qResponseQuiz);
    	
    	List<Question> questions2 = new ArrayList<Question>(); 
    	String fbq1 = "The | is the largest animal ever known to have lived.";
		FillBlankAnswer fba1 = new FillBlankAnswer("blue whale", 0);
		Set<Answer> fbAnswers1 = new HashSet<Answer>();
		fbAnswers1.add(fba1);
		FillBlank fb1 = new FillBlank(fbq1, fbAnswers1, 0);
		questions2.add(fb1);
		Quiz fillBlankQuiz = new Quiz(questions2, 1);
		quizList.add(fillBlankQuiz);
		
		List<Question> multipleChoiceQuestions = new ArrayList<Question>(); 
		String multipleChoiceQ = "What year is John Carlo?";
		List<Answer> answerChoices = new ArrayList<Answer>(); 
		MultipleChoiceAnswer correctChoice = new MultipleChoiceAnswer("Junior", 0);
		MultipleChoiceAnswer wrongChoice = new MultipleChoiceAnswer("Sophomore", 1);
		MultipleChoiceAnswer wrongChoice2 = new MultipleChoiceAnswer("Freshman", 2);
		answerChoices.add(correctChoice);
		answerChoices.add(wrongChoice);
		answerChoices.add(wrongChoice2);
		MultipleChoice mc1 = new MultipleChoice(multipleChoiceQ, correctChoice, answerChoices, 2);
		multipleChoiceQuestions.add(mc1);
		Quiz multipleChoiceQuiz = new Quiz(multipleChoiceQuestions, 2);
		quizList.add(multipleChoiceQuiz); 
		
    	arg0.getServletContext().setAttribute("quizlist", quizList); 
    	
    }
	
}
