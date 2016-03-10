package test;

import quiz.QuizAttempt;
import quiz.QuizRepository;
import quiz.QuizStats;
import quiz.QuizSummary;
import user.SocialRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex on 3/9/2016.
 */
public class QuizTests
{
    @org.junit.Test
    public void Should_Add_And_Delete_Quiz() //Just QuizSummary to start
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
       // int quizId = QuizRepository.AddQuizSummary(new QuizSummary("TestQuiz", "Test Quiz For Alex", 1, formatter.format(new Date())));
        int quizId = QuizRepository.AddQuizSummary(new QuizSummary("TestQuiz", "Test Quiz For Alex", 1, formatter.format(new Date()), 1));

        boolean doesQuizExist = QuizRepository.QuizExists(quizId);
        assertTrue(doesQuizExist);

        QuizRepository.RemoveQuiz(quizId);
        doesQuizExist = QuizRepository.QuizExists(quizId);
        assertFalse(doesQuizExist);
    }

    @org.junit.Test
    public void Should_Add_And_Delete_Attempt()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int attemptId = QuizRepository.AddAttempt(new QuizAttempt(1, 1, 10, 20, 20000, formatter.format(new Date())));

        boolean doesAttemptExist = QuizRepository.AttemptExists(attemptId);
        assertTrue(doesAttemptExist);

        QuizRepository.RemoveAttempt(attemptId);
        doesAttemptExist = QuizRepository.AttemptExists(attemptId);
        assertFalse(doesAttemptExist);
    }

    @org.junit.Test
    public void Should_Add_And_Delete_Quiz_With_Stats()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        int quizId = QuizRepository.AddQuizHeader(new QuizSummary("TestQuiz", "Test Quiz For Alex", 1, formatter.format(new Date())));

        boolean doesQuizExist = QuizRepository.QuizExists(quizId);
        assertTrue(doesQuizExist);
        boolean doesQuizStatsExist = QuizRepository.QuizStatsExists(quizId);
        assertTrue(doesQuizStatsExist);

        QuizRepository.RemoveQuiz(quizId);

        doesQuizExist = QuizRepository.QuizExists(quizId);
        assertFalse(doesQuizExist);
        doesQuizStatsExist = QuizRepository.QuizStatsExists(quizId);
        assertFalse(doesQuizStatsExist);
    }

    @org.junit.Test
    public void Should_Update_Quiz_Statistics()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int quizId = QuizRepository.AddQuizHeader(new QuizSummary("TestQuiz", "Test Quiz For Alex", 1, formatter.format(new Date())));

        QuizAttempt attempt = new QuizAttempt(quizId, 1, 10, 20, 20000, formatter.format(new Date()));
        int attemptId = QuizRepository.AddAttempt(attempt);
        QuizRepository.UpdateQuizStats(attempt);

        QuizStats qStats = QuizRepository.GetQuizStats(quizId);
        assertEquals(qStats.getQuizAttempts(), 1);
        assertEquals(qStats.getUserAttempts(), 1);
        assertEquals(qStats.getSumActualScores(), 10);
        assertEquals(qStats.getSumPossibleScores(), 20);

        QuizRepository.RemoveQuiz(quizId);
        QuizRepository.RemoveAttempt(attemptId);
    }

    @org.junit.Test
    public void Should_Update_Quiz_Statistics_For_Same_User_Two_Attempts()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int quizId = QuizRepository.AddQuizHeader(new QuizSummary("TestQuiz", "Test Quiz For Alex", 1, formatter.format(new Date())));

        QuizAttempt attempt = new QuizAttempt(quizId, 1, 10, 20, 20000, formatter.format(new Date()));
        int attemptId = QuizRepository.AddAttempt(attempt);
        QuizRepository.UpdateQuizStats(attempt);

        QuizAttempt attempt2 = new QuizAttempt(quizId, 1, 15, 20, 20000, formatter.format(new Date()));
        int attemptId2 = QuizRepository.AddAttempt(attempt2);
        QuizRepository.UpdateQuizStats(attempt2);


        QuizStats qStats = QuizRepository.GetQuizStats(quizId);
        assertEquals(qStats.getQuizAttempts(), 2);
        assertEquals(qStats.getUserAttempts(), 1);
        assertEquals(qStats.getSumActualScores(), 25);
        assertEquals(qStats.getSumPossibleScores(), 40);

        QuizRepository.RemoveQuiz(quizId);
        QuizRepository.RemoveAttempt(attemptId);
        QuizRepository.RemoveAttempt(attemptId2);
    }
}
