package com.eya.my_projet.payload.request;

public class AcceptRequest {

	private Long idDemande;

	public AcceptRequest(Long idDemande) {
		super();
		this.idDemande = idDemande;
	}

	public Long getIdDemande() {
		return idDemande;
	}

	public void setIdDemande(Long idDemande) {
		this.idDemande = idDemande;
	}
	
	
}
