package packet;

import java.util.List;

import domain.Complain;
import network.Server;

public class Packet10List extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Complain> list; 
	
	public Packet10List(List<Complain> data) {
		super(10);
		list = data;
	}
	
	
	@Override
	public void writeData(Server Server) {
		Server.sendData(this);
	}

	@Override
	public List<Complain> getData() {
		return this.list;
	}
	
}
