package packet;

import network.Server;
import network.Server.ClientHandler;
import domain.User;

public class Packet07User extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User User; 
	
	public Packet07User(User data) {
		super(07);
		User = data;
	}
	
	
	public void writeData(ClientHandler clientHandler) {  
		clientHandler.sendData(this);	
	}

	@Override
	public User getData() {
		return this.User;
	}


	@Override
	public void writeData(Server Server) {
		// TODO Auto-generated method stub
		
	}
	
}
