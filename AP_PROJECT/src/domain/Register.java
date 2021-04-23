package domain;

import java.io.Serializable;

public class Register implements Serializable{
	
	private String nameTitle;
	private String firstName;
	private String lastName;
	private long phoneNumber;
	private String email;
	private String password;
	
	
	
	public Register() {
		super();
	}
	
	
	public Register(String nameTitle, String firstName, String lastName, long phoneNumber, String email, String password) {
		super();
		
		this.nameTitle = nameTitle;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.password = password;
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
