package domain;

import java.sql.Date;

public class BillingAccount {
	
	private int id;
	private String status;
	private float amountDue;
	private float interest;
	private Date dueDate;
	private Date paidDate;
	
	
	public BillingAccount() {
		super();
	}


	public BillingAccount(int id, String status, float amountDue) {
		super();
		this.id = id;
		this.status = status;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public float getAmountDue() {
		return amountDue;
	}


	public void setAmountDue(float amountDue) {
		this.amountDue = amountDue;
	}


	public float getInterest() {
		return interest;
	}


	public void setInterest(float interest) {
		this.interest = interest;
	}


	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public Date getPaidDate() {
		return paidDate;
	}


	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	
	
}
