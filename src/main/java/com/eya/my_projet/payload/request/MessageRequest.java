package com.eya.my_projet.payload.request;

public class MessageRequest {

	private String contenu;
	
	
	private long thread;
	
	private long reciever;
	
	public MessageRequest() { }

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public long getReciever() {
		return reciever;
	}

	public void setReceiver(long reciever) {
		this.reciever = reciever;
	}

	public long getThread() {
		return thread;
	}

	public void setThread(long thread) {
		this.thread = thread;
	}
	
	
	
	
	
}
