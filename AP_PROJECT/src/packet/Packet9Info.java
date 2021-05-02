package packet;

import network.Client;

public class Packet9Info extends Packet{
	private static final long serialVersionUID = 1L;
	
	private long HandlerID;
	private long ThreadID;
	private String loginId;
	private String info;
	private String info2; 
	private String Assignment; 
	
	public Packet9Info(String data) {
		super(9);
		info = data;
	}
	
	
	@Override
	public void writeData(Client client) {
		client.sendData(this);
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


	public String getAssignment() {
		return Assignment;
	}


	public void setAssignment(String assignment) {
		Assignment = assignment;
	}
	
	public String getInfo2() {
		return info2;
	}


	public void setInfo2(String info2) {
		this.info2 = info2;
	}

}
