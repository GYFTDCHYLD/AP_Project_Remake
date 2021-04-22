package packet;

import network.Server;
import domain.Complain;

public class Packet04Complain extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Complain Complain; 
	
	public Packet04Complain(Complain data) {
		super(04);
		Complain = data;
	}
	
	
	@Override
	public void writeData(Server Server) {
		Server.sendData(this);
	}

	@Override
	public Complain getData() {
		return this.Complain;
	}
	
}
