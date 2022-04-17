package com.eya.my_projet.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;






@Entity

public class Comptable extends User {
	
	private String numero_telephone;
	private String adresse;
	private String ecole_sperieur;
	
	
	 @OneToMany(targetEntity=Demande.class, mappedBy="comptable" )
	 private List<Demande> demandes = new ArrayList<>();
	
	 /*@OneToOne()
	 @JoinColumn(name = "id", referencedColumnName="id")
	 private User user;*/
	 
	
	 public Comptable() { }
	 
	public Comptable(String username, String email, String adresse2, String numero_telephone2, String ecole_sperieur2,
			String encode) {
		this.username = username;
		this.email = email;
		this.adresse = adresse2;
		this.setNumero_telephone(numero_telephone2);
		this.ecole_sperieur=ecole_sperieur2;
		this.password = encode;
	}
	
	/*public User getUser() {
		return this.user;
	}*/
	
	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getEcole_sperieur() {
		return ecole_sperieur;
	}

	public void setEcole_sperieur(String ecole_sperieur) {
		this.ecole_sperieur = ecole_sperieur;
	}

	public String getUsername() {
		return this.username;
	}

	public String getNumero_telephone() {
		return numero_telephone;
	}

	public void setNumero_telephone(String numero_telephone) {
		this.numero_telephone = numero_telephone;
	}
	
	
	
}
