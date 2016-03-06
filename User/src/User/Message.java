package User;


public class Message {
	private int sender;
	private int recipient;
	private String type;
	private String content;
	
	public Message(int sender, int recipient, String type, String content) {
		this.sender = sender;
		this.recipient = recipient;
		this.type = type;
		this.content = content;
	}
	
	public int getSender() {
		return sender;
	}
	
	public int getRecipient() {
		return recipient;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getType() {
		return type;
	}
}