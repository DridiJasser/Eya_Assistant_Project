package com.eya.my_projet.models;

import javax.persistence.*;

import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {

	@Id
	@GeneratedValue
	private long id;
	
	
	@ManyToOne()
	@JoinColumn(name="thread_id", nullable=false)
	private Thread thread;
	
	private String contenu;

	private String sendAt = DateTime.now().toString();
	
	@ManyToOne()
	@JoinColumn(name="sender_id", nullable=false)
	private User sender;
	
	
	@ManyToOne()
	@JoinColumn(name="reciever_id", nullable=false)
	private User reciever;


	public Message(String contenu) {
		super();
		this.contenu = contenu;
	}


	public String getSendAt() {
		return this.sendAt;
	}
	
	public long getId() {
		return id;
	}


	public Thread getThread() {
		return thread;
	}


	public void setThread(Thread thread) {
		this.thread = thread;
	}


	public String getContenu() {
		return contenu;
	}


	public void setContenu(String contenu) {
		this.contenu = contenu;
	}


	public User getSender() {
		return sender;
	}


	public void setSender(User sender) {
		this.sender = sender;
	}


	public User getReciever() {
		return reciever;
	}


	public void setReciever(User reciever) {
		this.reciever = reciever;
	}
	
	
}
