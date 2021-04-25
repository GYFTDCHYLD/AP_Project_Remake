package packet;

import java.util.List;

import network.Client;

public class Packet11List extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<?> list; 
	
	public Packet11List(List<?> data) {
		super(11);
		list = data;
	}
	
	
	@Override
	public void writeData(Client client) {
		client.sendData(this);
	}

	@Override
	public List<?> getData() {
		return this.list;
	}
	
}
