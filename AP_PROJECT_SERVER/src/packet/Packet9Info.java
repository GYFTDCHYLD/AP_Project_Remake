package packet;

import network.Server;
import network.Server.ClientHandler;

public class Packet9Info extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long HandlerID;
	private long ThreadID;
	private String loginId;
	private String info; 
	
	public Packet9Info(String data) {
		super(9);
		info = data;
	}
	
	
	public void writeData(ClientHandler clientHandler) {  
		clientHandler.sendData(this);	
	}

	@Override
	public String getData() {
		return this.info;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}


	public String getLoginId() {
		return loginId;
	}


	public void setLoginId(String loginId) {
		this.loginId = loginId;
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


	@Override
	public void writeData(Server Server) {
		// TODO Auto-generated method stub
		
	}
	
}
