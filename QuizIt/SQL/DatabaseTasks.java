package QuizIt;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Alex on 3/2/2016.
 */
public class DatabaseTasks
{
    static String account = MyDBInfo.MYSQL_USERNAME;
    static String password = MyDBInfo.MYSQL_PASSWORD;
    static String server = MyDBInfo.MYSQL_DATABASE_SERVER;
    static String database = MyDBInfo.MYSQL_DATABASE_NAME;

    public static ResultSet GetResultSet(Connection con)
    {
        ResultSet rs = null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " + database);

            String query = String.format("Select UserDetail.UserName from UserFriends join UserDetail on UserFriends.FriendId = UserDetail.UserId WHERE UserFriends.UserId = 1;");
            rs = stmt.executeQuery(query);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return rs;
    }

    //Was just used to test handout example
    public static void main(String[] args)
    {

        try
        {
            Connection conNew = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = GetResultSet(conNew);
            while(rs.next())
            {
                String name = rs.getString("UserName");
                System.out.println("Friend Name: " + name);
            }
            conNew.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
