package packet;

import network.Client;
import domain.User;

public class Packet9Info extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String info; 
	
	public Packet9Info(String data) {
		super(9);
		info = data;
	}
	
	
	@Override
	public void writeData(Client client) {
		client.sendData(this);
	}

	@Override
	public String getData() {
		return this.info;
	}
	
}
