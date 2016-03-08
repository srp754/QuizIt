package User;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Alex on 3/7/2016.
 */
public class UserSocialTest
{
    @org.junit.Test
    public void Should_Add_And_Delete_A_Note()
    {
        int messageId = Messaging.addMessage(
                new Message(1,2,"note", "this is a note sent from 2 to recipient 1")
        );

        boolean doesMessageExist = Messaging.MessageExists(messageId);
        assertTrue(doesMessageExist);

        Messaging.removeMessage(messageId);
        doesMessageExist = Messaging.MessageExists(messageId);
        assertFalse(doesMessageExist);
    }

    @org.junit.Test
    public void Should_Add_And_Delete_A_Friend_Request()
    {
        int messageId = Messaging.addMessage(
                new Message(3,4, "friend", "this is a friend request sent from 4 to recipient 3")
        );

        boolean doesMessageExist = Messaging.MessageExists(messageId);
        assertTrue(doesMessageExist);

        Messaging.removeMessage(messageId);
        doesMessageExist = Messaging.MessageExists(messageId);
        assertFalse(doesMessageExist);
    }

    @org.junit.Test
    public void Should_Add_And_Delete_A_User_Challenge()
    {
        int messageId = Messaging.addMessage(
                new Message(5,2, "challenge", "this is a challenge sent from 2 to recipient 5")
        );

        boolean doesMessageExist = Messaging.MessageExists(messageId);
        assertTrue(doesMessageExist);

        Messaging.removeMessage(messageId);
        doesMessageExist = Messaging.MessageExists(messageId);
        assertFalse(doesMessageExist);
    }

    @org.junit.Test
    public void Should_Get_Sample_Note()
    {
        Message msg = Messaging.getMessage(2);
        String expected = "Note 2 with a lot of extra text to make sure it can store a lot.";
        String actual = msg.getContent();

        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void Should_Get_Sample_Challenge()
    {
        Message msg = Messaging.getMessage(4);
        String expected = "Youre being challenged!";
        String actual = msg.getContent();

        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void Should_Get_Friend_Request()
    {
        Message msg = Messaging.getMessage(7);
        String expected = "Rejected";
        String actual = msg.getContent();

        assertEquals(expected, actual);
    }
}
