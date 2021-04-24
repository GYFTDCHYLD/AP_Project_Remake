package packet;

import network.Client;

public class Packet02Logout extends Packet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;

    public Packet02Logout(String data) {
        super(02);
        userId = data;
    }
	@Override
	public void writeData(Client client) {
		client.sendData(this);
	}


    @Override
    public String getData() {
        return this.userId;
    }

}
