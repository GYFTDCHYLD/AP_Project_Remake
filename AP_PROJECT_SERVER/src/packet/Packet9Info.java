package packet;

import network.Server;

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
	public void writeData(Server Server) {
		Server.sendData(this);
	}

	@Override
	public String getData() {
		return this.info;
	}
	
}
