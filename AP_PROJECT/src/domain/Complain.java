package domain;

import java.io.Serializable;
import java.sql.Date;

public class Complain implements Serializable{
	
	private int id;
	private String userId;
	private String type;
	private String message;
	private String repId;
	private String tecId;
	private Date visitDate;
	
	
	public Complain() {
		super();
	}
	
	public Complain(int id, String userId, String type, String message, String repId, String tecId, Date visitDate) {
		super();
		this.id = id;
		this.userId = userId;
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
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
}
