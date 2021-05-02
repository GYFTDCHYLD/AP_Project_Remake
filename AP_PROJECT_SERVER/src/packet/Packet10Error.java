package packet;

import network.Server;
import network.Server.ClientHandler;

public class Packet10Error extends Packet{
	private static final long serialVersionUID = 1L;
	
	private String error; 
	
	public Packet10Error(String data) {
		super(10);
		error = data;
	}
	
	
	public void writeData(ClientHandler clientHandler) {  
		clientHandler.sendData(this);	
	}

	@Override
	public String getData() {
		return this.error;
	}


	@Override
	public void writeData(Server Server) {
		// TODO Auto-generated method stub
		
	}
	
}
