package domain;

import java.io.Serializable;

public class Employee extends User implements Serializable{
	
	private String jobTitle;
	
	
	public Employee() {
		super();
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
