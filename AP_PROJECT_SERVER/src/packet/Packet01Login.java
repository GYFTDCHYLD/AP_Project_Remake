package packet;

import domain.User;
import network.Server;
import network.Server.ClientHandler;

public class Packet01Login extends Packet{
	private static final long serialVersionUID = 1L;
	
	private long HandlerID;
	private User User; 
	
	public Packet01Login(User User) {
		super(01);
		this.User = User;
	}

	public void writeData(ClientHandler clientHandler) {  
		clientHandler.sendData(this);	
	}

	@Override
	public User getData() {
		return this.User;
	}

	public String getUserId() {
		return User.getUserId();
	}

	public String getPassword() {
		return User.getPassword();
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
