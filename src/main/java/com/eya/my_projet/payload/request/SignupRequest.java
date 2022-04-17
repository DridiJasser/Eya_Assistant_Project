package com.eya.my_projet.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private String role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;
  public String getNumero_telephone() {
	return numero_telephone;
}

public void setNumero_telephone(String numero_telephone) {
	this.numero_telephone = numero_telephone;
}

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

private String numero_telephone;
	private String adresse;
	private String ecole_sperieur;
	private String nom_entreprise;
	

  public String getNom_entreprise() {
		return nom_entreprise;
	}

	public void setNom_entreprise(String nom_entreprise) {
		this.nom_entreprise = nom_entreprise;
	}

public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

public String getRole() {
	return role;
}

public void setRole(String role) {
	this.role = role;
}

}
