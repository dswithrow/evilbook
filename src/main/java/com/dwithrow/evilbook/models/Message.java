package com.dwithrow.evilbook.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="messages")
public class Message {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="sender_id")
	private User sender;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="recipient_id")
	private User recipient;
	@Max(255)
	private String subject;
	@NotBlank
	private String body;
	private boolean isRead;
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	
	@PrePersist
	protected void onCreate(){
		this.createdAt = new Date();
		this.updatedAt = this.createdAt;
	}
	@PreUpdate
	protected void onUpdate(){
		this.updatedAt = new Date();
	}
	public Long getId() {
		return id;
	}
	public User getSender() {
		return sender;
	}
	public User getRecipient() {
		return recipient;
	}
	public String getSubject() {
		return subject;
	}
	public String getBody() {
		return body;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void markAsRead() {
		this.isRead = true;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
}
