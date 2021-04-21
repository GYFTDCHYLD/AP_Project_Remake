package packet;

import network.Client;
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
	public void writeData(Client client) {
		client.sendData(this);
	}

	@Override
	public Chat getData() {
		return this.Message;
	}
	
}
