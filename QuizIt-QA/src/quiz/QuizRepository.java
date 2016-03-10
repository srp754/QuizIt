package quiz;

import db.DatabaseTasks;
import db.QuizPersistence;

import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 3/9/2016.
 */
public class QuizRepository
{
    public static int AddQuiz(QuizSummary qz) //this is not complete, needs to add more places + take in quiz
    {
        int quizId = AddQuizSummary(qz);
        AddQuizStats(quizId);
        return quizId;
    }

    public static int AddQuizSummary(QuizSummary qz)
    {
        return QuizPersistence.InsertQuizSummary(qz);
    }

    public static void AddQuizStats(int quizId)
    {
        QuizPersistence.InsertQuizStats(quizId);
    }

    public static int AddAttempt(QuizAttempt attempt)
    {
        return QuizPersistence.InsertAttempt(attempt);
        //needs to update activity + quizhistory
    }

    public static void RemoveQuiz(int quizId)
    {
        QuizPersistence.DeleteQuiz(quizId);
        QuizPersistence.DeleteQuizStats(quizId);
    }

    public static void RemoveAttempt(int attemptId)
    {
        QuizPersistence.DeleteAttempt(attemptId);
    }

    public static boolean QuizExists(int quizId)
    {
        return  DatabaseTasks.CheckIfRecordExistsWithParameterInt("QuizSummary", "QuizId", Integer.toString(quizId));
    }


    public static boolean AttemptExists(int attemptId)
    {
        return  DatabaseTasks.CheckIfRecordExistsWithParameterInt("QuizHistory", "AttemptId", Integer.toString(attemptId));
    }

    public static boolean QuizStatsExists(int quizId)
    {
        return  DatabaseTasks.CheckIfRecordExistsWithParameterInt("QuizStats", "QuizId", Integer.toString(quizId));
    }

    public static void AddScore(int attemptId)
    {

    }

    public static void UpdateQuizStats(QuizAttempt attempt)
    {

    }

    public static Quiz GetQuiz(int quizId)
    {
        return null;
    }

    public static List<Quiz> GetQuizzes(int userId)
    {
        return null;
    }

    public static Question GetQuestion(int questionId)
    {
        return null;
    }

    public static List<Question> GetQuestions(int quizId)
    {
        return null;
    }

    public static Answer GetAnswer(int answerId)
    {
        return null;
    }

    public static List<Answer> GetAnswers(int questionId)
    {
        return null;
    }
}
