package packet;

import network.Server;
import domain.User;

public class Packet07User extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User User; 
	
	public Packet07User(User data) {
		super(03);
		User = data;
	}
	
	
	@Override
	public void writeData(Server Server) {
		Server.sendData(this);
	}

	@Override
	public User getData() {
		return this.User;
	}
	
}
