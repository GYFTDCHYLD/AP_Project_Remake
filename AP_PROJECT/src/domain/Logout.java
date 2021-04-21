package domain;

import java.io.Serializable;

public class Logout implements Serializable{
	private String userId;

	
	
	public Logout() {
		super();
	}
	
	public Logout(String userId) {
		super();
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
	

}
