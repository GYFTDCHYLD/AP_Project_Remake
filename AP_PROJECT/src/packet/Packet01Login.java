package packet;

import network.Client;

public class Packet01Login extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long HandlerID;
	private String userId;
	private String password; 
	
	public Packet01Login(String userId, String password) {
		super(01);
		this.userId = userId;
		this.password = password;
	}

	@Override
	public void writeData(Client client) {
		client.sendData(this); 
	}

	@Override
	public Object getData() {
		return this;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}
	
	public long getHandlerID() {
		return HandlerID;
	}


	public void setHandlerID(long handlerID) {
		HandlerID = handlerID;
	}
}
