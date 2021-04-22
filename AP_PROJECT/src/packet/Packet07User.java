package packet;

import network.Client;
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
	
	
	@Override
	public void writeData(Client client) {
		client.sendData(this);
	}

	@Override
	public User getData() {
		return this.User;
	}
	
}
