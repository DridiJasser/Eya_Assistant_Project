package com.eya.my_projet.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "files")
public class FileDB {
  
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	
	private String name;
	
	private String type;

	
	@Lob
	private byte[] data;
  
	public Demande getDemande() {
		return demande;
	}



	public void setDemande(Demande demande) {
		this.demande = demande;
	}
	private long status = 0;
  
	@ManyToOne
	@JoinColumn(name="demande_id", nullable=false)
	private Demande demande;
	
	
	
	@ManyToOne
	@JoinColumn(name="recipient_id", nullable=false)
	private User recipient;
	
	@ManyToOne
	@JoinColumn(name="sender_id", nullable=false)
	private User sender;
	
  
  
  
	public FileDB() {
	}
  
  
	
	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}
 
  
  
  
  
	  public FileDB(String name, String type, byte[] data) {
	    this.name = name;
	    this.type = type;
	    this.data = data;
	  
	  }
  public FileDB(String id, String name, String type, byte[] data) {
	super();
	this.id = id;
	this.name = name;
	this.type = type;
	this.data = data;
}







public User getRecipent() {
	return recipient;
}




public void setRecipent(User recipent) {
	this.recipient = recipent;
}


public User getSender() {
	return sender;
}


public void setSender(User sender) {
	this.sender = sender;
}


public String getId() {
    return id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public byte[] getData() {
    return data;
  }
  public void setData(byte[] data) {
    this.data = data;
  }
}