package quiz;

import db.DatabaseTasks;
import db.QuizPersistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 3/9/2016.
 */
public class QuizRepository
{
    public static int AddQuizHeader(QuizSummary qz) //this is not complete, needs to add more places + take in quiz
    {
        int quizId = AddQuizSummary(qz);
        AddQuizStats(quizId);
        return quizId;
    }

    public static void AddQuizContent(Quiz qz)
    {

    }

    public static void AddQuestions(List<Question> questions)
    {
        QuizPersistence.InsertQuestions(questions);
    }

    public static void AddAnswers(List<Answer> answers)
    {
        QuizPersistence.InsertAnswers(answers);
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

    public static void RemoveQuestions(int quizId)
    {
        QuizPersistence.DeleteQuestions(quizId);
    }

    public static void RemoveAnswers(int questionId)
    {
        QuizPersistence.DeleteAnswers(questionId);
    }

    public static void RemoveAttempt(int attemptId)
    {
        QuizPersistence.DeleteAttempt(attemptId);
    }

    public static boolean QuizExists(int quizId)
    {
        return DatabaseTasks.CheckIfRecordExistsWithParameterInt("QuizSummary", "QuizId", Integer.toString(quizId));
    }

    public static boolean QuestionsExist(int quizId)
    {
        return DatabaseTasks.CheckIfRecordExistsWithParameterInt("QuizSummary", "QuizId", Integer.toString(quizId));
    }

    public static boolean AttemptExists(int attemptId)
    {
        return DatabaseTasks.CheckIfRecordExistsWithParameterInt("QuizHistory", "AttemptId", Integer.toString(attemptId));
    }

    public static boolean QuizStatsExists(int quizId)
    {
        return DatabaseTasks.CheckIfRecordExistsWithParameterInt("QuizStats", "QuizId", Integer.toString(quizId));
    }

    public static void UpdateQuizStats(QuizAttempt attempt)
    {
        QuizPersistence.UpdateQuizStats(attempt);
    }

    public static QuizStats GetQuizStats(int quizId)
    {
        QuizStats qStats = null;

        if(QuizExists(quizId))
            qStats = db.QuizPersistence.GetQuizStats(quizId);

        return qStats;
    }

    public static Quiz GetQuiz(int userId)
    {
        return null;
    }

    public static List<Question> GetQuestions(int quizId)
    {
        List<Question> questions = db.QuizPersistence.GetQuestions(quizId);
        return questions;
    }

    public static List<Answer> GetAnswers(int questionId)
    {
        List<Answer> answers = db.QuizPersistence.GetAnswers(questionId);
        return answers;
    }

    public static List<Answer> GetAllAnswersForQuiz(int quizId)
    {
//        List<Answer> answers = db.QuizPersistence.GetAllAnswersForQuiz(quizId);
        return null;
    }
}
