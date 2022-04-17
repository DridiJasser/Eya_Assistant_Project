package com.eya.my_projet.payload.request;

import javax.persistence.Id;

public class DemandeRequest {
	
	
	private Long idC;
	private Long idComp;
	private String duree;
	

	public long getIdC() {
		return idC;
	}
	
	public void setIdC(long idC) {
		this.idC = idC;
	}
	
	public Long getIdComp() {
		return idComp;
	}
	
	public void setIdComp(Long idComp) {
		this.idComp = idComp;
	}
	
	public String getDuree() {
		return duree;
	}
	
	public void setDuree(String duree) {
		this.duree = duree;
	}
	
	

}
