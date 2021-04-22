package packet;

import network.Client;
import domain.Login;

public class Packet01Login extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Login Login;
	public Packet01Login(Login data) {
		super(01);
		Login = data;
	}

	@Override
	public void writeData(Client client) {
		client.sendData(this); 
	}


	@Override
	public Login getData() {
		return this.Login;
	}


}
