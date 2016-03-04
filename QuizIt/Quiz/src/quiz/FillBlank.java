package quiz;

public class FillBlank implements Question{
	/* Pretty much identical to QResponse
	 *
	 * TODO: One design choice we can make is how to represent the question string. 
	 * Could have it as two separate strings and separate on the blank or as one string. 
	*/
	private String question; 
	private Answer answer; 
	private int questionId; 
	
	public FillBlank(String question, Answer answer, int questionId) {
		this.question = question; 
		this.answer = answer;
		this.questionId = questionId; 
	}
	
	public Answer getAnswer() {
		return answer;
	}  
	
	public boolean checkAnswer(Answer userResponse) {
		String lowercaseResponse = userResponse.toString().toLowerCase();
		String lowercaseAnswer = answer.toString().toLowerCase();
		if (lowercaseAnswer.equals(lowercaseResponse)) {
			return true; 
		}
		return false; 
	}
	
	public boolean checkAnswer(String userResponse) {
		String lowercaseResponse = userResponse.toLowerCase();
		String lowercaseAnswer = answer.toString().toLowerCase();
		if (lowercaseAnswer.equals(lowercaseResponse)) {
			return true; 
		}
		return false; 
	}
	
	public String toString() {
		return question; 
	}
	
	public int getId() {
		return questionId; 
	}
}
