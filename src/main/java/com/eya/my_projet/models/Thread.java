package com.eya.my_projet.models;

import java.util.List;

import javax.persistence.*;

import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "threads")
public class Thread {

	
	@Id
	@GeneratedValue
	private long id;
	
	private String sujet;
	
	private String createdAt = DateTime.now().toString();
	
	
	@OneToMany(mappedBy="thread", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Message> messages;
	

	@ManyToOne()
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	public Thread() { }
	
	public Thread(String sujet, String createdAt) {
		super();
		this.sujet = sujet;
		this.createdAt = createdAt;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	public void addMessages(Message message) {
		this.messages.add(message);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}
