package packet;

import network.Server;

public class Packet9Info extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int threadIndex;
	private String loginId;
	private String info; 
	
	public Packet9Info(String data) {
		super(9);
		info = data;
	}
	
	
	@Override
	public void writeData(Server Server) {
		Server.sendData(this);
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


	public int getThreadIndex() {
		return threadIndex;
	}


	public void setThreadIndex(int threadIndex) {
		this.threadIndex = threadIndex;
	}


	public String getLoginId() {
		return loginId;
	}


	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
}
