package domain;

import java.io.Serializable;

public class User  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected String userId;
	protected String nameTitle;
	protected String firstName;
	protected String lastName;
	protected long phoneNumber;
	protected String email;
	protected String password;
	
	
	public User() {
		super();
	}

	public User(String userId, String nameTitle, String firstName, String lastName, long phoneNumber, String email, String password) {
		super();
		this.userId = userId;
		this.nameTitle = nameTitle;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNameTitle() {
		return nameTitle;
	}

	public void setNameTitle(String nameTitle) {
		this.nameTitle = nameTitle;
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

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
