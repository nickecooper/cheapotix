package com.cheapotix.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "games")
public class Game {
	@Id
	private String id;
	
	private String title;
	
	@Column(name= "ticketslink")
	private String ticketsLink;
	
	@Column(name= "arenaid")
	private String arenaId;
	
	@Column(name= "minprice")
	private double minPrice;
	
	private LocalDateTime date;

	public Game() {
		
	}
	
	public Game(String id, String title, String ticketsLink, String arenaId, double minPrice, LocalDateTime date) {
		this.id = id;
		this.title = title;
		this.ticketsLink = ticketsLink;
		this.arenaId = arenaId;
		this.minPrice = minPrice;
		this.date = date;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTicketsLink() {
		return ticketsLink;
	}

	public void setTicketsLink(String ticketsLink) {
		this.ticketsLink = ticketsLink;
	}

	public String getArenaId() {
		return arenaId;
	}

	public void setArenaId(String arenaId) {
		this.arenaId = arenaId;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	
}
