package quiz;

import java.util.*;

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
	
	public String toString() {
		return String.join(" OR ", answerAlts); 
	}

	public boolean matches(String ans) {
		if (available && answerAlts.contains(ans)) {
			available = false;
			return true;
		}
		return false;
	}

	public int getId() {
		return answerId; 
	}
}
