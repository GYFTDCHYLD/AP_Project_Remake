package domain;

import java.io.Serializable;

public class Complain implements Serializable{
	private static final long serialVersionUID = 1L;	
	
	private int id;
	private String custId; 
	private String type;
	private String message;
	private String repId;
	private String techId;
	private String visitDate; 
	
	
	public Complain() {
		super();
	}


	public Complain(int id, String custId, String type, String message) {
		super();
		this.id = id;
		this.custId = custId;
		this.type = type;
		this.message = message;
	}
	

	public Complain(int id, String custId, String type, String message, String repId, String techId, String visitDate) {
		super();
		this.id = id;
		this.custId = custId;
		this.type = type;
		this.message = message;
		this.repId = repId;
		this.techId = techId;
		this.visitDate = visitDate;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCustId() {
		return custId;
	}


	public void setCustId(String custId) {
		this.custId = custId;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getRepId() {
		return repId;
	}


	public void setRepId(String repId) {
		this.repId = repId;
	}


	public String getTechId() {
		return techId;
	}


	public void setTechId(String techId) {
		this.techId = techId;
	}


	public String getVisitDate() {
		return visitDate;
	}


	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
}
