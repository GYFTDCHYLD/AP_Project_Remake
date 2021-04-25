package packet;

import domain.Register;
import network.Server;
import network.Server.ClientHandler;

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

	public void writeData(ClientHandler clientHandler) {  
		clientHandler.sendData(this);	
	}

	@Override
	public Register getData() { 
		return this.Register;
	}

	public Register getRegister() {
		return Register;
	}

	@Override
	public void writeData(Server Server) {
		// TODO Auto-generated method stub
		
	}	

}
