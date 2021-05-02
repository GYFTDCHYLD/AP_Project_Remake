package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.Transaction;

import factories.complainSessionFactoryBuilder;


@Entity
@Table(name = "Complain")
public class Complain implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "custId")
	private String custId; 
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "repId")
	private String repId;
	
	@Column(name = "tecId")
	private String tecId;
	
	@Column(name = "visitDate")
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
	
	public void makeComplain(){
		Session session = complainSessionFactoryBuilder.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.save(this);
		transaction.commit();
		session.close();
	}
	
	public void assignComplain(){ 
		Session session = complainSessionFactoryBuilder.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Complain complain = (Complain) session.get(Complain.class, this.id); 
		complain.setRepId(this.repId);
		complain.setTecId(this.tecId);
		session.update(complain);
		transaction.commit();
		session.close();
	}
	
	public void setDate(){ 
		Session session = complainSessionFactoryBuilder.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Complain complain = (Complain) session.get(Complain.class, this.id); 
		complain.setVisitDate(this.visitDate);
		session.update(complain);
		transaction.commit();
		session.close();
	}
	
	public List<Complain> readAll(){
		List<Complain> complainList = new ArrayList<>(); 
		Session session = complainSessionFactoryBuilder.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		complainList = (List<Complain>) session.createQuery("FROM Complain").getResultList();
		transaction.commit();
		session.close();
		return complainList;
	}
	
	public void delete() {
		Session session = complainSessionFactoryBuilder.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Complain complain =(Complain) session.get(Complain.class, this.id); 
		session.delete(complain);
		transaction.commit();
		session.close();
	}
}
