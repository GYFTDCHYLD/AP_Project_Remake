package domain;

import java.io.Serializable;
import java.util.List;



public class User  implements Serializable{
	
	protected String userId;
	protected String firstName;
	protected String lastName;
	protected String password;
	protected List<Complain> Complains; 
	 
	
	
	public User() {
		super();
	}
	
	
	public User(String userId, String firstName, String lastName, String password) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public List<Complain> getComplains() {
		return Complains;
	}


	public void setComplains(List<Complain> complains) {
		Complains = complains;
	}
}
