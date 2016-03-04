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

        assertEquals(expectedCount, actualCount);
    }

    @org.junit.Test
    public void testCreateNewUser()
    {
        IUser userRepo = new UserRepository();
        userRepo.createNewUser("john", "password", false);
        userRepo.createNewUser("scott", "stanford", false);

        assertTrue(userRepo.createNewUser("jay", "computer", false));
        assertFalse(userRepo.createNewUser("scott", "stanford", false));
    }

    @org.junit.Test
    public void testIsCorrectLogin()
    {
        IUser userRepo = new UserRepository();
        assertTrue(userRepo.isCorrectLogin("john", "password"));
        assertFalse(userRepo.isCorrectLogin("alex", "password"));
        assertFalse(userRepo.isCorrectLogin("scott", "stanfords"));
    }

    @Test
    public void testName() throws Exception {

    }
}