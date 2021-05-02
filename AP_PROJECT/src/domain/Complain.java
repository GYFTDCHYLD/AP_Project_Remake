package domain;

import java.io.Serializable;

public class Complain implements Serializable{
	private static final long serialVersionUID = 1L;	
	
	private int id;
	private String custId; 
	private String type;
	private String message;
	private String repId;
	private String tecId;
	private String visitDate; 
	
	
	public Complain() {
		super();
	}
	
	public Complain(int id, String custId, String type, String message, String repId, String tecId, String visitDate) {
		super();
		this.id = id;
		this.custId = custId;
		this.type = type;
		this.message = message;
		this.repId = repId;
		this.tecId = tecId;
		this.visitDate = visitDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public String getcustId() {
		return custId;
	}

	public void setcustId(String custId) { 
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

	public String getTecId() {
		return tecId;
	}

	public void setTecId(String tecId) {
		this.tecId = tecId;
	}

	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
}
