package db;


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
    public static void ExecuteUpdate(String sql)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);
            stmt.executeQuery("SET @@auto_increment_increment=1; ");

            stmt.executeUpdate(sql);
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

    public static boolean CheckIfRecordExistsWithParameterString(String tableName, String parameterName, String parameterValue)
    {
        String query = String.format("Select * from %1$s WHERE %2$s = %3$s;", tableName, parameterName, "'" + parameterValue + "'");
        boolean doesRecordExist = CheckIfRecordExists(query);
        return doesRecordExist;
    }

    public static boolean CheckIfRecordExistsWithParameterInt(String tableName, String parameterName, String parameterValue)
    {
        String query = String.format("Select * from %1$s WHERE %2$s = %3$s;", tableName, parameterName, parameterValue);
        boolean doesRecordExist = CheckIfRecordExists(query);
        return doesRecordExist;
    }

    public static boolean CheckIfRecordExistsWithParametersIntInt(String tableName, String parameterName, String parameterValue, String parameterName2, String paramaterValue2)
    {
        String query = String.format("Select * from %1$s WHERE %2$s = %3$s and %4$s = %5$s;", tableName, parameterName, parameterValue, parameterName2, paramaterValue2);
        boolean doesRecordExist = CheckIfRecordExists(query);
        return doesRecordExist;
    }

    public static boolean CheckIfRecordExistsWithParametersIntString(String tableName, String parameterName, String parameterValue, String parameterName2, String paramaterValue2)
    {
        String query = String.format("Select * from %1$s WHERE %2$s = %3$s and %4$s = '%5$s';", tableName, parameterName, parameterValue, parameterName2, paramaterValue2);
        boolean doesRecordExist = CheckIfRecordExists(query);
        return doesRecordExist;
    }

    public static boolean CheckIfRecordExists(String query)
    {
        boolean doesRecordExist = false;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = GetResultSet(con, query);
            doesRecordExist = rs.next(); //if row exists, record is found, can return true
            con.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
        String query = String.format("Select * from %1$s WHERE %2$s = %3$s;", tableName, parameterName, parameterValue);
        ResultSet rs = GetResultSet(con, query);
        return rs;
    }

    public static ResultSet GetResultSet(Connection con,String selectionType, String tableName)
    {
        ResultSet rs = null;
        String query = String.format("Select " + selectionType + " from %1$s;", tableName);
        rs = GetResultSet(con, query);
        return rs;
    }

    public static ResultSet GetResultSet(Connection con, String query)
    {
        ResultSet rs = null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = (Statement) con.createStatement();
            stmt.executeQuery("USE " +  MyDBInfo.MYSQL_DATABASE_NAME);

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
}