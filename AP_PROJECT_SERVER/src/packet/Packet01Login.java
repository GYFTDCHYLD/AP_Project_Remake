package packet;

import network.Server;

public class Packet01Login extends Packet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String password; 
	
	public Packet01Login(String userId, String password) {
		super(01);
		this.userId = userId;
		this.password = password;
	}

	@Override
	public void writeData(Server Server) {
		Server.sendData(this); 
	}


	@Override
	public Object getData() {
		return this;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

}
