package packet;

import network.Client;

public class Packet10Error extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String info; 
	
	public Packet10Error(String data) {
		super(10);
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
