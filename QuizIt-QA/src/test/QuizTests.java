package test;

import quiz.QuizAttempt;
import quiz.QuizRepository;
import quiz.QuizSummary;
import user.SocialRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        int quizId = QuizRepository.AddQuizSummary(new QuizSummary("TestQuiz", "Test Quiz For Alex", 1, formatter.format(new Date())));

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
        int quizId = QuizRepository.AddQuiz(new QuizSummary("TestQuiz", "Test Quiz For Alex", 1, formatter.format(new Date())));

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
}
