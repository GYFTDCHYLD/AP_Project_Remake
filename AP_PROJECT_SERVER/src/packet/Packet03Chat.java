package packet;

import network.Server;
import network.Server.ClientHandler;

public class Packet03Chat extends Packet{
	private static final long serialVersionUID = 1L;
	
	private String senderId; 
	private String senderName; 
	private String recieverId;
	private String message;
	
	public Packet03Chat(String senderId, String senderName, String recieverId, String message) {  
		super(03);
		this.senderId = senderId;
		this.senderName = senderName;
		this.recieverId = recieverId;
		this.message = message;
	}
	
	
	
	public void writeData(ClientHandler clientHandler) {  
		clientHandler.sendData(this);	
	}


	@Override
	public Packet03Chat getData() {
		return this; 
	}


	public String getSenderId() {
		return senderId;
	}


	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}


	public String getSenderName() {
		return senderName;
	}


	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}


	public String getRecieverId() {
		return recieverId;
	}


	public void setRecieverId(String recieverId) {
		this.recieverId = recieverId;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public void writeData(Server Server) {
		// TODO Auto-generated method stub
		
	}

	
}
