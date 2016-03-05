package User;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 3/2/2016.
 */
public class DatabaseTasks
{
    public static void InsertUserDetail(String userName, String password, String salt, boolean isAdmin)
    {
        try
        {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);
            stmt.executeQuery("SET @@auto_increment_increment=1; ");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String tempEmail = "'email@somewhere.com'";

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO UserDetail VALUES(");
            sb.append("null,");
            sb.append("'" + userName + "',");
            sb.append("'" + password + "',");
            sb.append("'" + salt + "',");
            sb.append(String.valueOf(isAdmin) + ",");
            sb.append("'" + formatter.format(new Date()) + "',");
            sb.append(tempEmail + ");");

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

    public static void InsertUserSocial(int userId, int friendUserId)
    {
        try
        {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Class.forName("com.mysql.jdbc.Driver");
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

    public static void DeleteUserDetail(String userName)
    {
        try
        {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Class.forName("com.mysql.jdbc.Driver");
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

    public static User GetUser(String userName) throws SQLException
    {
        User foundUser = null;

        Connection con = (Connection) DriverManager.getConnection
                ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

        ResultSet rs = GetResultSetWithParameter(con, "UserDetail", "UserName", "'" + userName + "'");

        if(rs.next())
        {
            foundUser = new User();
            foundUser.userName = rs.getString("UserName");
            foundUser.userId = Integer.parseInt(rs.getString("UserId"));
            String isAdminst = rs.getString("AdminFlag");
            foundUser.isAdmin = isAdminst.equals("1");
        }

        return foundUser;
    }

    public static boolean CheckIfRecordExistsWithParameterString(String tableName, String parameterName, String parameterValue) throws SQLException
    {
        Connection con = (Connection) DriverManager.getConnection
                ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

        ResultSet rs = GetResultSetWithParameter(con, tableName, parameterName, "'" + parameterValue + "'");

        boolean doesRecordExist = rs.next(); //if row exists, record is found, can return true
        con.close();

        return doesRecordExist;
    }

    public static boolean CheckIfRecordExistsWithParameterInt(String tableName, String parameterName, String parameterValue) throws SQLException
    {
        Connection con = (Connection) DriverManager.getConnection
                ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

        ResultSet rs = GetResultSetWithParameter(con, tableName, parameterName, parameterValue);

        boolean doesRecordExist = rs.next(); //if row exists, record is found, can return true
        con.close();

        return doesRecordExist;
    }

    public static boolean CheckIfRecordExistsWithParametersIntInt(String tableName, String parameterName, String parameterValue, String parameterName2, String paramaterValue2) throws SQLException
    {
        Connection con = (Connection) DriverManager.getConnection
                ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

        boolean doesRecordExist = false;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);

            String query = String.format("Select * from %1$s WHERE %2$s = %3$s and %4$s = %5$s;", tableName, parameterName, parameterValue, parameterName2, paramaterValue2);
//            String query = String.format("Select UserDetail.UserName from UserFriends join UserDetail on UserFriends.FriendId = UserDetail.UserId WHERE UserFriends.UserId = 1;");
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

    public static int GetCountRecordsFromTable(String tableName) throws SQLException
    {
        Connection con = (Connection) DriverManager.getConnection
                ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

        ResultSet rs = GetResultSet(con, "Count(*)", tableName);

        rs.next(); // exactly one result so allowed
        int count = rs.getInt(1);
        con.close();

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

    public static ResultSet GetResultSet(Connection con,String selectionType, String tableName)
    {
        ResultSet rs = null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);

            String query = String.format("Select " + selectionType + " from %1$s;", tableName);
//            String query = String.format("Select * from %1$s;", tableName);
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

    public static ResultSet GetRows(String tableName)
    {
        ResultSet rs = null;

        try
        {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.jdbc.Driver");
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
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Class.forName("com.mysql.jdbc.Driver");
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