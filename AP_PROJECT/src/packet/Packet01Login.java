package packet;

import domain.User;
import network.Client;

public class Packet01Login extends Packet{
	private static final long serialVersionUID = 1L;
	
	private long HandlerID;
	private User User; 
	
	public Packet01Login(User User) {
		super(01);
		this.User = User;
	}

	@Override
	public void writeData(Client client) {
		client.sendData(this); 
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
}
