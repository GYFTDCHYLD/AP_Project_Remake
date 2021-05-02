package packet;

import java.util.List;

import network.Server;
import network.Server.ClientHandler;

public class Packet11List extends Packet{
	private static final long serialVersionUID = 1L;
	
	private List<?> list; 
	private String type;
	
	public Packet11List(List<?> data) {
		super(11);
		list = data;
	}
	
	
	public void writeData(ClientHandler clientHandler) {  
		clientHandler.sendData(this);	
	}

	@Override
	public List<?> getData() {
		return this.list;
	}
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void writeData(Server Server) {
		// TODO Auto-generated method stub
		
	}
	
}
