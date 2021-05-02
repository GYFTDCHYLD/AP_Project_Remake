package packet;

import domain.User;
import network.Client;

public class Packet00Register extends Packet{
	private static final long serialVersionUID = 1L;
	
	private User User; 
	
	public Packet00Register(User User) {
		super(00);
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
}
