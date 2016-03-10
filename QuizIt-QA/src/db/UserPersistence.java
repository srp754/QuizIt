package db;

import com.mysql.fabric.xmlrpc.base.Data;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import user.*;

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
public class UserPersistence
{
    public static void InsertAchievement(int userId, String achievementName, String achievementDesc)
    {
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

        DatabaseTasks.ExecuteUpdate(sb.toString());
    }

    public static void InsertUserDetail(String userName, String email, String password, String salt, boolean isAdmin)
    {
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

        DatabaseTasks.ExecuteUpdate(sb.toString());
    }

    public static void InsertUserFriend(int userId, int friendUserId)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO UserFriends VALUES(");
        sb.append(userId + ",");
        sb.append(friendUserId + ",");
        sb.append("'" + formatter.format(new Date()) + "');");

        DatabaseTasks.ExecuteUpdate(sb.toString());

        StringBuilder sb2 = new StringBuilder();
        sb2.append("INSERT INTO UserFriends VALUES(");
        sb2.append(friendUserId + ",");
        sb2.append(userId + ",");
        sb2.append("'" + formatter.format(new Date()) + "');");

        DatabaseTasks.ExecuteUpdate(sb2.toString());
    }

    public static void InsertAnnouncement(String text)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO Announcements VALUES(");
        sb.append("'" + text + "',");
        sb.append("'" + formatter.format(new Date()) + "');");
        DatabaseTasks.ExecuteUpdate(sb.toString());
    }

    public static void InsertUserActivity(int userId, String ActivityType, int ActivityLinkId)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO UserActivity VALUES(");
        sb.append("null,");
        sb.append("'" + userId + "',");
        sb.append("'" + ActivityType + "',");
        sb.append("'" + ActivityLinkId + "',");
        sb.append("'" + formatter.format(new Date()) + "');");
        DatabaseTasks.ExecuteUpdate(sb.toString());
    }

    public static void PromoteUserToAdmin(String userName)
    {
            String query = String.format("UPDATE UserDetail SET AdminFlag='1' WHERE UserName = %1$s;", "'" + userName + "'");
            DatabaseTasks.ExecuteUpdate(query);
    }

    public static void DeleteAchievement(int userId, String achievementName)
    {
        String query = String.format("Delete from UserAchievements WHERE UserId = %1$s and AchievementName = '%2$s';", userId, achievementName);
        DatabaseTasks.ExecuteUpdate(query);
    }

    public static void DeleteUserMessage(int messageId, String tableName)
    {
        String query = String.format("Delete from UserSocial WHERE MessageId = %1$s;", messageId);
        DatabaseTasks.ExecuteUpdate(query);

        query = String.format("Delete from %1$s WHERE MessageId = %2$s;", tableName, messageId);
        DatabaseTasks.ExecuteUpdate(query);
    }

    public static void DeleteUserFriendship(int userId, int friendId)
    {
        String query = String.format("Delete from UserFriends WHERE UserId = %1$s and FriendId = %2$s;", userId, friendId );
        DatabaseTasks.ExecuteUpdate(query);

        query = String.format("Delete from UserFriends WHERE UserId = %1$s and FriendId = %2$s;", friendId, userId );
        DatabaseTasks.ExecuteUpdate(query);
    }

    public static void DeleteUserDetail(String userName)
    {
        String query = String.format("Delete from UserDetail WHERE UserName = %1$s;", "'" + userName + "'");
        DatabaseTasks.ExecuteUpdate(query);
    }

    public static List<Announcement> GetAnnouncements()
    {
        List<Announcement> announcementList = new ArrayList<>();

        try {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = DatabaseTasks.GetResultSet(con, "*", "Announcements");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while(rs.next())
            {
                Date date = null;
                try {
                    date = formatter.parse(rs.getString("AnnouncementCreateDate"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String text = rs.getString("AnnouncementText");
                Announcement a = new Announcement(text, formatter.format(date));
                announcementList.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return announcementList;
    }

    public static List<Achievement> GetAchievements(int userId)
    {
        List<Achievement> achievementsList = new ArrayList<>();

        try {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = DatabaseTasks.GetResultSetWithParameter(con, "UserAchievements", "UserId", "'" + userId + "'");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            while(rs.next())
            {
                Date date = null;
                try {
                    date = formatter.parse(rs.getString("AchievementDate"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String name = rs.getString("AchievementName");
                String description = rs.getString("AchievementDescription");
                Achievement achievement = new Achievement(name, description, formatter.format(date));
                achievementsList.add(achievement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return achievementsList;
    }

    public static List<Activity> GetActivities(int userId)
    {
        List<Activity> activityList = new ArrayList<>();

        try {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = DatabaseTasks.GetResultSetWithParameter(con, "UserActivity", "UserId", "'" + userId + "'");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            while(rs.next())
            {
                Date date = null;
                try {
                    date = formatter.parse(rs.getString("ActivityDate"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String type = rs.getString("ActivityType");
                Activity activity = new Activity(type, formatter.format(date));
                activityList.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activityList;
    }

    public static List<User> GetUsers()
    {
        List<User> userList = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = DatabaseTasks.GetResultSet(con, "*", "UserDetail");

            while(rs.next())
            {
                User foundUser = new User();
                foundUser.userName = rs.getString("UserName");
                foundUser.userId = Integer.parseInt(rs.getString("UserId"));
                foundUser.email = rs.getString("UserEmail");
                foundUser.dateCreated = rs.getString("UserCreateDate");
                userList.add(foundUser);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
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

            ResultSet rs = DatabaseTasks.GetResultSetWithParameter(con, "UserDetail", "UserName", "'" + userName + "'");

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

            ResultSet rs = DatabaseTasks.GetResultSetWithParameter(con, "UserDetail", "UserName", "'" + userName + "'");

            if(rs.next())
            {
                foundUser = new User();
                foundUser.userName = rs.getString("UserName");
                foundUser.userId = Integer.parseInt(rs.getString("UserId"));
                foundUser.email = rs.getString("UserEmail");
                foundUser.dateCreated = rs.getString("UserCreateDate");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foundUser;
    }

    public static boolean isAdmin(String userName)
    {
        boolean isAdmin = false;
        try {
            Connection con = (Connection) DriverManager.getConnection
                    ( "jdbc:mysql://" + MyDBInfo.MYSQL_DATABASE_SERVER, MyDBInfo.MYSQL_USERNAME ,MyDBInfo.MYSQL_PASSWORD);

            ResultSet rs = DatabaseTasks.GetResultSetWithParameter(con, "UserDetail", "UserName", "'" + userName + "'");

            if(rs.next())
            {
                String isAdminst = rs.getString("AdminFlag");
                isAdmin = isAdminst.equals("1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isAdmin;
    }
}
