package packet;

import network.Server;

public class Packet03Chat extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sender;
	private String reciever;
	private String message;
	
	public Packet03Chat(String sender, String reciever, String message) { 
		super(03);
		this.sender = sender;
		this.reciever = reciever;
		this.message = message;
	}
	
	
	@Override
	public void writeData(Server server) { 
		server.sendDataToAllClients(this);	
	}


	@Override
	public Packet03Chat getData() {
		return this; 
	}

	public String getSender() {
		return sender;
	}


	public String getReciever() {
		return reciever;
	}


	public String getMessage() {
		return message;
	}
	
}
