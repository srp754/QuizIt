package quiz;
import java.util.*;
public class MultipleChoice implements Question{
	
	// Multiple choice question, choices should be fairly important 
	// TODO: Think about how choices should be stored: 
	// TODO: Decide if choices should be included in Question, Answer, or not at all 
	private List<Answer> answerChoices;
	private String question; 
	private Set<Answer> correctAnswers; 
	private int questionId; 
	private String questionType; 
	private boolean multi;
	
	public MultipleChoice(String question, Set<Answer> correctAnswers, List<Answer> answerChoices, boolean multi, int questionId) {
		this.questionType = "multiplechoice"; 
		this.question = question; 
		this.correctAnswers = correctAnswers; 
		this.answerChoices = answerChoices; 
		this.questionId = questionId; 
		this.multi = multi;
	}
	
	//TODO probably remove this
	public Set<Answer> getAnswers() {
		return correctAnswers; 
	}
	
	// TODO probably remove this
	public boolean checkAnswer(Answer userResponse) {
		// Check if userResponse is in choices  
		String urStr = userResponse.toString(); 
		return correctAnswers.contains(urStr);
	}
	
	//Temporary implementation
	public boolean checkAnswer(String userResponse) {
		return correctAnswers.contains(urStr);
	}
	
	//Override of toString
	public String toString() {
		return question; 
	}
	
	public int getId() {
		return questionId; 
	}
	
	public List<Answer> getAnswerChoices() {
		return answerChoices; 
	}
	
	public String getQuestionType() {
		return questionType; 
	}
	
}
