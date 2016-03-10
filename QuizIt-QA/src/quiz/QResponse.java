package quiz;
import java.util.*; 

public class QResponse extends Question {
	/*
	 * This is a standard text question with an appropriate text response. For
	 * example: Who was President during the Bay of Pigs fiasco?
	 */

	private int questionId;
	private int answersNeeded;
	private boolean ordered;
	private List<Answer> possibleAnswers;
	
	public QResponse(String question, List<Answer> possibleAnswers, int answersNeeded, boolean ordered, int questionId) {
		super(1, "qresponse", question);
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
		// TODO decide whether to use Answer or QResponseAnswer
		for(Answer currAnswer: possibleAnswers) {
			if(((QResponseAnswer) currAnswer).matches(lowercaseResponse)) {
				return true; 
			}
		}
		return false; 
	}
	
	public String toString() {
		return super.getQuestionText();
	}
	
	public int getId() {
		return questionId; 
	}
	
	public String getQuestionType() {
		return super.getQuestionType();
	}
	
}
