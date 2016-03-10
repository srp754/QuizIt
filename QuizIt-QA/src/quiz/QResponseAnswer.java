package quiz;

public class QResponseAnswer implements Answer{
	// The answer for this question type is a String 
	
	private Set<String> answerAlts; 
	private int answerId; 
	private boolean available;
	
	public QResponseAnswer(Set<String> answerAlternatives, int answerId) {
		this.answerAlts = answerAlternatives; 
		this.answerId = answerId; 
		this.available = true;
	}
	
	// TODO: can probably remove this
	public String toString() {
		return String.join(" OR ", answerAlts); 
	}
	
	public boolean isAvailable() {
		return available;
	}

	// TODO: move to Answer.java?
	public boolean matches(String ans) {
		if (answerAlts.contains(ans)) {
			available = false;
			return true;
		}
		return false;
	}

	public int getId() {
		return answerId; 
	}
}
