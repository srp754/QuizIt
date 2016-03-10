package quiz;

public class Question
{
	private int questionId = 0;
	private int quizzId;
	private String questionType;
	private String questionText;

	public Question(int quizzId, String questionType, String questionText)
	{
		this.quizzId = quizzId;
		this.questionType = questionType;
		this.questionText = questionText;
	}

	public int getQuestionId() {return questionId;}
	public int getQuizId() {return quizzId;}
	public String getQuestionType() {return questionType;}
	public String getQuestionText() {return questionText;}
	public void setQuestionId(int id) {questionId = id;}
}
