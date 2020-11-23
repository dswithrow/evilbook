package com.dwithrow.evilbook.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="bio")
public class Bio {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	@Size(min=3, max=25)
	private String displayName;
	@Size(min=0, max=255)
	private String about;
	@Size(min=0, max=255)
	private String occupation;
	@Size(min=0, max=255)
	private String nemesis;
	private String image;
	private String avatar;
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
	public User getUser() {
		return user;
	}
	public String getDisplayName() {
		return displayName;
	}
	public String getAbout() {
		return about;
	}
	public String getOccupation() {
		return occupation;
	}
	public String getNemesis() {
		return nemesis;
	}
	public String getImage() {
		return image;
	}
	public String getAvatar() {
		return avatar;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public void setNemesis(String nemesis) {
		this.nemesis = nemesis;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}
