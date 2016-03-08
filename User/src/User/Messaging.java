package User;

import java.util.*;

public class Messaging implements IMessaging
{
	private static Map<Integer, List<Integer>> messages = new HashMap<Integer, List<Integer>>();
    private static Map<Integer, Set<Integer>> dbFriendRequests = new HashMap<Integer, Set<Integer>>();
    private static Map<Integer, Set<Integer>> dbMessages = new HashMap<Integer, Set<Integer>>();
	
	public static void addMessage(Message msg) {
		int id = messages.size();
		messages.put(id, Arrays.asList(msg.getSender(), msg.getRecipient()));
		addMessageToDB(msg.getSender(), id);
		addMessageToDB(msg.getRecipient(), id);
	}
	
	public static void removeMessage(int id) {
		if (messages.containsKey(id)) {
			removeMessageFromDB(messages.get(id).get(0), id);
			removeMessageFromDB(messages.get(id).get(1), id);
			messages.remove(id);
		}
	}
	
	public static Message getMessage(int id) {
		int sender = messages.get(id).get(0);
		int recipient = messages.get(id).get(1);
		String type = "type";
		String content = "content";
		return new Message(sender, recipient, type, content);
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

    private static void addMessageToDB(int userId, int id) {
    	if (!dbMessages.containsKey(userId)) {
    		dbMessages.put(userId, new HashSet<Integer>());
    	}
    	dbMessages.get(userId).add(id);
    }
    
    private static void removeMessageFromDB(int userId, int id) {
    	//TODO error checking
    	dbMessages.get(userId).remove(id);
    }

}