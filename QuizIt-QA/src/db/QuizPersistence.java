package db;

import com.mysql.jdbc.Connection;
import user.Activity;
import user.MyDBInfo;

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
    }
}
