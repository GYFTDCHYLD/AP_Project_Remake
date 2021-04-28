package packet;

import network.Server;
import network.Server.ClientHandler; 

public class Packet02Logout extends Packet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long HandlerID;
	private long ThreadID;
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
	
	public long getHandlerID() {
		return HandlerID;
	}


	public void setHandlerID(long handlerID) {
		HandlerID = handlerID;
	}


	public long getThreadID() {
		return ThreadID;
	}


	public void setThreadID(long threadID) {
		ThreadID = threadID;
	}
}
