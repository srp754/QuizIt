package quiz;
import java.util.*; 

public class QResponse implements Question {
	/*
	 * This is a standard text question with an appropriate text response. For
	 * example: Who was President during the Bay of Pigs fiasco?
	 */

	private String question;
	//private Answer answer;
	private int questionId; 
	private int answersNeeded;
	private boolean ordered;
	private List<Answer> possibleAnswers; 
	private String questionType; 
	
	public QResponse(String question, List<Answer> possibleAnswers, int answersNeeded, boolean ordered, int questionId) {
		this.questionType = "qresponse";
		this.question = question;
		this.questionId = questionId; 
		this.answersNeeded = answersNeeded;
		this.ordered = ordered;
		this.possibleAnswers = possibleAnswers; 
	}
	
	// TODO: probably remove this
	// Current implementation: accepts any capitalization i.e. washington or Washington
	public boolean checkAnswer(Answer userResponse) {
		String lowercaseResponse = userResponse.toString().toLowerCase();
		for(Answer currAnswer: possibleAnswers) {
			if(currAnswer.toString().toLowerCase().equals(lowercaseResponse)) {
				return true; 
			}
		}
		return false; 
	}
	
	// Checks answer given answer string as a parameter 
	public boolean checkAnswer(String userResponse) {
		String lowercaseResponse = userResponse.toLowerCase();
		if (ordered) {
			// TODO
		}
		for(Answer currAnswer: possibleAnswers) {
			if(currAnswer.isAvailable() && currAnswer.matches(lowercaseResponse)) {
				return true; 
			}
		}
		return false; 
	}
	
	public String toString() {
		return question; 
	}
	
	public int getId() {
		return questionId; 
	}
	
	public String getQuestionType() {
		return questionType; 
	}
	
}
