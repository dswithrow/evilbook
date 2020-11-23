package com.dwithrow.evilbook.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Size(min=8, max=25)
	private String username;
	private String email;
	private String password;
	@Transient
	private String passwordConfirmation;
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private List<Post> posts;
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY)
	private List<Comment> comments;
	@OneToMany(mappedBy="recipient", fetch = FetchType.LAZY)
	private List<Message> inbox;
	@OneToMany(mappedBy="sender", fetch = FetchType.LAZY)
	private List<Message> outbox;
//	@OneToMany(mappedBy="products", fetch = FetchType.LAZY)
//	private List<Product> products;
	@OneToOne(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private Bio bio;
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	
	public User() {
	}
	
	@PrePersist
	protected void onCreate(){
		this.createdAt = new Date();
		this.updatedAt = this.createdAt;
		
	}
	@PreUpdate
	protected void onUpdate(){
		this.updatedAt = new Date();
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

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public Long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Bio getBio() {
		return bio;
	}

	public void setBio(Bio bio) {
		this.bio = bio;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public List<Comment> getComments() {
		return comments;
	}

//	public List<Product> getProducts() {
//		return products;
//	}

	public List<Message> getInbox() {
		return inbox;
	}
	public List<Message> getOutbox() {
		return outbox;
	}
}
