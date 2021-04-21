package domain;

public class Chat {
	private String sender;
	private String reciever;
	private String response;
	
	
	public Chat() {
		super();
	} 
	
	public Chat(String sender, String reciever, String response) {
		super();
		this.sender = sender;
		this.reciever = reciever;
		this.response = response;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReciever() {
		return reciever;
	}

	public void setReciever(String reciever) {
		this.reciever = reciever;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	} 
}
