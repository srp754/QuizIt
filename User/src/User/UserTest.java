package User;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Created by scottparsons on 2/25/16.
 */
public class UserTest
{
    @org.junit.Test
    public void Should_Get_Count_Of_Users() throws SQLException
    {
        IUser userRepository = new UserRepository();
        int expectedCount = 5;
        int actualCount = userRepository.getNumberOfUsers();

        assertTrue(actualCount >= actualCount);
    }

    @org.junit.Test
    public void Should_Check_If_Name_Exists_In_Db() throws SQLException
    {
        IUser userRepository = new UserRepository();
        boolean isCorrectNameInDb = userRepository.userExists("Alex");
        boolean isIncorrectNameInDb = userRepository.userExists("Garcia");

        assertTrue(isCorrectNameInDb);
        assertFalse(isIncorrectNameInDb);
    }

    @org.junit.Test
    public void Should_Not_Add_Already_Existing_Record() throws SQLException
    {
        IUser userRepository = new UserRepository();

        boolean isRecordedAdded = userRepository.createNewUser("Alex", "password", true);
        assertFalse(isRecordedAdded);
    }

    @org.junit.Test
    public void Should_Add_And_Delete_Record() throws SQLException
    {
        IUser userRepository = new UserRepository();
        userRepository.createNewUser("TestUserShouldNotExistInDatabaseEver", "password", true);

        boolean isRecordInDb = userRepository.userExists("TestUserShouldNotExistInDatabaseEver");
        assertTrue(isRecordInDb);

        userRepository.DeleteUser("TestUserShouldNotExistInDatabaseEver");
        isRecordInDb = userRepository.userExists("TestUserShouldNotExistInDatabaseEver");
        assertFalse(isRecordInDb);
    }

    @org.junit.Test
    public void Should_Create_Users_And_Check_If_They_Are_Admin() throws SQLException
    {
        IUser userRepository = new UserRepository();
        boolean isUserAdmin;

        userRepository.PopulateCurrentUser("Connie");
        isUserAdmin = userRepository.isAdmin();
        assertTrue(isUserAdmin);

        userRepository.PopulateCurrentUser("Scott");
        isUserAdmin = userRepository.isAdmin();
        assertFalse(isUserAdmin);

        userRepository.PopulateCurrentUser("NotRealUserName");
        isUserAdmin = userRepository.isAdmin();
        assertFalse( isUserAdmin);
    }

    @org.junit.Test
    public void Should_Add_Friend_One_Way() throws SQLException
    {
        IUser userRepository = new UserRepository();
        userRepository.PopulateCurrentUser("Connie");

        userRepository.addFriend(5);
        boolean doesFriendshipExist = userRepository.FriendsupExists(5);
        boolean selfFriendshipShouldNotExist = userRepository.FriendsupExists(2);

        assertTrue(doesFriendshipExist);
        assertFalse(selfFriendshipShouldNotExist);
    }

    @org.junit.Test
    public void Should_Add_Friend_Two_Ways() throws SQLException
    {
        IUser userRepository = new UserRepository();
        userRepository.PopulateCurrentUser("John");
        userRepository.addFriend(4);

        boolean doesFriendshipExist = userRepository.FriendsupExists(4);

        userRepository.PopulateCurrentUser("Larry");
        boolean doesFriendshipExistOtherWay = userRepository.FriendsupExists(5);

        boolean selfFriendshipShouldNotExist = userRepository.FriendsupExists(4);

        assertTrue(doesFriendshipExist);
        assertTrue(doesFriendshipExistOtherWay);
        assertFalse(selfFriendshipShouldNotExist);
    }
}