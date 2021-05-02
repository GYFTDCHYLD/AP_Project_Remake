package packet;

import network.Client;

public class Packet10Error extends Packet{
	private static final long serialVersionUID = 1L;
	
	private String error; 
	
	public Packet10Error(String data) {
		super(10);
		error = data;
	}
	
	
	@Override
	public void writeData(Client client) {
		client.sendData(this);
	}

	@Override
	public String getData() {
		return this.error;
	}
	
}
