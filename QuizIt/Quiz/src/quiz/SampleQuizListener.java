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
    	QResponseAnswer qra1 = new QResponseAnswer("4", 0);
    	QResponse qr1 = new QResponse(q1, qra1, 1);
    	String q2 = "What is 3+3?"; 
    	QResponseAnswer qra2 = new QResponseAnswer("6", 1);
    	QResponse qr2 = new QResponse(q2, qra2, 2);
    	String q3 = "What is 4+4?"; 
    	QResponseAnswer qra3 = new QResponseAnswer("8", 2);
    	QResponse qr3 = new QResponse(q3, qra3, 3);
    	questions.add(qr1);
    	questions.add(qr2);
    	questions.add(qr3);  
    	Quiz qResponseQuiz = new Quiz(questions, 0);
    	quizList.add(qResponseQuiz);
    	
    	List<Question> questions2 = new ArrayList<Question>(); 
    	String fbq1 = "The | is the largest animal ever known to have lived.";
		FillBlankAnswer fba1 = new FillBlankAnswer("blue whale", 0);
		FillBlank fb1 = new FillBlank(fbq1, fba1, 0);
		questions2.add(fb1);
		Quiz fillBlankQuiz = new Quiz(questions2, 1);
		quizList.add(fillBlankQuiz);
    	arg0.getServletContext().setAttribute("quizlist", quizList); 
    	
    }
	
}
