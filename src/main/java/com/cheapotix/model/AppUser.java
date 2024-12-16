package com.cheapotix.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	
	@Column(name = "pass")
	private String password;
	
	@Column(columnDefinition = "text[]", name = "arenaids")
	@ElementCollection
	private List<String> arenaIds;
	
	private double threshhold;

	public AppUser(String email, String password, List<String> arenaIds, double threshhold) {
		this.email = email;
		this.arenaIds = arenaIds;
		this.threshhold = threshhold;
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
