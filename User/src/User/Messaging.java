package User;

import java.util.*;

public class Messaging implements IMessaging
{
	private static Map<Integer, List<Integer>> messages = new HashMap<Integer, List<Integer>>();
    private static Map<Integer, Set<Integer>> dbFriendRequests = new HashMap<Integer, Set<Integer>>();
    private static Map<Integer, Set<Integer>> dbMessages = new HashMap<Integer, Set<Integer>>();
	
	public static int addMessage(Message msg) //may throw MySqlException that duplicate entry, this is OK - SQL prevent duplicate entries which is good
	{
		int newMessageId = DatabaseTasks.InsertUserMessage(msg);
		msg.setMessageId(newMessageId);

		if(msg.getType().equals("friend"))
			DatabaseTasks.InsertUserFriendRequest(msg);
		else if(msg.getType().equals("note"))
			DatabaseTasks.InsertUserNote(msg);
		else
			DatabaseTasks.InsertUserChallenge(msg);

		int id = messages.size();
		messages.put(id, Arrays.asList(msg.getSender(), msg.getRecipient()));
		return newMessageId;
	}

	public static boolean MessageExists(int messageId)
	{
		return DatabaseTasks.CheckIfRecordExistsWithParameterInt("UserSocial", "MessageId",  Integer.toString(messageId));
	}

	public static void removeMessage(int messageId)
	{
		if(MessageExists(messageId))
		{
			Message msg = getMessage(messageId);

			if(msg.getType().equals("friend"))
				DatabaseTasks.DeleteUserMessage(messageId, "UserFriendRequests");
			else if(msg.getType().equals("note"))
				DatabaseTasks.DeleteUserMessage(messageId, "UserNotes");
			else
				DatabaseTasks.DeleteUserMessage(messageId, "UserChallenges");
		}

	}

	public static Message getMessage(int id)
	{
		Message msg = null;

		if(MessageExists(id))
			msg = DatabaseTasks.GetMessage(id);

		return msg;
	}
    
    public static Set<Integer> getMessages(int userId) {
    	//TODO get from DB when it's ready
    	if (dbMessages.containsKey(userId)) {
    		return dbMessages.get(userId);
    	} else {
    		return new HashSet<Integer>();
    	}
    }
    
    public static boolean requestExists(int sender, int recipient) {
    	if (!dbFriendRequests.containsKey(recipient)) return false;
    	return dbFriendRequests.get(recipient).contains(sender);
    }
    
    public static Set<Integer> getFriendRequests(int userId) {
    	//TODO get from DB when it's ready
    	if (dbFriendRequests.containsKey(userId)) {
    		return dbFriendRequests.get(userId);
    	} else {
    		return new HashSet<Integer>();
    	}
    }
    
    public static void addFriendRequest(int sender, int recipient) {
    	if (!dbFriendRequests.containsKey(recipient)) {
    		dbFriendRequests.put(recipient, new HashSet<Integer>());
    	}
    	dbFriendRequests.get(recipient).add(sender);
    }
    
    public static void removeFriendRequest(int sender, int recipient) {
    	if (dbFriendRequests.containsKey(recipient) && 
    			dbFriendRequests.get(recipient).contains(sender)) {
    		dbFriendRequests.get(recipient).remove(sender);
    	}
    }
}