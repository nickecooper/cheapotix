package com.cheapotix.model;

import java.util.List;

import org.hibernate.annotations.Type;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;


@Entity
@Table(name = "users")
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	
	@Column(name = "pass")
	private String password;
	

	@Type(ListArrayType.class)
	@Column(columnDefinition = "text[]", name = "arenaids")
	private List<String> arenaIds;
	
	private double threshhold;
	
	@Column(name = "updatesuntilemail")
	private int updatesUntilEmail;
	
	@Column(name = "emailfrequency")
	private int emailFrequency;
	
	@Column(name = "onhold")
	private boolean onHold;

	public AppUser(String email, String password, List<String> arenaIds, double threshhold) {
		this.email = email;
		this.arenaIds = arenaIds;
		this.threshhold = threshhold;
		this.updatesUntilEmail = 32;
		this.emailFrequency = 32;
		this.onHold = false;
	}
	
	public int getUpdatesUntilEmail() {
		return updatesUntilEmail;
	}

	public void setUpdatesUntilEmail(int updatesUntilEmail) {
		this.updatesUntilEmail = updatesUntilEmail;
	}

	public int getEmailFrequency() {
		return emailFrequency;
	}

	public void setEmailFrequency(int emailFrequency) {
		this.emailFrequency = emailFrequency;
	}

	public boolean isOnHold() {
		return onHold;
	}

	public void setOnHold(boolean onHold) {
		this.onHold = onHold;
	}

	public AppUser() {
		
	}

	public Long getId() {
		return id;
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

	public List<String> getArenaIds() {
		return arenaIds;
	}

	public void setArenaIds(List<String> arenaIds) {
		this.arenaIds = arenaIds;
	}

	public double getThreshhold() {
		return threshhold;
	}

	public void setThreshhold(double threshhold) {
		this.threshhold = threshhold;
	}
	
	
}
