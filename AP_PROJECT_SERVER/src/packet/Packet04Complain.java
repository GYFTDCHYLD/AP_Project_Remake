package packet;

import network.Server;
import network.Server.ClientHandler;
import domain.Complain;

public class Packet04Complain extends Packet{
	private static final long serialVersionUID = 1L;
	
	private Complain Complain; 
	
	public Packet04Complain(Complain data) {
		super(04);
		Complain = data;
	}
	
	
	public void writeData(ClientHandler clientHandler) {  
		clientHandler.sendData(this);	
	}

	@Override
	public Complain getData() {
		return this.Complain;
	}


	@Override
	public void writeData(Server Server) {
		// TODO Auto-generated method stub
		
	}
	
}
