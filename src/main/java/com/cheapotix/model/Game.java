package com.cheapotix.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String Title;
	
	private String link;
	
	private String arenaId;
	
	private double minPrice;
	
	private boolean hasPassed;

}
