package domain;

import java.io.Serializable;
import java.sql.Date;

public class Complain  implements Serializable{
	
	private int id;
	private String type;
	private String message;
	private int repId;
	private int tecId;
	private Date visitDate;
	
	
	public Complain() {
		super();
	}
	
	public Complain(int id, String type, String message, int repId, int tecId, Date visitDate) {
		super();
		this.id = id;
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

	public int getRepId() {
		return repId;
	}

	public void setRepId(int repId) {
		this.repId = repId;
	}

	public int getTecId() {
		return tecId;
	}

	public void setTecId(int tecId) {
		this.tecId = tecId;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
}
