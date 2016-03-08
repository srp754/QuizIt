package user;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 3/2/2016.
 */
public class DatabaseTasks
{
    public static void InsertAchievement(int userId, String achievementName, String achievementDesc)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);
            stmt.executeQuery("SET @@auto_increment_increment=1; ");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String tempToolTip = "'sometooltip.jpg'";

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO UserAchievements VALUES(");
            sb.append("null,");
            sb.append(userId + ",");
            sb.append("'" + achievementName + "',");
            sb.append("'" + achievementDesc+ "',");
            sb.append("'" + formatter.format(new Date()) + "',");
            sb.append(tempToolTip + ");");

            stmt.executeUpdate(sb.toString());
            con.close();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void InsertUserDetail(String userName, String email, String password, String salt, boolean isAdmin)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);
            stmt.executeQuery("SET @@auto_increment_increment=1; ");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO UserDetail VALUES(");
            sb.append("null,");
            sb.append("'" + userName + "',");
            sb.append("'" + password + "',");
            sb.append("'" + salt + "',");
            sb.append(String.valueOf(isAdmin) + ",");
            sb.append("'" + formatter.format(new Date()) + "',");
            sb.append("'" + email + "');");

            stmt.executeUpdate(sb.toString());
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void InsertUserFriend(int userId, int friendUserId)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);
            stmt.executeQuery("SET @@auto_increment_increment=1; ");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO UserFriends VALUES(");
            sb.append(userId + ",");
            sb.append(friendUserId + ",");
            sb.append("'" + formatter.format(new Date()) + "');");

            stmt.executeUpdate(sb.toString());

            StringBuilder sb2 = new StringBuilder();
            sb2.append("INSERT INTO UserFriends VALUES(");
            sb2.append(friendUserId + ",");
            sb2.append(userId + ",");
            sb2.append("'" + formatter.format(new Date()) + "');");

            Statement stmt2 = (Statement) con.createStatement();
            stmt2.executeUpdate(sb2.toString());
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void DeleteAchievement(int userId, String achievementName)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);

            String query = String.format("Delete from UserAchievements WHERE UserId = %1$s and AchievementName = '%2$s';", userId, achievementName);
            stmt.executeUpdate(query.toString());

            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void DeleteUserFriendship(int userId, int friendId)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);

            String query = String.format("Delete from UserFriends WHERE UserId = %1$s and FriendId = %2$s;", userId, friendId );
            stmt.executeUpdate(query.toString());

            query = String.format("Delete from UserFriends WHERE UserId = %1$s and FriendId = %2$s;", friendId, userId );
            stmt.executeUpdate(query.toString());

            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void DeleteUserDetail(String userName)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);

            String query = String.format("Delete from UserDetail WHERE UserName = %1$s;", "'" + userName + "'");

            stmt.executeUpdate(query.toString());
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void PromoteUserToAdmin(String userName) {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection
                    ("jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME, MyDBInfo.MYSQL_PASSWORD);
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " + MyDBInfo.MYSQL_DATABASE_NAME);

            String query = String.format("UPDATE UserDetail SET AdminFlag='1' WHERE UserName = %1$s;", "'" + userName + "'");

            stmt.executeUpdate(query.toString());
            con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<User> GetUsers()
    {
        List<User> userList = new ArrayList<>();

        try {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = GetResultSet(con, "*", "UserDetail");

            while(rs.next())
            {
                User foundUser = new User();
                foundUser.userName = rs.getString("UserName");
                foundUser.userId = Integer.parseInt(rs.getString("UserId"));
                foundUser.email = rs.getString("UserEmail");
                foundUser.dateCreated = rs.getString("UserCreateDate");
                String isAdminst = rs.getString("AdminFlag");
                foundUser.isAdmin = isAdminst.equals("1");
                userList.add(foundUser);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return userList;
    }

    public static HashedPassword GetPasswordInfo(String userName)
    {
        HashedPassword foundUser = null;
        try {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = GetResultSetWithParameter(con, "UserDetail", "UserName", "'" + userName + "'");

            if(rs.next())
            {
                String hashedPass = rs.getString("UserPassword");
                String hashedSalt = rs.getString("UserSalt");
                foundUser = new HashedPassword(hashedPass, hashedSalt);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return foundUser;
    }

    public static User GetUser(String userName)
    {
        User foundUser = null;

        try {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = GetResultSetWithParameter(con, "UserDetail", "UserName", "'" + userName + "'");

            if(rs.next())
            {
                foundUser = new User();
                foundUser.userName = rs.getString("UserName");
                foundUser.userId = Integer.parseInt(rs.getString("UserId"));
                foundUser.email = rs.getString("UserEmail");
                foundUser.dateCreated = rs.getString("UserCreateDate");
                String isAdminst = rs.getString("AdminFlag");
                foundUser.isAdmin = isAdminst.equals("1");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return foundUser;
    }

    public static boolean CheckIfRecordExistsWithParameterString(String tableName, String parameterName, String parameterValue)
    {
        boolean doesRecordExist = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = GetResultSetWithParameter(con, tableName, parameterName, "'" + parameterValue + "'");

            doesRecordExist = rs.next(); //if row exists, record is found, can return true
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return doesRecordExist;
    }

    public static boolean CheckIfRecordExistsWithParameterInt(String tableName, String parameterName, String parameterValue)
    {
        boolean doesRecordExist = false;
        try {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = GetResultSetWithParameter(con, tableName, parameterName, parameterValue);

            doesRecordExist = rs.next(); //if row exists, record is found, can return true
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return doesRecordExist;
    }

    public static boolean CheckIfRecordExistsWithParametersIntInt(String tableName, String parameterName, String parameterValue, String parameterName2, String paramaterValue2)
    {
        boolean doesRecordExist = false;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);

            String query = String.format("Select * from %1$s WHERE %2$s = %3$s and %4$s = %5$s;", tableName, parameterName, parameterValue, parameterName2, paramaterValue2);
            ResultSet rs = stmt.executeQuery(query);

            doesRecordExist = rs.next(); //if row exists, record is found, can return true
            con.close();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return doesRecordExist;
    }

    public static boolean CheckIfRecordExistsWithParametersIntString(String tableName, String parameterName, String parameterValue, String parameterName2, String paramaterValue2)
    {
        boolean doesRecordExist = false;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);

            String query = String.format("Select * from %1$s WHERE %2$s = %3$s and %4$s = '%5$s';", tableName, parameterName, parameterValue, parameterName2, paramaterValue2);
            ResultSet rs = stmt.executeQuery(query);

            doesRecordExist = rs.next(); //if row exists, record is found, can return true
            con.close();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return doesRecordExist;
    }

    public static int GetCountRecordsFromTable(String tableName)
    {
        int count = 0;
        try
        {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = GetResultSet(con, "Count(*)", tableName);

            rs.next(); // exactly one result so allowed
            count = rs.getInt(1);
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return count;
    }

    public static ResultSet GetResultSetWithParameter(Connection con,String tableName, String parameterName, String parameterValue)
    {
        ResultSet rs = null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);

            String query = String.format("Select * from %1$s WHERE %2$s = %3$s;", tableName, parameterName, parameterValue);
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

    public static ResultSet GetResultSet(Connection con,String selectionType, String tableName)
    {
        ResultSet rs = null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);

            String query = String.format("Select " + selectionType + " from %1$s;", tableName);
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

    public static ResultSet GetRows(String tableName)
    {
        ResultSet rs = null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);

            String query = String.format("Select * from %1$s;", tableName);
//            String query = String.format("Select UserDetail.UserName from UserFriends join UserDetail on UserFriends.FriendId = UserDetail.UserId WHERE UserFriends.UserId = 1;");
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
        GetResultSet();
    }

    public static void GetResultSet()
    {
        ResultSet rs = null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);

            String query = String.format("Select UserDetail.UserName from UserFriends join UserDetail on UserFriends.FriendId = UserDetail.UserId WHERE UserFriends.UserId = 1;");
            rs = stmt.executeQuery(query);

            while(rs.next())
            {
                String name = rs.getString("UserName");
                System.out.println("Friend Name: " + name);
            }

            ResultSet rs2 = GetRows("UserDetail");
            ResultSetMetaData rsmd = (ResultSetMetaData) rs2.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            for (int i = 1; i <= columnsNumber; i++)
            {
                System.out.print(rsmd.getColumnName(i) + ",  ");
                if (i == columnsNumber) System.out.println(";");
            }

            while (rs2.next()) {
                for (int i = 1; i <= columnsNumber; i++)
                {
                    String columnValue = rs2.getString(i);
                    System.out.print(columnValue + ", ");
                    if (i == columnsNumber) System.out.println(";");
                }
                System.out.println("");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}