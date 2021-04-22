package packet;

import network.Client;
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
	public void writeData(Client client) {
		client.sendData(this);
	}

	@Override
	public Complain getData() {
		return this.Complain;
	}
	
}
