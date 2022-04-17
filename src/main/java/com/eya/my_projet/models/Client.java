package com.eya.my_projet.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="client")
public class Client extends User {

	
	
	private String  nom_entreprise;
	
	

	 @OneToMany( targetEntity=Demande.class, mappedBy="client" )
	 private List<Demande> demandes = new ArrayList<>();
	
	 @OneToOne()
	 @JoinColumn(name = "user_id", referencedColumnName = "id")
	 private User user;
	 
	
	public Client() { }
	 
	public Client(String username, String email, String nom_entreprise, String encode) {
		
		this.username = username;
		this.email = email;
		
		this.nom_entreprise=nom_entreprise;
		
		this.password = encode;
	}


	public User getUser() {
		return this.user;
	}
	
	public String getNom_entreprise() {
		return nom_entreprise;
	}


	public void setNom_entreprise(String nom_entreprise) {
		this.nom_entreprise = nom_entreprise;
	}
	
	
	
	 
	
	
}
