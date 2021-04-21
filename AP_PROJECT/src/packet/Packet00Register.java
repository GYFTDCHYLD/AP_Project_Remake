package packet;

import domain.Register;
import network.Client;

public class Packet00Register extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Register Register;
	
	public Packet00Register(Register Register) {
		super(00);
		this.Register = Register; 
	}

	@Override
	public void writeData(Client client) {
		client.sendData(this);
	}

	@Override
	public Register getData() { 
		return this.Register;
	}
}
