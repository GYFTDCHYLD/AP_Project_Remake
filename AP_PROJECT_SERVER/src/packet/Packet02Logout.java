package packet;

import network.Server;
import network.Server.ClientHandler; 

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
	
    public void writeData(ClientHandler clientHandler) {  
		clientHandler.sendData(this);	
	}


    @Override
    public String getData() {
        return this.userId;
    }

	@Override
	public void writeData(Server Server) {
		// TODO Auto-generated method stub
		
	}
}
