package domain;

import java.io.Serializable;
import java.sql.Date;

public class BillingAccount  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String status;
	private float amountDue;
	private float interest;
	private Date dueDate;
	private Date paidDate;
	
	
	public BillingAccount() {
		super();
	}


	public BillingAccount(String id, String status, float amountDue) {
		super();
		this.id = id;
		this.status = status;
		this.amountDue = amountDue;
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
