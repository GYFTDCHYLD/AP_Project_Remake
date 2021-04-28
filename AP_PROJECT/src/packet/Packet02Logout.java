package packet;

import network.Client;

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
	@Override
	public void writeData(Client client) {
		client.sendData(this);
	}


    @Override
    public String getData() {
        return this.userId;
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
