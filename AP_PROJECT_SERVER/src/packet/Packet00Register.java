package packet;

import domain.User;
import network.Server;
import network.Server.ClientHandler;

public class Packet00Register extends Packet{
	private static final long serialVersionUID = 1L;
	
	private User User; 
	
	public Packet00Register(User User) {
		super(00);
		this.User = User; 
	}

	public void writeData(ClientHandler clientHandler) {  
		clientHandler.sendData(this);	
	}

	@Override
	public User getData() { 
		return this.User;
	}

	@Override
	public void writeData(Server Server) {
		// TODO Auto-generated method stub
	}	
}
