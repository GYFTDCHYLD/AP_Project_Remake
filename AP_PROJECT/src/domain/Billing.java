package domain;

import java.io.Serializable;

public class Billing  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private float amountDue;
	private float interest;
	private String dueDate;
	private String paidDate;
	
	
	public Billing() {
		super();
	}


	public Billing(String id, String status, float amountDue) {
		super();
		this.id = id;
		this.status = status;
		this.amountDue = amountDue;
	}


	public Billing(String id, String status, float amountDue, float interest, String dueDate, String paidDate) {
		super();
		this.id = id;
		this.status = status;
		this.amountDue = amountDue;
		this.interest = interest;
		this.dueDate = dueDate;
		this.paidDate = paidDate;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
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


	public String getDueDate() {
		return dueDate;
	}


	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}


	public String getPaidDate() {
		return paidDate;
	}


	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}
	
}
