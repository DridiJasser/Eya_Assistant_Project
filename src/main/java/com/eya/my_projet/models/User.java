package com.eya.my_projet.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)

@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
@UniqueConstraint(columnNames = "email") })
@JsonIgnoreProperties({ "password", "comptable", "client" })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@NotBlank
	@Size(max = 20)
	protected String username;
	
	@NotBlank
	@Size(max = 50)
	@Email
	protected String email;

	@NotBlank
	@Size(max = 120)
	protected String password;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToOne()
	@JoinColumn(name = "id", referencedColumnName = "user_id")
	private Client client;
	
	@OneToOne()
	@JoinColumn(name = "id", referencedColumnName = "user_id")
	private Comptable comptable;

	
	@OneToMany(mappedBy="recipient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FileDB> recievedFiles;
	
	
	@OneToMany(mappedBy= "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<FileDB> sendedFiles;
	
	@OneToMany(mappedBy= "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> sendedMessages;
	
	@OneToMany(mappedBy= "reciever", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> recievedMessages;
	
	

	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Thread> threads;
	
	public User() { }

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	
	
	
	public List<Thread> getThreads() {
		return threads;
	}

	public void setThreads(List<Thread> threads) {
		this.threads = threads;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setRecievedMessages(Set<Message> recievedMessages) {
		this.recievedMessages = recievedMessages;
	}

	public Comptable getComptable() {
		return comptable;
	}

	public void setComptable(Comptable comptable) {
		this.comptable = comptable;
	}

	public Set<FileDB> getRecievedFiles() {
		return recievedFiles;
	}

	public void setRecievedFiles(Set<FileDB> recievedFiles) {
		this.recievedFiles = recievedFiles;
	}

	public Set<FileDB> getSendedFiles() {
		return sendedFiles;
	}

	public void setSendedFiles(Set<FileDB> sendedFiles) {
		this.sendedFiles = sendedFiles;
	}

	public Client getClient() {
		return this.client;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean hasRole(String role) {
		for (Role r: this.roles) {
			if(r.getName().name() == role) {
				return true;
			}
		}
		return false;
	}
	
}