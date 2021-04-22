package packet;

import network.Server;
import domain.Logout; 

public class Packet02Logout extends Packet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    Logout Logout;

    public Packet02Logout(Logout data) {
        super(02);
        Logout = data;
    }
	@Override
	public void writeData(Server client) {
		client.sendData(this);
	}


    @Override
    public Logout getData() {
        return this.Logout;
    }
}
