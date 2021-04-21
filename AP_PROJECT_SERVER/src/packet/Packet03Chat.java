package packet;

import network.Server;
import domain.Chat;

public class Packet03Chat extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Chat Message; 
	
	public Packet03Chat(Chat data) {
		super(03);
		Message = data;
	}
	
	@Override
	public void writeData(Server Server) {
		Server.sendData(this);
	}

	@Override
	public Chat getData() {
		return this.Message;
	}
	
}
