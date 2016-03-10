package quiz;

import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 3/9/2016.
 */
public class QuizRepository
{
    public void AddNewQuiz(Quiz qz)
    {
        //adds quiz to the database
    }

    public void RemoveQuiz(Quiz qz)
    {
        //adds quiz to the database
    }

    public void AddAttempt(QuizAttempt attempt)
    {

    }

    public void AddScore(int attemptId)
    {

    }

    public void UpdateQuizStats(QuizAttempt attempt)
    {

    }

    public Quiz GetQuiz(int quizId)
    {
        return null;
    }

    public List<Quiz> GetQuizzes(int userId)
    {
        return null;
    }

    public Question GetQuestion(int questionId)
    {
        return null;
    }

    public List<Question> GetQuestions(int quizId)
    {
        return null;
    }

    public Answer GetAnswer(int answerId)
    {
        return null;
    }

    public List<Answer> GetAnswers(int questionId)
    {
        return null;
    }
}
