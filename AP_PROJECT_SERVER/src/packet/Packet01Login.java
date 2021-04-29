package packet;

import network.Server;
import network.Server.ClientHandler;

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

	public void writeData(ClientHandler clientHandler) {  
		clientHandler.sendData(this);	
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
	@Override
	public void writeData(Server Server) {
		// TODO Auto-generated method stub
		
	}

}
