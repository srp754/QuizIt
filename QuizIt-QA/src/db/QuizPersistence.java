package db;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import quiz.*;
import user.Activity;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 3/9/2016.
 */
public class QuizPersistence
{
    public static int InsertQuizSummary(QuizSummary qz)
    {
        int quizId = 0;
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO QuizSummary VALUES(");
            sb.append("null,");
            sb.append("'" + qz.getQuizName()+ "',");
            sb.append("'" + qz.getQuizDescription()+ "',");
            sb.append(qz.getCreatorId() + ",");
            sb.append("'" + formatter.format(new Date()) + "');");
            DatabaseTasks.ExecuteUpdate(sb.toString());

            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            String query = String.format("Select * from QuizSummary WHERE %1$s = %2$s order by QuizId desc LIMIT 1;", "CreatorId", qz.getCreatorId());
            ResultSet rs = DatabaseTasks.GetResultSet(con, query);
            rs.next(); // exactly one result so allowed
            quizId = rs.getInt(1);

            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return quizId;
    }

    public static void InsertQuestions(List<Question> questions)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO QuizQuestions VALUES ");

        for(int i =0; i < questions.size(); i++)
        {
            Question question = questions.get(i);
            sb.append(" (");
            sb.append("null,");
            sb.append(question.getQuizId() + ",");
            sb.append("'" + question.getQuestionType() + "',");
            sb.append("'" + question.getQuestionText() + "')");

            if(i!=questions.size()-1)
                sb.append(",");
        }
        sb.append(";");
        DatabaseTasks.ExecuteUpdate(sb.toString());
    }

    public static void InsertAnswers(List<Answer> answers)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO QuizAnswers VALUES ");

        for(int i =0; i < answers.size(); i++)
        {
            Answer answer = answers.get(i);
            sb.append(" (");
            sb.append("null,");
            sb.append(answer.getQuestionId() + ",");
            sb.append("'" + answer.getAnswerType() + "',");
            sb.append("'" + answer.getAnswerText() + "',");
            sb.append(answer.getAnswerCorrectFlag() + ")");

            if(i!=answers.size()-1)
                sb.append(",");
        }
        sb.append(";");
        DatabaseTasks.ExecuteUpdate(sb.toString());
    }

    public static int InsertAttempt(QuizAttempt attempt)
    {
        int attemptId = 0;
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO QuizHistory VALUES(");
            sb.append("null,");
            sb.append(attempt.getQuizId() + ",");
            sb.append(attempt.getUserId() + ",");
            sb.append(attempt.getAttemptScore() + ",");
            sb.append(attempt.getAttemptPossible() + ",");
            sb.append(attempt.getElapsedTime() + ",");
            sb.append("'" + formatter.format(new Date()) + "');");
            DatabaseTasks.ExecuteUpdate(sb.toString());

            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            String query = String.format("Select * from QuizHistory WHERE %1$s = %2$s order by AttemptId desc LIMIT 1;", "UserId", attempt.getUserId());
            ResultSet rs = DatabaseTasks.GetResultSet(con, query);
            rs.next(); // exactly one result so allowed
            attemptId = rs.getInt(1);

            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return attemptId;
    }

    public static void InsertQuizStats(int quizId)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO QuizStats VALUES(");
        sb.append(quizId + ",");
        sb.append("0,0,0,0);");
        DatabaseTasks.ExecuteUpdate(sb.toString());
    }

    public static void UpdateQuizStats(QuizAttempt attempt)
    {
        boolean isUniqueUser = IsUniqueAttempt(attempt.getUserId(), attempt.getQuizId());
        int userAttempt = isUniqueUser ? 1 : 0;

        StringBuilder sb = new StringBuilder();
        sb.append("Update QuizStats SET ");
        sb.append("QuizAttempts = QuizAttempts + 1, ");
        sb.append("SumActualScore = SumActualScore + " + attempt.getAttemptScore() + ", ");
        sb.append("SumPossibleScore = SumPossibleScore + " + attempt.getAttemptPossible() + ", ");
        sb.append("UserAttempts = UserAttempts + " + userAttempt);
        sb.append(" WHERE QuizId = " + attempt.getQuizId() + ";");

        DatabaseTasks.ExecuteUpdate(sb.toString());
    }

    public static boolean IsUniqueAttempt(int userId, int quizId)
    {
        String query = String.format("Select * from %1$s WHERE %2$s = %3$s and %4$s = %5$s;","QuizHistory", "UserId", Integer.toString(userId), "QuizId", Integer.toString(quizId));
        boolean doesRecordExist = DatabaseTasks.CheckIfMultipleRecordsExists(query);
        return !doesRecordExist;
    }

    public static void DeleteQuiz(int quizId)
    {
        String query = String.format("Delete from QuizSummary WHERE QuizId = %1$s;", quizId);
        DatabaseTasks.ExecuteUpdate(query);
    }

    public static void DeleteQuestions(int quizId)
    {
        String query = String.format("Delete from QuizQuestions WHERE QuizId = %1$s;", quizId);
        DatabaseTasks.ExecuteUpdate(query);
    }

    public static void DeleteAnswers(int questionId)
    {
        String query = String.format("Delete from QuizAnswers WHERE QuestionId= %1$s;", questionId);
        DatabaseTasks.ExecuteUpdate(query);
    }

    public static void DeleteAttempt(int quizId)
    {
        String query = String.format("Delete from QuizHistory WHERE AttemptId = %1$s;", quizId);
        DatabaseTasks.ExecuteUpdate(query);
    }

    public static void DeleteQuizStats(int quizId)
    {
        String query = String.format("Delete from QuizStats WHERE QuizId = %1$s;", quizId);
        DatabaseTasks.ExecuteUpdate(query);
    }

    public static QuizStats GetQuizStats(int quizId)
    {
        QuizStats qStats = null;

        try {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = DatabaseTasks.GetResultSetWithParameter(con, "QuizStats", "QuizId", "" + quizId + "");

            if(rs.next())
            {
                int quizAttempts = Integer.parseInt(rs.getString("QuizAttempts"));
                int totalScore = Integer.parseInt(rs.getString("SumActualScore"));
                int totalPossible = Integer.parseInt(rs.getString("SumPossibleScore"));
                int userAttempts = Integer.parseInt(rs.getString("UserAttempts"));

                qStats = new QuizStats(quizId, quizAttempts, totalScore, totalPossible, userAttempts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return qStats;
    }

    public static List<Activity> GetCreatedQuizzes()
    {
        List<Activity> quizList = new ArrayList<>();

        try {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = DatabaseTasks.GetResultSet(con, "*", "UserActivity");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            while(rs.next())
            {
                if(rs.getString("ActivityType").equals("QuizCreated")) {
                    Date date = null;
                    try {
                        date = formatter.parse(rs.getString("ActivityDate"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String type = rs.getString("ActivityType");
                    Activity activity = new Activity(type, formatter.format(date));
                    quizList.add(activity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quizList;
    }

    public static List<Activity> GetTakenQuizzes()
    {
        List<Activity> quizList = new ArrayList<>();

        try
        {
            Connection con = (Connection) DriverManager.getConnection
                    ("jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME, MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = DatabaseTasks.GetResultSet(con, "*", "UserActivity");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            while (rs.next())
            {
                if (rs.getString("ActivityType").equals("QuizTaken"))
                {
                    Date date = null;
                    try
                    {
                        date = formatter.parse(rs.getString("ActivityDate"));
                    } catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                    String type = rs.getString("ActivityType");
                    Activity activity = new Activity(type, formatter.format(date));
                    quizList.add(activity);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return quizList;
    }

    public static List<Question> GetQuestions(int quizId)
    {
        List<Question> foundQuestions = new ArrayList<>();

        int questionId = 0;
        int quizzId = 0;
        String questionType = "notdefined";
        String questionText = "notdefined";

        ResultSet rs;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);

            String query = String.format("Select * from QuizQuestions where QuizId = %1$s;", quizId);
            rs = stmt.executeQuery(query);
            while(rs.next())
            {
                questionId = Integer.parseInt(rs.getString("QuestionId"));
                quizzId = Integer.parseInt(rs.getString("QuizId"));
                questionType = rs.getString("QuestionType");
                questionText =  rs.getString("QuestionText");
                foundQuestions.add(new Question(questionId, quizzId, questionType, questionText));
            }
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return foundQuestions;
    }

    public static List<Answer> GetAnswers(int questionId)
    {
        List<Answer> foundAnswers = new ArrayList<>();

        int answerId = 0;
        String answerType = "notdefined";
        String answerText = "notdefined";
        boolean answerCorrectFlag = false;

        ResultSet rs;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);

            String query = String.format("Select * from QuizAnswers where QuestionId = %1$s;", questionId);
            rs = stmt.executeQuery(query);
            while(rs.next())
            {
                answerId = Integer.parseInt(rs.getString("AnswerId"));
                answerType = rs.getString("AnswerType");
                answerText =  rs.getString("AnswerText");
                String answerCorrectFlagValue = rs.getString("AnswerCorrectFlag");
                answerCorrectFlag = answerCorrectFlagValue.equals("1");
                foundAnswers.add(new Answer(answerId,questionId,answerType,answerText,answerCorrectFlag));
            }
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return foundAnswers;
    }
}
