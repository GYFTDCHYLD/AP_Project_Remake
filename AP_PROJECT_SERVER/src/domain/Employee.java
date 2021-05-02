package domain;

import java.io.Serializable;

public class Employee extends User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String jobTitle;
	
	public Employee() {
		super();
	}
	
	public Employee(String userId, String nameTitle, String firstName, String lastName, long phoneNumber, String email, String password, String title) { 
		super(userId, nameTitle, firstName, lastName, phoneNumber, email, password);
		this.jobTitle = title;
	}

	public Employee(String jobTitle) {
		super();
		this.jobTitle = jobTitle;
	}


	public String getJobTitle() {
		return jobTitle;
	}


	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
}