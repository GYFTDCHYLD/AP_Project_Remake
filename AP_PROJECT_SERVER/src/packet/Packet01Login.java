package packet;

import network.Server;
import domain.Login;

public class Packet01Login extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Login Login;
	
	public Packet01Login(Login Login) {
		super(01);
		this.Login = Login; 
	}

	@Override
	public void writeData(Server Server) {
		Server.sendData(getData());
	}


	@Override
	public Login getData() {
		return this.Login;
	}

}
