package db;

import com.mysql.jdbc.Connection;
<<<<<<< HEAD
import user.Activity;
import user.MyDBInfo;
=======
import quiz.QuizAttempt;
import quiz.QuizSummary;
>>>>>>> Ashavsky/master

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< HEAD
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
=======
import java.text.SimpleDateFormat;
import java.util.Date;
>>>>>>> Ashavsky/master

/**
 * Created by Alex on 3/9/2016.
 */
public class QuizPersistence
{
<<<<<<< HEAD

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

        try {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = DatabaseTasks.GetResultSet(con, "*", "UserActivity");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            while(rs.next())
            {
                if(rs.getString("ActivityType").equals("QuizTaken")) {
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
=======
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

    public static void DeleteQuiz(int quizId)
    {
        String query = String.format("Delete from QuizSummary WHERE QuizId = %1$s;", quizId);
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
>>>>>>> Ashavsky/master
    }
}
