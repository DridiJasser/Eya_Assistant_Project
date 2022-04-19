package com.eya.my_projet.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "id", "comptable", "client" })
public class Demande {
	
	@Id
	@GeneratedValue()
	private Long id;
	 private String duree;
	 private String dateD;
	 private int etat = 0;
	 private String dateAC;
	 
	
	 
	 
	 @ManyToOne @JoinColumn(name="idComptable", nullable=false)
	 private Comptable comptable;
	 
	 @ManyToOne @JoinColumn(name="idClient", nullable=false)
	 private Client client;
	 
	 @OneToMany(targetEntity=FileDB.class, mappedBy="demande" )
	 private List<FileDB> files = new ArrayList<>();
	 
	 
	 public Demande(){}
	 
	 public Demande(String duree) {
		 this.duree = duree;
	 }
	 
	 public Demande(String dateAC2, String dateD2, String duree2, int etat2) {
		this.dateAC= dateAC2;
		this.dateD=dateD2;
		this.duree=duree2;
		this.etat=etat2;
	 }
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDuree() {
		return duree;
	}
	public void setDuree(String duree) {
		this.duree = duree;
	}
	public String getDateD() {
		return dateD;
	}
	public void setDateD(String dateD) {
		this.dateD = dateD;
	}
	public int getEtat() {
		return etat;
	}
	public void setEtat(int etat) {
		this.etat = etat;
	}
	public String getDateAC() {
		return dateAC;
	}
	public void setDateAC(String dateAC) {
		this.dateAC = dateAC;
	}
	public String getDateRF() {
		return dateRF;
	}
	public void setDateRF(String dateRF) {
		this.dateRF = dateRF;
	}
	private String dateRF;
	
	
	 public Comptable getComptable() {
	        return comptable;
	    }

	    public void setComptable(Comptable comptable) {
	        this.comptable = comptable;
	    }
	    
	    
	    
	    public Client getClient() {
	        return client;
	    }

	    public void setClient(Client client) {
	        this.client = client;
	    }
	    
	    

}
